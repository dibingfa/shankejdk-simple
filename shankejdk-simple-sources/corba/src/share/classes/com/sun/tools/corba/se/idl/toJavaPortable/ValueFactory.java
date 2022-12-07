/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 */
/*
 * COMPONENT_NAME: idl.toJava
 *
 * ORIGINS: 27
 *
 * Licensed Materials - Property of IBM
 * 5639-D57 (C) COPYRIGHT International Business Machines Corp. 1997, 1999
 * RMI-IIOP v1.0
 *
 */

package com.sun.tools.corba.se.idl.toJavaPortable;

// NOTES:
// -D62023 klr new class

import java.io.PrintWriter;
import java.util.Vector;

import com.sun.tools.corba.se.idl.GenFileStream;
import com.sun.tools.corba.se.idl.SymtabEntry;
import com.sun.tools.corba.se.idl.MethodEntry;
import com.sun.tools.corba.se.idl.ValueEntry;

/**
 *
 **/
public class ValueFactory implements AuxGen
{
  /**
   * Public zero-argument constructor.
   **/
  public ValueFactory ()
  {
  } // ctor

  /**
   * Generate the default value factory class. Provides general algorithm for
   * auxiliary binding generation:
   * 1.) Initialize symbol table and symbol table entry members,
   *     common to all generators.
   * 2.) Initialize members unique to this generator.
   * 3.) Open print stream
   * 4.) Write class heading (package, prologue, source comment, class
   *     statement, open curly
   * 5.) Write class body (member data and methods)
   * 6.) Write class closing (close curly)
   * 7.) Close the print stream
   **/
  public void generate (java.util.Hashtable symbolTable, com.sun.tools.corba.se.idl.SymtabEntry entry)
  {
    this.symbolTable = symbolTable;
    this.entry       = entry;
    init ();
    if (hasFactoryMethods ()) {
        openStream ();
        if (stream == null)
          return;
        writeHeading ();
        writeBody ();
        writeClosing ();
        closeStream ();
    }
  } // generate

  /**
   * Initialize variables unique to this generator.
   **/
  protected void init ()
  {
    factoryClass = entry.name () + "ValueFactory";
    factoryType = Util.javaName (entry);
  } // init

  /**
   * @return true if entry has any factory methods declared
   **/
  protected boolean hasFactoryMethods ()
  {
    Vector init = ((ValueEntry)entry).initializers ();
    if (init != null && init.size () > 0)
      return true;
    else
      return false;
  } // hasFactoryMethods

  /**
   * Open the print stream for subsequent output.
   **/
  protected void openStream ()
  {
    stream = Util.stream (entry, "ValueFactory.java");
  } // openStream

  /**
   * Generate the heading, including the package, imports,
   * source comment, class statement, and left curly.
   **/
  protected void writeHeading ()
  {
    Util.writePackage (stream, entry, Util.TypeFile); // REVISIT - same as interface?
    Util.writeProlog (stream, stream.name ());
    if (entry.comment () != null)
      entry.comment ().generate ("", stream);
    stream.println ("public interface " + factoryClass + " extends org.omg.CORBA.portable.ValueFactory");
    stream.println ('{');
  } // writeHeading

  /**
   * Generate members of this class.
   **/
  protected void writeBody ()
  {
    Vector init = ((ValueEntry)entry).initializers ();
    if (init != null)
    {
      for (int i = 0; i < init.size (); i++)
      {
        MethodEntry element = (MethodEntry) init.elementAt (i);
        element.valueMethod (true); //tag value method if not tagged previously
        ((MethodGen) element.generator ()). interfaceMethod (symbolTable, element, stream);
      }
    }
  } // writeBody

  /**
   * Generate the closing statements.
   **/
  protected void writeClosing ()
  {
    stream.println ('}');
  } // writeClosing

  /**
   * Write the stream to file by closing the print stream.
   **/
  protected void closeStream ()
  {
    stream.close ();
  } // closeStream

  protected java.util.Hashtable     symbolTable;
  protected com.sun.tools.corba.se.idl.SymtabEntry entry;
  protected GenFileStream           stream;

  // Unique to this generator
  protected String factoryClass;
  protected String factoryType;
} // class Holder