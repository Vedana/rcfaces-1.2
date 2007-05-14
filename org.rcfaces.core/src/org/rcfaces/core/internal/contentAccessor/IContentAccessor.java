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

    String FILTER_SEPARATOR = "::";

    int UNDEFINED_PATH_TYPE = 0;

    int RELATIVE_PATH_TYPE = 1;

    int CONTEXT_PATH_TYPE = 2;

    int ABSOLUTE_PATH_TYPE = 3;

    int EXTERNAL_PATH_TYPE = 4;

    int FILTER_PATH_TYPE = 5;
    
    String CONTEXT_KEYWORD = "$context";

    IContentType getType();

    int getPathType();

    void setPathType(int pathType);

    Object getAttribute(String attributeName);

    Map getAttributes();

    Object getContentRef();

    String resolveURL(FacesContext facesContext,
            IContentInformation contentInformation,
            IFilterProperties filterProperties);

    IContentAccessor getParentAccessor();

    IContentVersionHandler getContentVersionHandler();

    void setContentVersionHandler(IContentVersionHandler contentVersionHandler);
}
