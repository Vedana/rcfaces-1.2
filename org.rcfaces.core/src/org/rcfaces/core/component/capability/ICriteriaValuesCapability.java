/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.provider.ICriteriaProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICriteriaValuesCapability extends ICriteriaProvider {

	Class getCriteriaValuesType(FacesContext facesContext);
}
