/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.model.ISortedComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortedComponentsCapability {

    ISortedComponent[] listSortedComponents(FacesContext context);
}
