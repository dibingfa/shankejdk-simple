/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
/*
   Copyright 2009-2013 Attila Szegedi

   Licensed under both the Apache License, Version 2.0 (the "Apache License")
   and the BSD License (the "BSD License"), with licensee being free to
   choose either of the two at their discretion.

   You may not use this file except in compliance with either the Apache
   License or the BSD License.

   If you choose to use this file in compliance with the Apache License, the
   following notice applies to you:

       You may obtain a copy of the Apache License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
       implied. See the License for the specific language governing
       permissions and limitations under the License.

   If you choose to use this file in compliance with the BSD License, the
   following notice applies to you:

       Redistribution and use in source and binary forms, with or without
       modification, are permitted provided that the following conditions are
       met:
       * Redistributions of source code must retain the above copyright
         notice, this list of conditions and the following disclaimer.
       * Redistributions in binary form must reproduce the above copyright
         notice, this list of conditions and the following disclaimer in the
         documentation and/or other materials provided with the distribution.
       * Neither the name of the copyright holder nor the names of
         contributors may be used to endorse or promote products derived from
         this software without specific prior written permission.

       THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
       IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
       TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
       PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDER
       BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
       CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
       SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
       BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
       WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
       OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
       ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

/**
 * A default, fairly light implementation of a call site descriptor used for describing non-standard operations. It does
 * not store {@link Lookup} objects but always returns the public lookup from its {@link #getLookup()} method. If you
 * need to support non-public lookup, you can use {@link LookupCallSiteDescriptor}.
 * @author Attila Szegedi
 */
class DefaultCallSiteDescriptor extends AbstractCallSiteDescriptor {

    private final String[] tokenizedName;
    private final MethodType methodType;

    DefaultCallSiteDescriptor(final String[] tokenizedName, final MethodType methodType) {
        this.tokenizedName = tokenizedName;
        this.methodType = methodType;
    }

    @Override
    public int getNameTokenCount() {
        return tokenizedName.length;
    }

    @Override
    public String getNameToken(final int i) {
        try {
            return tokenizedName[i];
        } catch(final ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    String[] getTokenizedName() {
        return tokenizedName;
    }

    @Override
    public MethodType getMethodType() {
        return methodType;
    }

    @Override
    public CallSiteDescriptor changeMethodType(final MethodType newMethodType) {
        return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new DefaultCallSiteDescriptor(tokenizedName,
                newMethodType));
    }
}
