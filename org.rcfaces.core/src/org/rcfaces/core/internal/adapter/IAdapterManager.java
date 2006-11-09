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
public interface IAdapterManager {

    Object getAdapter(Object adaptable, Class adapterType, Map parameters);

    void registerAdapters(IAdapterFactory factory, Class adaptable);
}
