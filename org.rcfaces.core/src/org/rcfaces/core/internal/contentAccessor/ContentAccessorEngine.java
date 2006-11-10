/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentAccessorEngine {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ContentAccessorEngine.class);

    public static String resolveURL(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation contentInformation,
            IFilterProperties filterProperties) {

        Object initContentRef = contentAccessor.getContentRef();

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        IContentAccessorRegistry registry = rcfacesContext
                .getContentAccessorRegistry();

        IContentAccessorHandler handlers[] = registry
                .listContentAccessorHandlers(contentAccessor.getType());

        IContentInformation contentInformationRef[] = new IContentInformation[] { contentInformation };
        for (int i = 0; i < handlers.length; i++) {
            IContentAccessorHandler handler = handlers[i];

            IContentAccessor newContentAccessor = handler.handleContent(
                    facesContext, contentAccessor, contentInformationRef,
                    filterProperties);
            if (newContentAccessor != null) {
                contentAccessor = newContentAccessor;
                break;
            }
        }

        Object result = contentAccessor.getContentRef();
        if (result == null) {
            return null;
        }

        if ((result instanceof String) == false) {
            LOG.error("Invalid url '" + result + "'.");

            return null;
        }

        String url = (String) result;

        contentInformation = contentInformationRef[0];
        IContentVersionHandler contentVersionHandler = contentAccessor
                .getContentVersionHandler();
        if (contentVersionHandler != null) {
            // On calcule la position absolue ....
            String versionnedURL = contentVersionHandler.getVersionPath(
                    rcfacesContext, facesContext, url, contentAccessor,
                    contentInformation);

            if (versionnedURL != null) {
                url = versionnedURL;
            }
        }

        if (url.equals(initContentRef) && url.charAt(0) != '/') {
            // Pas de version ... rien du tout !
            return url;
        }

        IProcessContext processContext = AbstractProcessContext
                .getProcessContext(facesContext);

        return processContext.getAbsolutePath(url, true);
    }
}
