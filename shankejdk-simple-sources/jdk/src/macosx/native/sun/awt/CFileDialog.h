/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

@interface CFileDialog : NSObject <NSOpenSavePanelDelegate> {
    // Should we query back to Java for a file filter?
    jboolean fHasFileFilter;

    // sun.awt.CFileDialog
    jobject fFileDialog;

    // Return value from dialog
    NSInteger fPanelResult;

    // Dialog's title
    NSString *fTitle;

    // Starting directory and file
    NSString *fDirectory;
    NSString *fFile;

    // File dialog's mode
    jint fMode;

    // Indicates whether the user can select multiple files
    BOOL fMultipleMode;

    // Should we navigate into apps?
    BOOL fNavigateApps;

    // Can the dialog choose directories ?
    BOOL fChooseDirectories;

    // Contains the absolute paths of the selected files as URLs
    NSArray *fURLs;
}

// Allocator
- (id) initWithFilter:(jboolean)inHasFilter
           fileDialog:(jobject)inDialog
                title:(NSString *)inTitle
            directory:(NSString *)inPath
                 file:(NSString *)inFile
                 mode:(jint)inMode
         multipleMode:(BOOL)inMultipleMode
       shouldNavigate:(BOOL)inNavigateApps
 canChooseDirectories:(BOOL)inChooseDirectories
              withEnv:(JNIEnv*)env;

// Invoked from the main thread
- (void) safeSaveOrLoad;

// Get dialog return value
- (BOOL) userClickedOK;

// Returns the absolute paths of the selected files as URLs
- (NSArray *) URLs;

@end
