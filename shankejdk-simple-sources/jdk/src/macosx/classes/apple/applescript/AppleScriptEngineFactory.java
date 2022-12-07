/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package apple.applescript;

import java.security.*;
import java.util.*;
import javax.script.*;

public class AppleScriptEngineFactory implements ScriptEngineFactory {
    private static volatile boolean initialized = false;

    private static native void initNative();

    static void TRACE(final String str) {
//        System.out.println(AppleScriptEngineFactory.class.getName() + "." + str);
    }

    /**
     * The name of this ScriptEngine
     */
    static final String ENGINE_NAME = "AppleScriptEngine";

    /**
     * The version of this ScriptEngine
     */
    static final String ENGINE_VERSION = "1.1";

    /**
     * The name of this ScriptEngine (yes, again)
     */
    static final String ENGINE_SHORT_NAME = ENGINE_NAME;

    /**
     * The name of the language supported by this ScriptEngine
     */
    static final String LANGUAGE = "AppleScript";

    static ScriptEngineFactory getFactory() {
        TRACE("getFactory()");
        return new AppleScriptEngineFactory();
    }

    /**
     * Initialize a new AppleScriptEngineFactory, replete with a member AppleScriptEngine
     */
    public AppleScriptEngineFactory() {
        TRACE("<ctor>()");
    }

    /**
     * Returns the full name of the ScriptEngine.
     *
     * @return full name of the ScriptEngine
     */
    @Override
    public String getEngineName() {
        TRACE("getEngineName()");
        return ENGINE_NAME;
    }

    /**
     * Returns the version of the ScriptEngine.
     *
     * @return version of the ScriptEngine
     */
    @Override
    public String getEngineVersion() {
        TRACE("getEngineVersion()");
        return ENGINE_VERSION;
    }

    /**
     * Returns the name of the scripting language supported by this ScriptEngine.
     *
     * @return name of the language supported by the ScriptEngine(Factory)
     */
    @Override
    public String getLanguageName() {
        TRACE("getLanguageName()");
        return LANGUAGE;
    }

    /**
     * Returns the version of the scripting language supported by this ScriptEngine(Factory).
     *
     * @return language version supported by the ScriptEngine(Factory)
     */
    @Override
    public String getLanguageVersion() {
        TRACE("getLanguageVersion()");
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                final AppleScriptEngine engine = getScriptEngine();
                return engine.getLanguageVersion();
            }
        });
    }

    /**
     * Returns an immutable list of filename extensions, which generally identify
     * scripts written in the language supported by this ScriptEngine.
     *
     * @return ArrayList of file extensions AppleScript associates with
     */
    @Override
    public List<String> getExtensions() {
        TRACE("getExtensions()");
        return Arrays.asList("scpt", "applescript", "app");
    }

    /**
     * Returns an immutable list of mimetypes, associated with scripts
     * that can be executed by the engine.
     *
     * @return ArrayList of mimetypes that AppleScript associates with
     */
    @Override
    public List<String> getMimeTypes() {
        TRACE("getMimeTypes()");
        return Arrays.asList("application/x-applescript", "text/plain", "text/applescript");
    }

    /**
     * Returns an immutable list of short names for the ScriptEngine,
     * which may be used to identify the ScriptEngine by the ScriptEngineManager.
     *
     * @return
     */
    @Override
    public List<String> getNames() {
        TRACE("getNames()");
        return Arrays.asList("AppleScriptEngine", "AppleScript", "OSA");
    }

    /**
     * Returns a String which can be used to invoke a method of a Java
     * object using the syntax of the supported scripting language.
     *
     * @param obj
     *            unused -- AppleScript does not support objects
     * @param m
     *            function name
     * @param args
     *            arguments to the function
     * @return the AppleScript string calling the method
     */
    @Override
    public String getMethodCallSyntax(final String obj, final String fname, final String ... args) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("my " + fname + "(");
//        // TODO -- do
//        builder.append(")\n");
//        return builder.toString();

        return null;
    }

    /**
     * Returns a String that can be used as a statement to display the specified String using the syntax of the supported scripting language.
     *
     * @param toDisplay
     * @return
     */
    @Override
    public String getOutputStatement(final String toDisplay) {
        // TODO -- this might even be good enough? XD
        return getMethodCallSyntax(null, "print", toDisplay);
    }

    /**
     * Returns the value of an attribute whose meaning may be implementation-specific.
     *
     * @param key
     *            the key to look up
     * @return the static preseeded value for the key in the ScriptEngine, if it exists, otherwise <code>null</code>
     */
    @Override
    public Object getParameter(final String key) {
        final AppleScriptEngine engine = getScriptEngine();
        if (!engine.getBindings(ScriptContext.ENGINE_SCOPE).containsKey(key)) return null;
        return engine.getBindings(ScriptContext.ENGINE_SCOPE).get(key);
    }

    /**
     * Returns A valid scripting language executable program with given statements.
     *
     * @param statements
     * @return
     */
    @Override
    public String getProgram(final String ... statements) {
        final StringBuilder program = new StringBuilder();
        for (final String statement : statements) {
            program.append(statement + "\n");
        }
        return program.toString();
    }

    /**
     * Returns an instance of the ScriptEngine associated with this ScriptEngineFactory.
     *
     * @return new AppleScriptEngine with this factory as it's parent
     */
    @Override
    public AppleScriptEngine getScriptEngine() {
        AppleScriptEngine.checkSecurity();
        ensureInitialized();

        return new AppleScriptEngine(this);
    }

    private static synchronized void ensureInitialized() {
        if (!initialized) {
            initialized = true;

            java.awt.Toolkit.getDefaultToolkit();
            System.loadLibrary("AppleScriptEngine");
            initNative();
        }
    }
}
