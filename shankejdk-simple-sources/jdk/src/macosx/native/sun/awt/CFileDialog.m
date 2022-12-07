/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import <sys/stat.h>
#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "CFileDialog.h"
#import "ThreadUtilities.h"

#import "java_awt_FileDialog.h"
#import "sun_lwawt_macosx_CFileDialog.h"

@implementation CFileDialog

- (id)initWithFilter:(jboolean)inHasFilter
          fileDialog:(jobject)inDialog
               title:(NSString *)inTitle
           directory:(NSString *)inPath
                file:(NSString *)inFile
                mode:(jint)inMode
        multipleMode:(BOOL)inMultipleMode
      shouldNavigate:(BOOL)inNavigateApps
canChooseDirectories:(BOOL)inChooseDirectories
             withEnv:(JNIEnv*)env;
{
    if (self == [super init]) {
        fHasFileFilter = inHasFilter;
        fFileDialog = JNFNewGlobalRef(env, inDialog);
        fDirectory = inPath;
        [fDirectory retain];
        fFile = inFile;
        [fFile retain];
        fTitle = inTitle;
        [fTitle retain];
        fMode = inMode;
        fMultipleMode = inMultipleMode;
        fNavigateApps = inNavigateApps;
        fChooseDirectories = inChooseDirectories;
        fPanelResult = NSCancelButton;
    }

    return self;
}

-(void) disposer {
    if (fFileDialog != NULL) {
        JNIEnv *env = [ThreadUtilities getJNIEnvUncached];
        JNFDeleteGlobalRef(env, fFileDialog);
        fFileDialog = NULL;
    }
}

-(void) dealloc {
    [fDirectory release];
    fDirectory = nil;

    [fFile release];
    fFile = nil;

    [fTitle release];
    fTitle = nil;

    [fURLs release];
    fURLs = nil;

    [super dealloc];
}

- (void)safeSaveOrLoad {
    NSSavePanel *thePanel = nil;

    /* 
     * 8013553: turns off extension hiding for the native file dialog.
     * This way is used because setExtensionHidden(NO) doesn't work
     * as expected.
     */
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setBool:NO forKey:@"NSNavLastUserSetHideExtensionButtonState"];

    if (fMode == java_awt_FileDialog_SAVE) {
        thePanel = [NSSavePanel savePanel];
        [thePanel setAllowsOtherFileTypes:YES];
    } else {
        thePanel = [NSOpenPanel openPanel];
    }

    if (thePanel != nil) {
        [thePanel setTitle:fTitle];

        if (fNavigateApps) {
            [thePanel setTreatsFilePackagesAsDirectories:YES];
        }

        if (fMode == java_awt_FileDialog_LOAD) {
            NSOpenPanel *openPanel = (NSOpenPanel *)thePanel;
            [openPanel setAllowsMultipleSelection:fMultipleMode];
            [openPanel setCanChooseFiles:!fChooseDirectories];
            [openPanel setCanChooseDirectories:fChooseDirectories];
            [openPanel setCanCreateDirectories:YES];
        }

        [thePanel setDelegate:self];
        fPanelResult = [thePanel runModalForDirectory:fDirectory file:fFile];
        [thePanel setDelegate:nil];

        if ([self userClickedOK]) {
            if (fMode == java_awt_FileDialog_LOAD) {
                NSOpenPanel *openPanel = (NSOpenPanel *)thePanel;
                fURLs = [openPanel URLs];
            } else {
                fURLs = [NSArray arrayWithObject:[thePanel URL]];
            }
            [fURLs retain];
        }
    }

    [self disposer];
}

- (BOOL) askFilenameFilter:(NSString *)filename {
    JNIEnv *env = [ThreadUtilities getJNIEnv];
    jstring jString = JNFNormalizedJavaStringForPath(env, filename);

    static JNF_CLASS_CACHE(jc_CFileDialog, "sun/lwawt/macosx/CFileDialog");
    static JNF_MEMBER_CACHE(jm_queryFF, jc_CFileDialog, "queryFilenameFilter", "(Ljava/lang/String;)Z");
    BOOL returnValue = JNFCallBooleanMethod(env, fFileDialog, jm_queryFF, jString); // AWT_THREADING Safe (AWTRunLoopMode)
    (*env)->DeleteLocalRef(env, jString);

    return returnValue;
}

- (BOOL)panel:(id)sender shouldEnableURL:(NSURL *)url {
    if (!fHasFileFilter) return YES; // no filter, no problem!

    // check if it's not a normal file
    NSNumber *isFile = nil;
    if ([url getResourceValue:&isFile forKey:NSURLIsRegularFileKey error:nil]) {
        if (![isFile boolValue]) return YES; // always show directories and non-file entities (browsing servers/mounts, etc)
    }

    // if in directory-browsing mode, don't offer files
    if ((fMode != java_awt_FileDialog_LOAD) && (fMode != java_awt_FileDialog_SAVE)) {
        return NO;
    }

    // ask the file filter up in Java
    NSString* filePath = (NSString*)CFURLCopyFileSystemPath((CFURLRef)url, kCFURLPOSIXPathStyle);
    BOOL shouldEnableFile = [self askFilenameFilter:filePath];
    [filePath release];
    return shouldEnableFile;
}

- (BOOL) userClickedOK {
    return fPanelResult == NSOKButton;
}

- (NSArray *)URLs {
    return [[fURLs retain] autorelease];
}
@end

/*
 * Class:     sun_lwawt_macosx_CFileDialog
 * Method:    nativeRunFileDialog
 * Signature: (Ljava/lang/String;ILjava/io/FilenameFilter;
 *             Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL
Java_sun_lwawt_macosx_CFileDialog_nativeRunFileDialog
(JNIEnv *env, jobject peer, jstring title, jint mode, jboolean multipleMode,
 jboolean navigateApps, jboolean chooseDirectories, jboolean hasFilter,
 jstring directory, jstring file)
{
    jobjectArray returnValue = NULL;

JNF_COCOA_ENTER(env);
    NSString *dialogTitle = JNFJavaToNSString(env, title);
    if ([dialogTitle length] == 0) {
        dialogTitle = @" ";
    }

    CFileDialog *dialogDelegate = [[CFileDialog alloc] initWithFilter:hasFilter
                                                           fileDialog:peer
                                                                title:dialogTitle
                                                            directory:JNFJavaToNSString(env, directory)
                                                                 file:JNFJavaToNSString(env, file)
                                                                 mode:mode
                                                         multipleMode:multipleMode
                                                       shouldNavigate:navigateApps
                                                 canChooseDirectories:chooseDirectories
                                                              withEnv:env];

    [JNFRunLoop performOnMainThread:@selector(safeSaveOrLoad)
                                 on:dialogDelegate
                         withObject:nil
                      waitUntilDone:YES];

    if ([dialogDelegate userClickedOK]) {
        NSArray *urls = [dialogDelegate URLs];
        jsize count = [urls count];

        static JNF_CLASS_CACHE(jc_String, "java/lang/String");
        returnValue = JNFNewObjectArray(env, &jc_String, count);

        [urls enumerateObjectsUsingBlock:^(id url, NSUInteger index, BOOL *stop) {
            jstring filename = JNFNormalizedJavaStringForPath(env, [url path]);
            (*env)->SetObjectArrayElement(env, returnValue, index, filename);
            (*env)->DeleteLocalRef(env, filename);
        }];
    }

    [dialogDelegate release];
JNF_COCOA_EXIT(env);
    return returnValue;
}
