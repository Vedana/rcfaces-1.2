/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import java.io.IOException;

import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IService {

    void initialize(FacesContext facesContext);

    void service(FacesContext facesContext, String commandId)
            throws IOException;
}
