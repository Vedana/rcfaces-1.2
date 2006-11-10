/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
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

    public IContentAccessor handleContent(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation[] contentInformationRef,
            IFilterProperties filterProperties) {

        Object ref = contentAccessor.getContentRef();
        if (ref == null || (ref instanceof String)) {
            return null;
        }

        if (ref instanceof ValueBinding) {
            ValueBinding valueBinding = (ValueBinding) ref;

            ref = valueBinding.getValue(facesContext);
            if (ref == null) {
                return null;
            }
        }

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

            return handleContentModel(facesContext, contentModel,
                    contentInformationRef);
        }

        return handleContentRaw(facesContext, ref, contentInformationRef);
    }

    private IContentAccessor handleContentRaw(FacesContext facesContext,
            Object ref, IContentInformation[] contentInformationRef) {

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        String registredURL = rcfacesContext.getContentStorageEngine()
                .registerRaw(facesContext, ref, contentInformationRef[0]);

        return ContentAccessorFactory.createAccessor(registredURL,
                IContentType.USER, null, null);
    }

    private IContentAccessor handleContentModel(FacesContext facesContext,
            IContentModel contentModel,
            IContentInformation[] contentInformationRef) {

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        String registredURL = rcfacesContext.getContentStorageEngine()
                .registerContentModel(facesContext, contentModel,
                        contentInformationRef[0]);

        return ContentAccessorFactory.createAccessor(registredURL,
                IContentType.USER, null, null);
    }

}
