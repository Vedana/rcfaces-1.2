/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;

import org.rcfaces.core.image.IImageContentModel;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.model.IContentModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentStorageAccessorHandler extends AbstractProvider implements
        IContentAccessorHandler {
    private static final String REVISION = "$Revision$";

    private static final String COPY_ATTRIBUTES[] = {
            IImageContentModel.WIDTH_PROPERTY,
            IImageContentModel.HEIGHT_PROPERTY };

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        ((ContentAccessorsRegistryImpl) rcfacesContext
                .getContentAccessorRegistry()).declareContentAccessorHandler(
                null, this);
    }

    public String getId() {
        return "ContentStorageAccessorHandler";
    }

    public IContentAccessor handleContent(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation[] contentInformationRef,
            IFilterProperties filterProperties) {

        if (contentAccessor.getPathType() != IContentAccessor.UNDEFINED_PATH_TYPE) {
            return null;
        }

        Object ref = contentAccessor.getContentRef();
        if (ref == null || (ref instanceof String)) {
            return null;
        }

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        if (ref instanceof IContentModel) {
            IContentModel contentModel = (IContentModel) ref;
            IContentInformation contentInformation = contentInformationRef[0];

            if (contentModel instanceof IFiltredModel) {
                ((IFiltredModel) contentModel).setFilter(filterProperties);

                if (contentAccessor != null) {
                    contentInformation.setFiltredModel(true);
                }
            }

            if (contentInformation != null) {
                for (int i = 0; i < COPY_ATTRIBUTES.length; i++) {
                    String attributeName = COPY_ATTRIBUTES[i];

                    Object value = contentModel.getAttribute(attributeName);
                    if (value == null) {
                        continue;
                    }

                    contentInformation.setAttribute(attributeName, value);
                }
            }

            return rcfacesContext
                    .getContentStorageEngine()
                    .registerContentModel(facesContext, contentModel,
                            contentInformationRef[0], contentAccessor.getType());
        }

        return rcfacesContext.getContentStorageEngine().registerRaw(
                facesContext, ref, contentInformationRef[0],
                contentAccessor.getType());
    }

}
