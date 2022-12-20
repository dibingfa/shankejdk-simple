/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "runtime/os.hpp"
#include "utilities/globalDefinitions.hpp"
#include "utilities/top.hpp"

// Basic error support

// Info for oops within a java object.  Defaults are zero so
// things will break badly if incorrectly initialized.
int heapOopSize        = 0;
int LogBytesPerHeapOop = 0;
int LogBitsPerHeapOop  = 0;
int BytesPerHeapOop    = 0;
int BitsPerHeapOop     = 0;

// Object alignment, in units of HeapWords.
// Defaults are -1 so things will break badly if incorrectly initialized.
int MinObjAlignment            = -1;
int MinObjAlignmentInBytes     = -1;
int MinObjAlignmentInBytesMask = 0;

int LogMinObjAlignment         = -1;
int LogMinObjAlignmentInBytes  = -1;

// Oop encoding heap max
uint64_t OopEncodingHeapMax = 0;

void basic_fatal(const char* msg) {
  fatal(msg);
}

// Something to help porters sleep at night

void basic_types_init() {

  if( JavaPriority1_To_OSPriority != -1 )
    os::java_to_os_priority[1] = JavaPriority1_To_OSPriority;
  if( JavaPriority2_To_OSPriority != -1 )
    os::java_to_os_priority[2] = JavaPriority2_To_OSPriority;
  if( JavaPriority3_To_OSPriority != -1 )
    os::java_to_os_priority[3] = JavaPriority3_To_OSPriority;
  if( JavaPriority4_To_OSPriority != -1 )
    os::java_to_os_priority[4] = JavaPriority4_To_OSPriority;
  if( JavaPriority5_To_OSPriority != -1 )
    os::java_to_os_priority[5] = JavaPriority5_To_OSPriority;
  if( JavaPriority6_To_OSPriority != -1 )
    os::java_to_os_priority[6] = JavaPriority6_To_OSPriority;
  if( JavaPriority7_To_OSPriority != -1 )
    os::java_to_os_priority[7] = JavaPriority7_To_OSPriority;
  if( JavaPriority8_To_OSPriority != -1 )
    os::java_to_os_priority[8] = JavaPriority8_To_OSPriority;
  if( JavaPriority9_To_OSPriority != -1 )
    os::java_to_os_priority[9] = JavaPriority9_To_OSPriority;
  if(JavaPriority10_To_OSPriority != -1 )
    os::java_to_os_priority[10] = JavaPriority10_To_OSPriority;

  // Set the size of basic types here (after argument parsing but before
  // stub generation).
  if (UseCompressedOops) {
    // Size info for oops within java objects is fixed
    heapOopSize        = jintSize;
    LogBytesPerHeapOop = LogBytesPerInt;
    LogBitsPerHeapOop  = LogBitsPerInt;
    BytesPerHeapOop    = BytesPerInt;
    BitsPerHeapOop     = BitsPerInt;
  } else {
    heapOopSize        = oopSize;
    LogBytesPerHeapOop = LogBytesPerWord;
    LogBitsPerHeapOop  = LogBitsPerWord;
    BytesPerHeapOop    = BytesPerWord;
    BitsPerHeapOop     = BitsPerWord;
  }
  _type2aelembytes[T_OBJECT] = heapOopSize;
  _type2aelembytes[T_ARRAY]  = heapOopSize;
}


// Map BasicType to signature character
char type2char_tab[T_CONFLICT+1]={ 0, 0, 0, 0, 'Z', 'C', 'F', 'D', 'B', 'S', 'I', 'J', 'L', '[', 'V', 0, 0, 0, 0, 0};

// Map BasicType to Java type name
const char* type2name_tab[T_CONFLICT+1] = {
  NULL, NULL, NULL, NULL,
  "boolean",
  "char",
  "float",
  "double",
  "byte",
  "short",
  "int",
  "long",
  "object",
  "array",
  "void",
  "*address*",
  "*narrowoop*",
  "*metadata*",
  "*narrowklass*",
  "*conflict*"
};


BasicType name2type(const char* name) {
  for (int i = T_BOOLEAN; i <= T_VOID; i++) {
    BasicType t = (BasicType)i;
    if (type2name_tab[t] != NULL && 0 == strcmp(type2name_tab[t], name))
      return t;
  }
  return T_ILLEGAL;
}


// Map BasicType to size in words
int type2size[T_CONFLICT+1]={ -1, 0, 0, 0, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, -1};

