/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.runtime;

/**
 * Interface for getting and setting properties from script objects
 * This can be a plugin point for e.g. tagged values or alternative
 * array property getters.
 *
 * The interface is engineered with the combinatorially exhaustive
 * combination of types by purpose, for speed, as currently HotSpot is not
 * good enough at removing boxing.
 */
public interface PropertyAccess {
    /**
     * Get the value for a given key and return it as an int
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public int getInt(Object key, int programPoint);

    /**
     * Get the value for a given key and return it as an int
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public int getInt(double key, int programPoint);

    /**
     * Get the value for a given key and return it as an int
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public int getInt(int key, int programPoint);

    /**
     * Get the value for a given key and return it as a double
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public double getDouble(Object key, int programPoint);

    /**
     * Get the value for a given key and return it as a double
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public double getDouble(double key, int programPoint);

    /**
     * Get the value for a given key and return it as a double
     * @param key the key
     * @param programPoint or INVALID_PROGRAM_POINT if pessimistic
     * @return the value
     */
    public double getDouble(int key, int programPoint);

    /**
     * Get the value for a given key and return it as an Object
     * @param key the key
     * @return the value
     */
    public Object get(Object key);

    /**
     * Get the value for a given key and return it as an Object
     * @param key the key
     * @return the value
     */
    public Object get(double key);

    /**
     * Get the value for a given key and return it as an Object
     * @param key the key
     * @return the value
     */
    public Object get(int key);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(Object key, int value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(Object key, double value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(Object key, Object value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(double key, int value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(double key, double value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(double key, Object value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(int key, int value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(int key, double value, int flags);

    /**
     * Set the value of a given key
     * @param key     the key
     * @param value   the value
     * @param flags   call site flags
     */
    public void set(int key, Object value, int flags);

    /**
     * Check if the given key exists anywhere in the proto chain
     * @param key the key
     * @return true if key exists
     */
    public boolean has(Object key);

    /**
     * Check if the given key exists anywhere in the proto chain
     * @param key the key
     * @return true if key exists
     */
    public boolean has(int key);

    /**
     * Check if the given key exists anywhere in the proto chain
     * @param key the key
     * @return true if key exists
     */
    public boolean has(double key);

    /**
     * Check if the given key exists directly in the implementor
     * @param key the key
     * @return true if key exists
     */
    public boolean hasOwnProperty(Object key);

    /**
     * Check if the given key exists directly in the implementor
     * @param key the key
     * @return true if key exists
     */
    public boolean hasOwnProperty(int key);

    /**
     * Check if the given key exists directly in the implementor
     * @param key the key
     * @return true if key exists
     */
    public boolean hasOwnProperty(double key);

    /**
     * Delete a property with the given key from the implementor
     * @param key    the key
     * @param strict are we in strict mode
     * @return true if deletion succeeded, false otherwise
     */
    public boolean delete(int key, boolean strict);

    /**
     * Delete a property with the given key from the implementor
     * @param key    the key
     * @param strict are we in strict mode
     * @return true if deletion succeeded, false otherwise
     */
    public boolean delete(double key, boolean strict);

    /**
     * Delete a property with the given key from the implementor
     * @param key    the key
     * @param strict are we in strict mode
     * @return true if deletion succeeded, false otherwise
     */
    public boolean delete(Object key, boolean strict);
}
