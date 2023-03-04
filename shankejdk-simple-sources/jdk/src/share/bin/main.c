/*
 * Copyright (c) 1995, 2012, Oracle and/or its affiliates. All rights reserved.
 */


/*
 * This file contains the main entry point into the launcher code
 * this is the only file which will be repeatedly compiled by other
 * tools. The rest of the files will be linked in.
 */

#include "defines.h"


/*
 * Entry point.
 */
int main(int argc, char **argv) {
    int margc;
    char** margv;
    const jboolean const_javaw = JNI_FALSE;
    margc = argc;
    margv = argv;
    printf("[shanke debug] start from main.c\n");
    return JLI_Launch(margc, margv,
                   sizeof(const_jargs) / sizeof(char *), const_jargs,
                   sizeof(const_appclasspath) / sizeof(char *), const_appclasspath,
                   FULL_VERSION,
                   DOT_VERSION,
                   (const_progname != NULL) ? const_progname : *margv,
                   (const_launcher != NULL) ? const_launcher : *margv,
                   (const_jargs != NULL) ? JNI_TRUE : JNI_FALSE,
                   const_cpwildcard, const_javaw, const_ergo_class);
}