BasicType type2field[T_CONFLICT+1] = {
  (BasicType)0,            // 0,
  (BasicType)0,            // 1,
  (BasicType)0,            // 2,
  (BasicType)0,            // 3,
  T_BOOLEAN,               // T_BOOLEAN  =  4,
  T_CHAR,                  // T_CHAR     =  5,
  T_FLOAT,                 // T_FLOAT    =  6,
  T_DOUBLE,                // T_DOUBLE   =  7,
  T_BYTE,                  // T_BYTE     =  8,
  T_SHORT,                 // T_SHORT    =  9,
  T_INT,                   // T_INT      = 10,
  T_LONG,                  // T_LONG     = 11,
  T_OBJECT,                // T_OBJECT   = 12,
  T_OBJECT,                // T_ARRAY    = 13,
  T_VOID,                  // T_VOID     = 14,
  T_ADDRESS,               // T_ADDRESS  = 15,
  T_NARROWOOP,             // T_NARROWOOP= 16,
  T_METADATA,              // T_METADATA = 17,
  T_NARROWKLASS,           // T_NARROWKLASS = 18,
  T_CONFLICT               // T_CONFLICT = 19,
};


BasicType type2wfield[T_CONFLICT+1] = {
  (BasicType)0,            // 0,
  (BasicType)0,            // 1,
  (BasicType)0,            // 2,
  (BasicType)0,            // 3,
  T_INT,     // T_BOOLEAN  =  4,
  T_INT,     // T_CHAR     =  5,
  T_FLOAT,   // T_FLOAT    =  6,
  T_DOUBLE,  // T_DOUBLE   =  7,
  T_INT,     // T_BYTE     =  8,
  T_INT,     // T_SHORT    =  9,
  T_INT,     // T_INT      = 10,
  T_LONG,    // T_LONG     = 11,
  T_OBJECT,  // T_OBJECT   = 12,
  T_OBJECT,  // T_ARRAY    = 13,
  T_VOID,    // T_VOID     = 14,
  T_ADDRESS, // T_ADDRESS  = 15,
  T_NARROWOOP, // T_NARROWOOP  = 16,
  T_METADATA,  // T_METADATA   = 17,
  T_NARROWKLASS, // T_NARROWKLASS  = 18,
  T_CONFLICT // T_CONFLICT = 19,
};


int _type2aelembytes[T_CONFLICT+1] = {
  0,                         // 0
  0,                         // 1
  0,                         // 2
  0,                         // 3
  T_BOOLEAN_aelem_bytes,     // T_BOOLEAN  =  4,
  T_CHAR_aelem_bytes,        // T_CHAR     =  5,
  T_FLOAT_aelem_bytes,       // T_FLOAT    =  6,
  T_DOUBLE_aelem_bytes,      // T_DOUBLE   =  7,
  T_BYTE_aelem_bytes,        // T_BYTE     =  8,
  T_SHORT_aelem_bytes,       // T_SHORT    =  9,
  T_INT_aelem_bytes,         // T_INT      = 10,
  T_LONG_aelem_bytes,        // T_LONG     = 11,
  T_OBJECT_aelem_bytes,      // T_OBJECT   = 12,
  T_ARRAY_aelem_bytes,       // T_ARRAY    = 13,
  0,                         // T_VOID     = 14,
  T_OBJECT_aelem_bytes,      // T_ADDRESS  = 15,
  T_NARROWOOP_aelem_bytes,   // T_NARROWOOP= 16,
  T_OBJECT_aelem_bytes,      // T_METADATA = 17,
  T_NARROWKLASS_aelem_bytes, // T_NARROWKLASS= 18,
  0                          // T_CONFLICT = 19,
};


// Support for 64-bit integer arithmetic

// The following code is mostly taken from JVM typedefs_md.h and system_md.c

static const jlong high_bit   = (jlong)1 << (jlong)63;
static const jlong other_bits = ~high_bit;

jlong float2long(jfloat f) {
  jlong tmp = (jlong) f;
  if (tmp != high_bit) {
    return tmp;
  } else {
    if (g_isnan((jdouble)f)) {
      return 0;
    }
    if (f < 0) {
      return high_bit;
    } else {
      return other_bits;
    }
  }
}


jlong double2long(jdouble f) {
  jlong tmp = (jlong) f;
  if (tmp != high_bit) {
    return tmp;
  } else {
    if (g_isnan(f)) {
      return 0;
    }
    if (f < 0) {
      return high_bit;
    } else {
      return other_bits;
    }
  }
}

// least common multiple
size_t lcm(size_t a, size_t b) {
    size_t cur, div, next;

    cur = MAX2(a, b);
    div = MIN2(a, b);

    assert(div != 0, "lcm requires positive arguments");


    while ((next = cur % div) != 0) {
        cur = div; div = next;
    }


    julong result = julong(a) * b / div;
    assert(result <= (size_t)max_uintx, "Integer overflow in lcm");

    return size_t(result);
}

