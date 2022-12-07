/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.objects;

import static jdk.nashorn.internal.codegen.CompilerConstants.specialCall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import jdk.nashorn.internal.objects.annotations.Attribute;
import jdk.nashorn.internal.objects.annotations.Constructor;
import jdk.nashorn.internal.objects.annotations.Function;
import jdk.nashorn.internal.objects.annotations.Property;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import jdk.nashorn.internal.objects.annotations.Where;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

/**
 * Float32 array for the TypedArray extension
 */
@ScriptClass("Float32Array")
public final class NativeFloat32Array extends ArrayBufferView {
    /**
     * The size in bytes of each element in the array.
     */
    @Property(attributes = Attribute.NOT_ENUMERABLE | Attribute.NOT_WRITABLE | Attribute.NOT_CONFIGURABLE, where = Where.CONSTRUCTOR)
    public static final int BYTES_PER_ELEMENT = 4;

    // initialized by nasgen
    @SuppressWarnings("unused")
    private static PropertyMap $nasgenmap$;

    private static final Factory FACTORY = new Factory(BYTES_PER_ELEMENT) {
        @Override
        public ArrayBufferView construct(final NativeArrayBuffer buffer, final int byteOffset, final int length) {
            return new NativeFloat32Array(buffer, byteOffset, length);
        }

        @Override
        public Float32ArrayData createArrayData(final ByteBuffer nb, final int start, final int end) {
            return new Float32ArrayData(nb.asFloatBuffer(), start, end);
        }

        @Override
        public String getClassName() {
            return "Float32Array";
        }
    };

    private static final class Float32ArrayData extends TypedArrayData<FloatBuffer> {

        private static final MethodHandle GET_ELEM = specialCall(MethodHandles.lookup(), Float32ArrayData.class, "getElem", double.class, int.class).methodHandle();
        private static final MethodHandle SET_ELEM = specialCall(MethodHandles.lookup(), Float32ArrayData.class, "setElem", void.class, int.class, double.class).methodHandle();

        private Float32ArrayData(final FloatBuffer nb, final int start, final int end) {
            super(((FloatBuffer)nb.position(start).limit(end)).slice(), end - start);
        }

        @Override
        public Class<?> getElementType() {
            return double.class;
        }

        @Override
        public Class<?> getBoxedElementType() {
            return Double.class;
        }

        @Override
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        private double getElem(final int index) {
            try {
                return nb.get(index);
            } catch (final IndexOutOfBoundsException e) {
                throw new ClassCastException(); //force relink - this works for unoptimistic too
            }
        }

        private void setElem(final int index, final double elem) {
            try {
                if (index < nb.limit()) {
                    nb.put(index, (float) elem);
                }
            } catch (final IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        @Override
        public MethodHandle getElementGetter(final Class<?> returnType, final int programPoint) {
            if (returnType == int.class) {
                return null;
            }
            return getContinuousElementGetter(getClass(), GET_ELEM, returnType, programPoint);
        }

        @Override
        public int getInt(final int index) {
            return (int)getDouble(index);
        }

        @Override
        public double getDouble(final int index) {
            return getElem(index);
        }

        @Override
        public double getDoubleOptimistic(final int index, final int programPoint) {
            return getElem(index);
        }

        @Override
        public Object getObject(final int index) {
            return getDouble(index);
        }

        @Override
        public ArrayData set(final int index, final Object value, final boolean strict) {
            return set(index, JSType.toNumber(value), strict);
        }

        @Override
        public ArrayData set(final int index, final int value, final boolean strict) {
            return set(index, (double)value, strict);
        }

        @Override
        public ArrayData set(final int index, final double value, final boolean strict) {
            setElem(index, value);
            return this;
        }
    }

    /**
     * Constructor
     *
     * @param newObj is this typed array instantiated with the new operator
     * @param self   self reference
     * @param args   args
     *
     * @return new typed array
     */
    @Constructor(arity = 1)
    public static NativeFloat32Array constructor(final boolean newObj, final Object self, final Object... args) {
        return (NativeFloat32Array)constructorImpl(newObj, args, FACTORY);
    }

    NativeFloat32Array(final NativeArrayBuffer buffer, final int byteOffset, final int length) {
        super(buffer, byteOffset, length);
    }

    @Override
    protected Factory factory() {
        return FACTORY;
    }

    @Override
    protected boolean isFloatArray() {
        return true;
    }

    /**
     * Set values
     * @param self   self reference
     * @param array  multiple values of array's type to set
     * @param offset optional start index, interpreted  0 if undefined
     * @return undefined
     */
    @Function(attributes = Attribute.NOT_ENUMERABLE)
    protected static Object set(final Object self, final Object array, final Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    /**
     * Returns a new TypedArray view of the ArrayBuffer store for this TypedArray,
     * referencing the elements at begin, inclusive, up to end, exclusive. If either
     * begin or end is negative, it refers to an index from the end of the array,
     * as opposed to from the beginning.
     * <p>
     * If end is unspecified, the subarray contains all elements from begin to the end
     * of the TypedArray. The range specified by the begin and end values is clamped to
     * the valid index range for the current array. If the computed length of the new
     * TypedArray would be negative, it is clamped to zero.
     * <p>
     * The returned TypedArray will be of the same type as the array on which this
     * method is invoked.
     *
     * @param self self reference
     * @param begin begin position
     * @param end end position
     *
     * @return sub array
     */
    @Function(attributes = Attribute.NOT_ENUMERABLE)
    protected static NativeFloat32Array subarray(final Object self, final Object begin, final Object end) {
        return (NativeFloat32Array)ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override
    protected ScriptObject getPrototype(final Global global) {
        return global.getFloat32ArrayPrototype();
    }
}
