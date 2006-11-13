/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.AbstractContentAccessor;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.ImageContentInformation;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ImageContentAccessorHandler extends AbstractProvider
        implements IContentAccessorHandler {
    private static final String REVISION = "$Revision$";

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
            FacesContext facesContext, IFiltredImageAccessor contentAccessor,
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

        String url = (String) content;

        int idx = url.indexOf(IContentAccessor.FILTER_SEPARATOR);

        ImageContentInformation imageInformation = null;
        IContentInformation contentInformation = contentInformationRef[0];
        if (contentInformation instanceof ImageContentInformation) {
            imageInformation = (ImageContentInformation) contentInformation;
        }

        if (isProviderEnabled() == false) {
            return null;
        }

        IFiltredImageAccessor modifiedContentAccessor = null;
        if (idx == url.length() - 2) { // Filtre tout seul !
            if (contentAccessor.getRootAccessor() == null) {
                throw new FacesException("Can not get main image of '" + url
                        + "'.");
            }

            String filter = url.substring(0, idx);
            modifiedContentAccessor = new FiltredImageAccessor(filter,
                    contentAccessor.getRootAccessor());

        } else {
            String filter = url.substring(0, idx);
            String newURL = url.substring(idx
                    + IContentAccessor.FILTER_SEPARATOR.length());

            modifiedContentAccessor = new FiltredImageAccessor(filter,
                    new BasicContentAccessor(facesContext, newURL,
                            contentAccessor,
                            IContentAccessor.UNDEFINED_PATH_TYPE));
        }

        if (imageInformation == null) {
            imageInformation = new ImageContentInformation();

            if (contentInformationRef[0] == null) {
                contentInformationRef[0] = imageInformation;
            }
        }

        return formatImageURL(facesContext, modifiedContentAccessor,
                imageInformation);
    }

    public abstract boolean isProviderEnabled();

    public abstract String getContentType(String url);

    public abstract boolean isValidContenType(String contentType);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class FiltredImageAccessor extends AbstractContentAccessor
            implements IFiltredImageAccessor {
        private static final String REVISION = "$Revision$";

        private final String filter;

        public FiltredImageAccessor(String filter, IContentAccessor accessor) {
            super(accessor);
            this.filter = filter;

            setPathType(IContentAccessor.FILTER_PATH_TYPE);
        }

        public Object getContentRef() {
            return null;
        }

        public String getFilter() {
            return filter;
        }

        public String toString() {
            return "[FiltredContentAccessor filter='" + filter
                    + "' contentType=" + getType() + " pathType="
                    + getPathTypeName(getPathType()) + " versionHandler="
                    + getContentVersionHandler() + " root=" + getRootAccessor()
                    + "]";
        }

    }
}
