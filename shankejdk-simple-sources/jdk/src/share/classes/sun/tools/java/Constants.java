/*
 * Copyright (c) 1994, 2004, Oracle and/or its affiliates. All rights reserved.
 */


package sun.tools.java;

/**
 * This interface defines constant that are used
 * throughout the compiler. It inherits from RuntimeConstants,
 * which is an autogenerated class that contains contstants
 * defined in the interpreter.
 *
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 *
 * @author      Arthur van Hoff
 */

public
interface Constants extends RuntimeConstants {

    /*
     * Enable/disable inclusion of certain debug tracing code in the
     * compiler.  When included, the tracing code may be selectively
     * enabled at runtime, otherwise we save the space/time overhead.
     * Should normally be 'false' for a release version.
     */
    public static final boolean tracing = true;

    /*
     * Frequently used identifiers
     */
    Identifier idAppend = Identifier.lookup("append");
    Identifier idClassInit = Identifier.lookup("<clinit>");
    Identifier idCode = Identifier.lookup("Code");
    Identifier idInit = Identifier.lookup("<init>");
    Identifier idLength = Identifier.lookup("length");
    Identifier idNull = Identifier.lookup("");
    Identifier idStar = Identifier.lookup("*");
    Identifier idSuper = Identifier.lookup("super");
    Identifier idThis = Identifier.lookup("this");
    Identifier idClass = Identifier.lookup("class");
    Identifier idToString = Identifier.lookup("toString");
    Identifier idValueOf = Identifier.lookup("valueOf");
    Identifier idNew = Identifier.lookup("new");
    Identifier idGetClass = Identifier.lookup("getClass");
    Identifier idTYPE = Identifier.lookup("TYPE");
    Identifier idFinallyReturnValue = Identifier.lookup("<return>");

    Identifier idJavaLang = Identifier.lookup("java.lang");

    Identifier idJavaLangCloneable = Identifier.lookup("java.lang.Cloneable");

    Identifier idJavaLangError = Identifier.lookup("java.lang.Error");
    Identifier idJavaLangException = Identifier.lookup("java.lang.Exception");
    Identifier idJavaLangObject = Identifier.lookup("java.lang.Object");
    Identifier idJavaLangClass = Identifier.lookup("java.lang.Class");
    Identifier idJavaLangRuntimeException =
          Identifier.lookup("java.lang.RuntimeException");
    Identifier idJavaLangString = Identifier.lookup("java.lang.String");
    Identifier idJavaLangStringBuffer =
          Identifier.lookup("java.lang.StringBuffer");
    Identifier idJavaLangThrowable = Identifier.lookup("java.lang.Throwable");

    Identifier idJavaIoSerializable = Identifier.lookup("java.io.Serializable");


    Identifier idConstantValue = Identifier.lookup("ConstantValue");
    Identifier idLocalVariableTable = Identifier.lookup("LocalVariableTable");
    Identifier idLineNumberTable = Identifier.lookup("LineNumberTable");
// JCOV
    Identifier idCoverageTable = Identifier.lookup("CoverageTable");
// end JCOV
    Identifier idSourceFile = Identifier.lookup("SourceFile");
    Identifier idDocumentation = Identifier.lookup("Documentation");
    Identifier idDeprecated = Identifier.lookup("Deprecated");
    Identifier idSynthetic = Identifier.lookup("Synthetic");
    Identifier idExceptions = Identifier.lookup("Exceptions");
    Identifier idInnerClasses = Identifier.lookup("InnerClasses");

    /* methods we need to know about */
    Identifier idClone = Identifier.lookup("clone");


    /* This is not a real signature marker, since it is also
     * an identifier constituent character.
     */
    char   SIGC_INNERCLASS      = '$';
    String SIG_INNERCLASS       = "$";

    String prefixThis           = "this$";
    String prefixVal            = "val$";
    String prefixLoc            = "loc$";
    String prefixAccess         = "access$";
    String prefixClass          = "class$";
    String prefixArray          = "array$";

    /*
     * Flags
     */
    int F_VERBOSE               = 1 << 0;
    int F_DUMP                  = 1 << 1;
    int F_WARNINGS              = 1 << 2;

