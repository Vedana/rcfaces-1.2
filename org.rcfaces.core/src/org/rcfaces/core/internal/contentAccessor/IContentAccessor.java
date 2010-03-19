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
public interface IContentAccessor extends IContentPath {

    String FILTER_SEPARATOR = "::";

    IContentFamily getContentFamily();

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
