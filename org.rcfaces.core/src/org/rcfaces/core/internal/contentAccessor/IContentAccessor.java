/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentAccessor {

    String FILTER_SEPARATOR = "::";

    int UNDEFINED_PATH_TYPE = 0x00;

    int RELATIVE_PATH_TYPE = 0x01;

    int CONTEXT_PATH_TYPE = 0x02;

    int ABSOLUTE_PATH_TYPE = 0x04;

    int EXTERNAL_PATH_TYPE = 0x08;

    int FILTER_PATH_TYPE = 0x10;

    String CONTEXT_KEYWORD = "$context";

    IContentFamily getContentFamily();

    int getPathType();

    void setPathType(int pathType);

    Object getAttribute(String attributeName);

    Map getAttributes();

    Object getContentRef();

    String resolveURL(FacesContext facesContext,
            IGeneratedResourceInformation contentInformation,
            IGenerationResourceInformation generationInformation);

    String resolveURL(FacesContext facesContext,
            IGeneratedResourceInformation contentInformation,
            IGenerationResourceInformation generationInformation,
            int pathTypeMask);

    IContentAccessor getParentAccessor();

    IContentVersionHandler getContentVersionHandler();

    void setContentVersionHandler(IContentVersionHandler contentVersionHandler);

    IContentProxyHandler getContentProxyHandler();

    void setContentProxyHandler(IContentProxyHandler contentProxyHandler);
}
