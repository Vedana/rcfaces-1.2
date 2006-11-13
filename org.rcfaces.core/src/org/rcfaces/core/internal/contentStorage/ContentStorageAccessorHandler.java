/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

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

            boolean filtred = false;
            if (contentModel instanceof IFiltredModel) {
                ((IFiltredModel) contentModel).setFilter(filterProperties);

                filtred = (contentInformationRef[0] != null);
            }

            if (filtred) {
                contentInformationRef[0].setFiltredModel(filtred);
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
