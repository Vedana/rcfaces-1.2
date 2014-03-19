/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.provider.ISelectionProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectedValuesCapability extends ISelectionProvider {

    Class< ? > getSelectedValuesType(FacesContext facesContext);
}