    // The meaning of -g has changed, so F_DEBUG flag is removed.
    // public static final int F_DEBUG          = 1 << 3;
    int F_DEBUG_LINES           = 1 << 12;
    int F_DEBUG_VARS            = 1 << 13;
    int F_DEBUG_SOURCE          = 1 << 18;

    // The meaning of -O has changed, so F_OPTIMIZE flag is removed.
    // public static final int F_OPTIMIZE       = 1 << 4;
    int F_OPT                   = 1 << 14;
    int F_OPT_INTERCLASS        = 1 << 15;

    int F_DEPENDENCIES          = 1 << 5;

// JCOV
    int F_COVERAGE              = 1 << 6;
    int F_COVDATA               = 1 << 7;
// end JCOV

    int F_DEPRECATION           = 1 << 9;
    int F_PRINT_DEPENDENCIES    = 1 << 10;
    int F_VERSION12             = 1 << 11;


    int F_ERRORSREPORTED        = 1 << 16;

    int F_STRICTDEFAULT         = 1 << 17;

    /*
     * Modifiers.
     *
     * There has been much confusion regarding modifiers.  There
     * are a number of distinct usages:
     *
     *    - in classfiles to annotate classes, as per JVM pg. 102.
     *    - in classfiles to annotate methods, as per JVM pg. 104.
     *    - in classfiles to annotate InnerClass attributes, as per
     *          http://java.sun.com/products/jdk/1.1/docs/guide/innerclasses
     *    - in the compiler to record java source level modifiers,
     *          as per JLS pg. 157 et al., plus misc. info such as whether
     *          a method is deprecated
     *    - in the JVM to record misc. info, such as whether a method has
     *          has been compiled
     *
     * To make matters worse, the terms "access flags" and "modifiers"
     * are often used interchangably, and some information that might
     * make sense as a flag is expressed using attributes (ie. Synthetic).
     *
     * The constants defined herein have been divided by whether they
     * make sense only within the compiler (M_* and MM_*) or whether
     * they only make sense to the JVM (ACC_* and ACCM_*).  At an earlier
     * time these were all lumped together.  Future maintenance should
     * strive to keep the distinction clear.
     *
     * Note that modifier M_STRICTFP is not in general recoverable from
     * the ACC_STRICT bit in classfiles.
     *
     * Note also that the modifiers M_LOCAL and M_ANONYMOUS do not appear
     * in the InnerClass attribute, as they are above the first 16 bits.
     */

    // Modifiers meaningful to both Java source and the JVM.  These
    // have been kept the same bit in the M_* and ACC_* forms
    // to avoid destabilizing the compiler.
    int M_PUBLIC                = ACC_PUBLIC;
    int M_PRIVATE               = ACC_PRIVATE;
    int M_PROTECTED             = ACC_PROTECTED;
    int M_STATIC                = ACC_STATIC;
    int M_TRANSIENT             = ACC_TRANSIENT;
    int M_SYNCHRONIZED          = ACC_SYNCHRONIZED; // collides with ACC_SUPER
    int M_ABSTRACT              = ACC_ABSTRACT;
    int M_NATIVE                = ACC_NATIVE;
    int M_FINAL                 = ACC_FINAL;
    int M_VOLATILE              = ACC_VOLATILE;
    int M_INTERFACE             = ACC_INTERFACE;

    // Modifiers not meaningful to the JVM.  The JVM only allows 16 bits
    // for modifiers, so keeping these in the unusable bits after the first
    // 16 is a good idea.
    int M_ANONYMOUS             = 0x00010000;
    int M_LOCAL                 = 0x00020000;
    int M_DEPRECATED            = 0x00040000;
    int M_SYNTHETIC             = 0x00080000;
    int M_INLINEABLE            = 0x00100000;

    int M_STRICTFP              = 0x00200000;

    String paraDeprecated       = "@deprecated";

    // Masks for modifiers that apply to Java source code
    int MM_CLASS  = M_PUBLIC
                        | M_INTERFACE
                        | M_FINAL
                        | M_ABSTRACT
                        | M_STRICTFP;
    int MM_MEMBER = M_PUBLIC
                        | M_PRIVATE
                        | M_PROTECTED
                        | M_FINAL
                        | M_STATIC;
    int MM_FIELD  = MM_MEMBER
                        | M_TRANSIENT
                        | M_VOLATILE;
    int MM_METHOD = MM_MEMBER
                        | M_SYNCHRONIZED
                        | M_ABSTRACT
                        | M_NATIVE
                        | M_STRICTFP;

