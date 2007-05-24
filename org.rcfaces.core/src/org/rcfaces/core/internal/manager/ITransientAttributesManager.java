/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITransientAttributesManager {
    Object getTransientAttribute(String name);

    Object setTransientAttribute(String name, Object value);
}
