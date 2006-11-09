/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentAccessor {

    IContentType getType();

    Object getAttribute(String attributeName);

    Map getAttributes();

    Object getContentRef();

    String resolveURL(FacesContext facesContext,
            IContentInformation contentInformation,
            IFilterProperties filterProperties);

    IContentAccessor getRootAccessor();

    IContentVersionHandler getContentVersionHandler();
}
