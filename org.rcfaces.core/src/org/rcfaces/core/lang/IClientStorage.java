/*
 * $Id$
 */
package org.rcfaces.core.lang;

import java.util.Iterator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientStorage {
    Object getAttribute(String name);

    Object setAttribute(String name, Object value);

    Object removeAttribute(String name);

    boolean isEmpty();

    int getSize();

    Iterator listAttributeNames();
}