    // Masks for modifiers that apply to class files.
    // Note that the M_SYNTHETIC modifier is never written out to a class file.
    // Synthetic members are indicated using the "Synthetic" attribute.
    int ACCM_CLASS  = ACC_PUBLIC
                        | ACC_INTERFACE
                        | ACC_FINAL
                        | ACC_ABSTRACT
                        | ACC_SUPER
                        | ACC_STRICT;
    int ACCM_MEMBER = ACC_PUBLIC
                        | ACC_PRIVATE
                        | ACC_PROTECTED
                        | ACC_FINAL
                        | ACC_STATIC;
    // The M_ANONYMOUS and M_LOCAL modifiers are not mentioned in the
    // inner classes specification and are never written to classfiles.
    // Also note that ACC_SUPER should never be set in an InnerClass
    // attribute.
    int ACCM_INNERCLASS = ACC_PUBLIC
                        | ACC_PRIVATE
                        | ACC_PROTECTED
                        | ACC_STATIC
                        | ACC_ABSTRACT
                        | ACC_FINAL
                        | ACC_INTERFACE
                        | ACC_STRICT;
    int ACCM_FIELD  = ACCM_MEMBER
                        | ACC_TRANSIENT
                        | ACC_VOLATILE;
    int ACCM_METHOD = ACCM_MEMBER
                        | ACC_SYNCHRONIZED
                        | ACC_ABSTRACT
                        | ACC_NATIVE
                        | ACC_STRICT;

    /*
     * Type codes
     */
    int TC_BOOLEAN   = 0;
    int TC_BYTE      = 1;
    int TC_CHAR      = 2;
    int TC_SHORT     = 3;
    int TC_INT       = 4;
    int TC_LONG      = 5;
    int TC_FLOAT     = 6;
    int TC_DOUBLE    = 7;
    int TC_NULL      = 8;
    int TC_ARRAY     = 9;
    int TC_CLASS     = 10;
    int TC_VOID      = 11;
    int TC_METHOD    = 12;
    int TC_ERROR     = 13;

// JCOV
    /*
     * Cover's types
     */
    int CT_FIRST_KIND   = 1;
    int CT_METHOD       = 1;
    int CT_FIKT_METHOD  = 2;
    int CT_BLOCK        = 3;
    int CT_FIKT_RET     = 4;
    int CT_CASE         = 5;
    int CT_SWITH_WO_DEF = 6;
    int CT_BRANCH_TRUE  = 7;
    int CT_BRANCH_FALSE = 8;
    int CT_LAST_KIND    = 8;
// end JCOV

    /*
     * Type Masks
     */
    int TM_NULL      = 1 << TC_NULL;
    int TM_VOID      = 1 << TC_VOID;
    int TM_BOOLEAN   = 1 << TC_BOOLEAN;
    int TM_BYTE      = 1 << TC_BYTE;
    int TM_CHAR      = 1 << TC_CHAR;
    int TM_SHORT     = 1 << TC_SHORT;
    int TM_INT       = 1 << TC_INT;
    int TM_LONG      = 1 << TC_LONG;
    int TM_FLOAT     = 1 << TC_FLOAT;
    int TM_DOUBLE    = 1 << TC_DOUBLE;
    int TM_ARRAY     = 1 << TC_ARRAY;
    int TM_CLASS     = 1 << TC_CLASS;
    int TM_METHOD    = 1 << TC_METHOD;
    int TM_ERROR     = 1 << TC_ERROR;

    int TM_INT32     = TM_BYTE | TM_SHORT | TM_CHAR | TM_INT;
    int TM_NUM32     = TM_INT32 | TM_FLOAT;
    int TM_NUM64     = TM_LONG | TM_DOUBLE;
    int TM_INTEGER   = TM_INT32 | TM_LONG;
    int TM_REAL      = TM_FLOAT | TM_DOUBLE;
    int TM_NUMBER    = TM_INTEGER | TM_REAL;
    int TM_REFERENCE = TM_ARRAY | TM_CLASS | TM_NULL;

