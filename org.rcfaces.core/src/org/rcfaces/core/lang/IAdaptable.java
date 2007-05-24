/*
 * $Id$
 */
package org.rcfaces.core.lang;

/**
 * An interface for an adaptable object.
 * 
 * Adaptable objects can be dynamically extended to provide different interfaces
 * (or "adapters"). Adapters are created by adapter factories, which are in turn
 * managed by type by adapter managers. For example,
 * 
 * <pre>
 *     IAdaptable a = [some adaptable];
 *     IFoo x = (IFoo)a.getAdapter(IFoo.class);
 *     if (x != null)
 *        [do IFoo things with x]
 * </pre>
 * 
 * @author Eclipse team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdaptable {

    /**
     * Returns an object which is an instance of the given class associated with
     * this object. Returns <code>null</code> if no such object can be found.
     * 
     * @param adapter
     *            the adapter class to look up
     * @return a object castable to the given class, or <code>null</code> if
     *         this object does not have an adapter for the given class
     */
    Object getAdapter(Class adapter, Object parameter);
}
