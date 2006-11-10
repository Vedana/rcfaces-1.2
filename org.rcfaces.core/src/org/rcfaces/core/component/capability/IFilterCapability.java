/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFilterCapability {
    IFilterProperties getFilterProperties();

    void setFilterProperties(IFilterProperties properties);
}
