/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package anttasks;

import compileproperties.CompileProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;

public class CompilePropertiesTask extends MatchingTask {
    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    @Override
    public void execute() {
        CompileProperties.Log log = new CompileProperties.Log() {
            public void error(String msg, Exception e) {
                log(msg, Project.MSG_ERR);
            }
            public void info(String msg) {
                log(msg, Project.MSG_INFO);
            }
            public void verbose(String msg) {
                log(msg, Project.MSG_VERBOSE);
            }
        };
        List<String> mainOpts = new ArrayList<String>();
        int count = 0;
        DirectoryScanner s = getDirectoryScanner(srcDir);
        for (String path: s.getIncludedFiles()) {
            if (path.endsWith(".properties")) {
                String destPath =
                        path.substring(0, path.length() - ".properties".length()) +
                        ".java";
                File srcFile = new File(srcDir, path);
                File destFile = new File(destDir, destPath);
                // Arguably, the comparison in the next line should be ">", not ">="
                // but that assumes the resolution of the last modified time is fine
                // grained enough; in practice, it is better to use ">=".
                if (destFile.exists() && destFile.lastModified() >= srcFile.lastModified())
                    continue;
                destFile.getParentFile().mkdirs();
                mainOpts.add("-compile");
                mainOpts.add(srcFile.getPath());
                mainOpts.add(destFile.getPath());
                mainOpts.add(superclass);
                count++;
            }
        }
        if (mainOpts.size() > 0) {
            log("Generating " + count + " resource files to " + destDir, Project.MSG_INFO);
            CompileProperties cp = new CompileProperties();
            cp.setLog(log);
            boolean ok = cp.run(mainOpts.toArray(new String[mainOpts.size()]));
            if (!ok)
                throw new BuildException("CompileProperties failed.");
        }
    }

    private File srcDir;
    private File destDir;
    private String superclass = "java.util.ListResourceBundle";
}
