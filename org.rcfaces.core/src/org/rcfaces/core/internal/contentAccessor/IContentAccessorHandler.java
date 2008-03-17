/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentAccessorHandler {

    String getId();

    IContentAccessor handleContent(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation contentInformation[],
            IFilterProperties filterProperties);
}
