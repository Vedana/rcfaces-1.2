/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServicesRegistry {

    IService getService(FacesContext facesContext, String renderKitId,
            String serviceId);
}
