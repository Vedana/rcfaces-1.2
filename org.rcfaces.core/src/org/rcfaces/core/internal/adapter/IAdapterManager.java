/*
 * $Id$
 */
package org.rcfaces.core.internal.adapter;

import org.rcfaces.core.lang.IAdapterFactory;

/**
 * 
 * @author Eclipse Team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdapterManager {

	<T> T getAdapter(Object adaptable, Class<T> adapterType, Object parameter);

	void registerAdapters(IAdapterFactory factory, Class<?> adaptable);
}