    /*
     * Class status
     */
    int CS_UNDEFINED    = 0;
    int CS_UNDECIDED    = 1;
    int CS_BINARY       = 2;
    int CS_SOURCE       = 3;
    int CS_PARSED       = 4;
    int CS_CHECKED      = 5;
    int CS_COMPILED     = 6;
    int CS_NOTFOUND     = 7;


    /*
     * Attributes
     */
    int ATT_ALL         = 0xFFFFFFFF;
    int ATT_CODE        = 1 << 1;
    int ATT_ALLCLASSES  = 1 << 2;

    /*
     * Number of bits used in file offsets.  The line number and
     * file offset are concatenated into a long, with enough room
     * for other information to be added later if desired (such as
     * token lengths).  For the moment explicit bit manipulations
     * are used to modify the fields.  This makes sense for efficiency
     * but at some point these ought to be better encapsulated.
     */
    int WHEREOFFSETBITS = 32;
    long MAXFILESIZE    = (1L << WHEREOFFSETBITS) - 1;
    long MAXLINENUMBER  = (1L << (64 - WHEREOFFSETBITS)) - 1;

    /*
     * Operators
     */
    int COMMA           = 0;
    int ASSIGN          = 1;

    int ASGMUL          = 2;
    int ASGDIV          = 3;
    int ASGREM          = 4;
    int ASGADD          = 5;
    int ASGSUB          = 6;
    int ASGLSHIFT       = 7;
    int ASGRSHIFT       = 8;
    int ASGURSHIFT      = 9;
    int ASGBITAND       = 10;
    int ASGBITOR        = 11;
    int ASGBITXOR       = 12;

    int COND            = 13;
    int OR              = 14;
    int AND             = 15;
    int BITOR           = 16;
    int BITXOR          = 17;
    int BITAND          = 18;
    int NE              = 19;
    int EQ              = 20;
    int GE              = 21;
    int GT              = 22;
    int LE              = 23;
    int LT              = 24;
    int INSTANCEOF      = 25;
    int LSHIFT          = 26;
    int RSHIFT          = 27;
    int URSHIFT         = 28;
    int ADD             = 29;
    int SUB             = 30;
    int DIV             = 31;
    int REM             = 32;
    int MUL             = 33;
    int CAST            = 34;           // (x)y
    int POS             = 35;           // +x
    int NEG             = 36;           // -x
    int NOT             = 37;
    int BITNOT          = 38;
    int PREINC          = 39;           // ++x
    int PREDEC          = 40;           // --x
    int NEWARRAY        = 41;
    int NEWINSTANCE     = 42;
    int NEWFROMNAME     = 43;
    int POSTINC         = 44;           // x++
    int POSTDEC         = 45;           // x--
    int FIELD           = 46;
    int METHOD          = 47;           // x(y)
    int ARRAYACCESS     = 48;           // x[y]
    int NEW             = 49;
    int INC             = 50;
    int DEC             = 51;

    int CONVERT         = 55;           // implicit conversion
    int EXPR            = 56;           // (x)
    int ARRAY           = 57;           // {x, y, ...}
    int GOTO            = 58;

    /*
     * Value tokens
     */
    int IDENT           = 60;
    int BOOLEANVAL      = 61;
    int BYTEVAL         = 62;
    int CHARVAL         = 63;
    int SHORTVAL        = 64;
    int INTVAL          = 65;
    int LONGVAL         = 66;
    int FLOATVAL        = 67;
    int DOUBLEVAL       = 68;
    int STRINGVAL       = 69;

    /*
     * Type keywords
     */
    int BYTE            = 70;
    int CHAR            = 71;
    int SHORT           = 72;
    int INT             = 73;
    int LONG            = 74;
    int FLOAT           = 75;
    int DOUBLE          = 76;
    int VOID            = 77;
    int BOOLEAN         = 78;

    /*
     * Expression keywords
     */
    int TRUE            = 80;
    int FALSE           = 81;
    int THIS            = 82;
    int SUPER           = 83;
    int NULL            = 84;

