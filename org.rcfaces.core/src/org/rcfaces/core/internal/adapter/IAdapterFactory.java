/*
 * $Id$
 */
package org.rcfaces.core.internal.adapter;

import java.util.Map;

/**
 * 
 * @author Eclipse Team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdapterFactory {
    Object getAdapter(Object adaptableObject, Class adapterType, Map parameters);

    Class[] getAdapterList();
}
