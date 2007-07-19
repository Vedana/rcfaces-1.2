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
public interface IAdditionalInformationValuesCapability extends IAdditionalInformationProvider {

    Class getAdditionalInformationValuesType(FacesContext facesContext);
}
