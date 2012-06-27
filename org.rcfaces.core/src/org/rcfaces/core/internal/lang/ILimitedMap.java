/*
 * $Id$
 */
package org.rcfaces.core.internal.lang;

import java.io.Serializable;

public interface ILimitedMap<K, V extends Serializable> {

    V get(K key);

    void remove(K key);

    void put(K key, V serializable);
}