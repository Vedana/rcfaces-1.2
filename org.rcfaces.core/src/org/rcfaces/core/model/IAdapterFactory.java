/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * <p>
 * An adapter factory defines behavioral extensions for one or more classes that
 * implements the IAdaptable interface. Adapter factories are registered with an
 * adapter manager.
 * </p>
 * 
 * Clients may implement this interface.
 * 
 * @author Eclipse Team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdapterFactory {
    /**
     * Returns an object which is an instance of the given class associated with
     * the given object. Returns <code>null</code> if no such object can be
     * found.
     * 
     * @param adaptableObject
     *            the adaptable object being queried (usually an instance of
     *            <code>IAdaptable</code>)
     * @param adapterType
     *            the type of adapter to look up
     * @reutrn a object castable to the given adapter type, or <code>null</code>
     *         if this adapter factory does not have an adapter of the given
     *         type for the given object
     */

    Object getAdapter(Object adaptableObject, Class adapterType, Map parameters);

    /**
     * Returns the collection of adapter types handled by this factory.
     * 
     * This method is generally used by an adapter manager to discover which
     * adapter types are supported, in advance of dispatching any actual
     * getAdapter requests.
     * 
     * @return the collection of adapter types
     */
    Class[] getAdapterList();
}
