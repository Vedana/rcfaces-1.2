/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.model.IFilterProperties;

/**
 * An object that represent the filter to use on the server side.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFilterCapability {

    /**
     * Return an object that represent the filter to use on the server side.
     * 
     * @return filter
     */
    IFilterProperties getFilterProperties();

    /**
     * Sets an object that represent the filter to use on the server side.
     * 
     * @param properties
     *            filter
     */
    void setFilterProperties(IFilterProperties properties);
}