    /*
     * Statement keywords
     */
    int IF              = 90;
    int ELSE            = 91;
    int FOR             = 92;
    int WHILE           = 93;
    int DO              = 94;
    int SWITCH          = 95;
    int CASE            = 96;
    int DEFAULT         = 97;
    int BREAK           = 98;
    int CONTINUE        = 99;
    int RETURN          = 100;
    int TRY             = 101;
    int CATCH           = 102;
    int FINALLY         = 103;
    int THROW           = 104;
    int STAT            = 105;
    int EXPRESSION      = 106;
    int DECLARATION     = 107;
    int VARDECLARATION  = 108;

    /*
     * Declaration keywords
     */
    int IMPORT          = 110;
    int CLASS           = 111;
    int EXTENDS         = 112;
    int IMPLEMENTS      = 113;
    int INTERFACE       = 114;
    int PACKAGE         = 115;

    /*
     * Modifier keywords
     */
    int PRIVATE         = 120;
    int PUBLIC          = 121;
    int PROTECTED       = 122;
    int CONST           = 123;
    int STATIC          = 124;
    int TRANSIENT       = 125;
    int SYNCHRONIZED    = 126;
    int NATIVE          = 127;
    int FINAL           = 128;
    int VOLATILE        = 129;
    int ABSTRACT        = 130;
    int STRICTFP        = 131;

    /*
     * Punctuation
     */
    int SEMICOLON       = 135;
    int COLON           = 136;
    int QUESTIONMARK    = 137;
    int LBRACE          = 138;
    int RBRACE          = 139;
    int LPAREN          = 140;
    int RPAREN          = 141;
    int LSQBRACKET      = 142;
    int RSQBRACKET      = 143;
    int THROWS          = 144;

    /*
     * Special tokens
     */
    int ERROR           = 145;          // an error
    int COMMENT         = 146;          // not used anymore.
    int TYPE            = 147;
    int LENGTH          = 148;
    int INLINERETURN    = 149;
    int INLINEMETHOD    = 150;
    int INLINENEWINSTANCE       = 151;

    /*
     * Operator precedence
     */
    int opPrecedence[] = {
        10, 11, 11, 11, 11, 11, 11, 11, 11, 11,
        11, 11, 11, 12, 13, 14, 15, 16, 17, 18,
        18, 19, 19, 19, 19, 19, 20, 20, 20, 21,
        21, 22, 22, 22, 23, 24, 24, 24, 24, 24,
        24, 25, 25, 26, 26, 26, 26, 26, 26
    };

    /*
     * Operator names
     */
    String opNames[] = {
        ",",    "=",    "*=",   "/=",   "%=",
        "+=",   "-=",   "<<=",  ">>=",  ">>>=",
        "&=",   "|=",   "^=",   "?:",   "||",
        "&&",   "|",    "^",    "&",    "!=",
        "==",   ">=",   ">",    "<=",   "<",
        "instanceof", "<<", ">>", ">>>", "+",
        "-",    "/",    "%",    "*",    "cast",
        "+",    "-",    "!",    "~",    "++",
        "--",   "new",  "new",  "new",  "++",
        "--",   "field","method","[]",  "new",
        "++",   "--",   null,   null,   null,

        "convert", "expr", "array", "goto", null,

        "Identifier", "boolean", "byte", "char", "short",
        "int", "long", "float", "double", "string",

        "byte", "char", "short", "int", "long",
        "float", "double", "void", "boolean", null,

        "true", "false", "this", "super", "null",
        null,   null,   null,   null,   null,

        "if",   "else", "for",  "while","do",
        "switch", "case", "default", "break", "continue",
        "return", "try", "catch", "finally", "throw",
        "stat", "expression", "declaration", "declaration", null,

        "import", "class", "extends", "implements", "interface",
        "package", null, null,  null,   null,

        "private", "public", "protected", "const", "static",
        "transient", "synchronized", "native", "final", "volatile",
        "abstract", "strictfp", null, null, null,

        ";",    ":",    "?",    "{",    "}",
        "(",    ")",    "[",    "]",    "throws",
        "error", "comment", "type", "length", "inline-return",
        "inline-method", "inline-new"
    };
}
