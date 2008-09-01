/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.image.ImageContentInformation;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.FiltredContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IComponentContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.contentAccessor.IFiltredContentAccessor;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ImageContentAccessorHandler extends AbstractProvider
        implements IContentAccessorHandler {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageContentAccessorHandler.class);

    public static final String IMAGE_CONTENT_PROVIDER_ID = "org.rcfaces.core.IMAGE_CONTENT_PROVIDER";

    public abstract IImageOperation getImageOperation(String operationId);

    public ImageContentAccessorHandler() {
    }

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        ((ContentAccessorsRegistryImpl) rcfacesContext
                .getContentAccessorRegistry()).declareContentAccessorHandler(
                IContentType.IMAGE, this);
    }

    protected abstract IContentAccessor formatImageURL(
            FacesContext facesContext, IFiltredContentAccessor contentAccessor,
            ImageContentInformation imageInformation);

    public IContentAccessor handleContent(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation[] contentInformationRef,
            IFilterProperties filterProperties) {

        if (contentAccessor.getPathType() != IContentAccessor.FILTER_PATH_TYPE) {
            return null;
        }

        Object content = contentAccessor.getContentRef();
        if ((content instanceof String) == false) {
            return null;
        }

        if (isProviderEnabled() == false) {
            if (LOG.isDebugEnabled()) {
                LOG
                        .debug("Provider is disabled, return an unsupported content accessor flag");
            }

            return ContentAccessorFactory.UNSUPPORTED_CONTENT_ACCESSOR;
        }

        String url = (String) content;

        ImageContentInformation imageInformation = null;
        IContentInformation contentInformation = contentInformationRef[0];
        if (contentInformation instanceof ImageContentInformation) {
            imageInformation = (ImageContentInformation) contentInformation;

        } else {
            imageInformation = new ImageContentInformation();
            
            if (contentInformation instanceof IComponentContentInformation) {
                IComponentContentInformation componentContentInformation = (IComponentContentInformation) contentInformation;

                UIComponent component = componentContentInformation
                        .getComponent();
                String componentClientId = componentContentInformation
                        .getComponentClientId();
                if (component != null) {
                    imageInformation.setComponent(component, componentClientId);

                } else if (componentClientId != null) {
                    imageInformation.setComponentClientId(componentClientId);
                }
            }

            contentInformationRef[0] = imageInformation;
        }

        IFiltredContentAccessor modifiedContentAccessor = null;

        int idx = url.indexOf(IContentAccessor.FILTER_SEPARATOR);
        String filter = url.substring(0, idx);

        if (idx == url.length() - 2) { // Filtre tout seul !
            IContentAccessor parentAccessor = contentAccessor
                    .getParentAccessor();

            if (parentAccessor == null) {
                throw new FacesException("Can not get main image of '" + url
                        + "'.");
            }

            modifiedContentAccessor = new FiltredContentAccessor(filter,
                    parentAccessor);

        } else {
            String newURL = url.substring(idx
                    + IContentAccessor.FILTER_SEPARATOR.length());

            modifiedContentAccessor = new FiltredContentAccessor(filter,
                    new BasicContentAccessor(facesContext, newURL,
                            contentAccessor,
                            IContentAccessor.UNDEFINED_PATH_TYPE));
        }

        IContentAccessor formattedContentAccessor = formatImageURL(
                facesContext, modifiedContentAccessor, imageInformation);

        if (LOG.isDebugEnabled()) {
            LOG.debug("formattedContentAccessor=" + formattedContentAccessor);
        }

        return formattedContentAccessor;
    }

    public abstract boolean isProviderEnabled();

    public abstract String getContentType(String url);

    public abstract boolean isValidContenType(String contentType);

    public static boolean isOperationSupported(FacesContext facesContext,
            String operationId, IContentAccessor imageContentAccessor) {
        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        ImageContentAccessorHandler imageOperationRepository = (ImageContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(
                        ImageContentAccessorHandler.IMAGE_CONTENT_PROVIDER_ID);

        if (imageOperationRepository == null) {
            return false;
        }

        return imageOperationRepository.isOperationSupported(operationId,
                imageContentAccessor);
    }

    protected abstract boolean isOperationSupported(String operationId,
            IContentAccessor imageContentAccessor);
}