#ifndef PRODUCT

void GlobalDefinitions::test_globals() {
  intptr_t page_sizes[] = { os::vm_page_size(), 4096, 8192, 65536, 2*1024*1024 };
  const int num_page_sizes = sizeof(page_sizes) / sizeof(page_sizes[0]);

  for (int i = 0; i < num_page_sizes; i++) {
    intptr_t page_size = page_sizes[i];

    address a_page = (address)(10*page_size);

    // Check that address within page is returned as is
    assert(clamp_address_in_page(a_page, a_page, page_size) == a_page, "incorrect");
    assert(clamp_address_in_page(a_page + 128, a_page, page_size) == a_page + 128, "incorrect");
    assert(clamp_address_in_page(a_page + page_size - 1, a_page, page_size) == a_page + page_size - 1, "incorrect");

    // Check that address above page returns start of next page
    assert(clamp_address_in_page(a_page + page_size, a_page, page_size) == a_page + page_size, "incorrect");
    assert(clamp_address_in_page(a_page + page_size + 1, a_page, page_size) == a_page + page_size, "incorrect");
    assert(clamp_address_in_page(a_page + page_size*5 + 1, a_page, page_size) == a_page + page_size, "incorrect");

    // Check that address below page returns start of page
    assert(clamp_address_in_page(a_page - 1, a_page, page_size) == a_page, "incorrect");
    assert(clamp_address_in_page(a_page - 2*page_size - 1, a_page, page_size) == a_page, "incorrect");
    assert(clamp_address_in_page(a_page - 5*page_size - 1, a_page, page_size) == a_page, "incorrect");
  }
}

#define EXPECT_EQ(expected, actual) \
        assert(expected == actual, "Test failed");
#define EXPECT_STREQ(expected, actual) \
        assert(strcmp(expected, actual) == 0, "Test failed");

void GlobalDefinitions::test_proper_unit() {
  EXPECT_EQ(0u,     byte_size_in_proper_unit(0u));
  EXPECT_STREQ("B", proper_unit_for_byte_size(0u));

  EXPECT_EQ(1u,     byte_size_in_proper_unit(1u));
  EXPECT_STREQ("B", proper_unit_for_byte_size(1u));

  EXPECT_EQ(1023u,  byte_size_in_proper_unit(K - 1));
  EXPECT_STREQ("B", proper_unit_for_byte_size(K - 1));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(K));
  EXPECT_STREQ("B", proper_unit_for_byte_size(K));

  EXPECT_EQ(1025u,  byte_size_in_proper_unit(K + 1));
  EXPECT_STREQ("B", proper_unit_for_byte_size(K + 1));

  EXPECT_EQ(51200u, byte_size_in_proper_unit(50*K));
  EXPECT_STREQ("B", proper_unit_for_byte_size(50*K));

  EXPECT_EQ(1023u,  byte_size_in_proper_unit(M - 1));
  EXPECT_STREQ("K", proper_unit_for_byte_size(M - 1));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(M));
  EXPECT_STREQ("K", proper_unit_for_byte_size(M));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(M + 1));
  EXPECT_STREQ("K", proper_unit_for_byte_size(M + 1));

  EXPECT_EQ(1025u,  byte_size_in_proper_unit(M + K));
  EXPECT_STREQ("K", proper_unit_for_byte_size(M + K));

  EXPECT_EQ(51200u, byte_size_in_proper_unit(50*M));
  EXPECT_STREQ("K", proper_unit_for_byte_size(50*M));

#ifdef _LP64
  EXPECT_EQ(1023u,  byte_size_in_proper_unit(G - 1));
  EXPECT_STREQ("M", proper_unit_for_byte_size(G - 1));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(G));
  EXPECT_STREQ("M", proper_unit_for_byte_size(G));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(G + 1));
  EXPECT_STREQ("M", proper_unit_for_byte_size(G + 1));

  EXPECT_EQ(1024u,  byte_size_in_proper_unit(G + K));
  EXPECT_STREQ("M", proper_unit_for_byte_size(G + K));

  EXPECT_EQ(1025u,  byte_size_in_proper_unit(G + M));
  EXPECT_STREQ("M", proper_unit_for_byte_size(G + M));

  EXPECT_EQ(51200u, byte_size_in_proper_unit(50*G));
  EXPECT_STREQ("M", proper_unit_for_byte_size(50*G));
#endif
}

#undef EXPECT_EQ
#undef EXPECT_STREQ

#endif // PRODUCT
