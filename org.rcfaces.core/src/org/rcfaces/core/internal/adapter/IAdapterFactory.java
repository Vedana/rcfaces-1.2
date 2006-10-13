/*
 * $Id$
 */
package org.rcfaces.core.internal.adapter;

/**
 * 
 * @author Eclipse Team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdapterFactory {
    Object getAdapter(Object adaptableObject, Class adapterType);

    Class[] getAdapterList();
}
