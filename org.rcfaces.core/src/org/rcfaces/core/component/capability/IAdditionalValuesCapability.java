/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdditionalValuesCapability extends IAdditionalProvider {

    Class getAdditionalValuesType(FacesContext facesContext);
}
