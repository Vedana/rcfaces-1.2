/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
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

    public static IContentAccessor resolveURL(FacesContext facesContext,
            final IContentAccessor contentAccessor,
            final IContentInformation contentInformation,
            final IFilterProperties filterProperties) {

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        IContentAccessorRegistry registry = rcfacesContext
                .getContentAccessorRegistry();

        IContentAccessorHandler handlers[] = registry
                .listContentAccessorHandlers(contentAccessor.getType());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Try to resolve URL '" + contentAccessor + "'");
        }

        IContentAccessor returnContentAccessor = contentAccessor;

        IContentInformation contentInformationRef[] = new IContentInformation[] { contentInformation };
        for (int i = 0; i < handlers.length; i++) {
            IContentAccessorHandler handler = handlers[i];

            IContentAccessor newContentAccessor = handler.handleContent(
                    facesContext, returnContentAccessor, contentInformationRef,
                    filterProperties);

            if (LOG.isDebugEnabled()) {
                LOG.debug("ContentAccessorHandler(" + handler.getId()
                        + ") returns " + newContentAccessor);
            }

            if (newContentAccessor != null) {
                returnContentAccessor = newContentAccessor;
                break;
            }
        }

        Object result = returnContentAccessor.getContentRef();
        if (result == null
                || result
                        .equals(ContentAccessorFactory.UNSUPPORTED_CONTENT_ACCESSOR)) {
            return null;
        }

        IContentVersionHandler contentVersionHandler = returnContentAccessor
                .getContentVersionHandler();
        if (contentVersionHandler != null) {

            IContentAccessor versionedContentAccessor = contentVersionHandler
                    .getVersionedContentAccessor(rcfacesContext, facesContext,
                            returnContentAccessor, contentInformationRef);

            if (LOG.isDebugEnabled()) {
                LOG.debug("contentVersionHandler("
                        + contentVersionHandler.getId() + ") returns "
                        + versionedContentAccessor);
            }

            if (versionedContentAccessor != null) {
                returnContentAccessor = versionedContentAccessor;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("ResolveURL '" + contentAccessor + "' returns '"
                    + returnContentAccessor + "'");
        }
        return returnContentAccessor;
    }
}
