/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.images.ImageAdapterFactory;
import org.rcfaces.core.model.IAdaptable;
import org.rcfaces.core.model.IContentModel;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentStorageEngineImpl extends AbstractProvider implements
        IContentStorageEngine {
    private static final String REVISION = "$Revision$";

    private final IContentStorageRepository indirectContentRepository = new BasicContentStorageRepository();

    private String indirectionURL;

    private IAdapterManager adapterManager;

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        if (rcfacesContext.getContentStorageEngine() == null) {
            rcfacesContext.setContentStorageEngine(this);
        }

        indirectionURL = ContentStorageServlet
                .getContentStorageBaseURI(facesContext.getExternalContext()
                        .getApplicationMap());

        adapterManager = rcfacesContext.getAdapterManager();
    }

    public IContentStorageRepository getRepository() {
        return indirectContentRepository;
    }

    public String registerContentModel(FacesContext facesContext,
            IContentModel contentModel, IContentInformation information) {

        IContentStorageRepository repository = getRepository();

        IResolvedContent resolvedContent = null;

        String contentEngineId = contentModel.getContentEngineId();
        if (contentEngineId != null) {
            if (contentModel.checkNotModified()) {
                resolvedContent = repository.load(contentEngineId);
            }

            if (resolvedContent == null) {
                contentModel.setContentEngineId(null);
            }
        }

        if (resolvedContent == null) {
            Map parameters = contentModel.getAttributes();

            if (contentModel.isProcessDataAtRequest()) {
                resolvedContent = new ResolvedContentAtRequest(contentModel);

            } else {
                Object wrappedData = contentModel.getWrappedData();

                if (wrappedData instanceof IAdaptable) {
                    resolvedContent = (IResolvedContent) ((IAdaptable) wrappedData)
                            .getAdapter(IResolvedContent.class, parameters);
                }

                if (resolvedContent == null) {
                    resolvedContent = (IResolvedContent) adapterManager
                            .getAdapter(wrappedData, IResolvedContent.class,
                                    parameters);
                }

                if (resolvedContent == null) {
                    throw new FacesException("Can not transform raw object '"
                            + contentModel.getClass() + "' !");
                }
            }
        }

        String url = repository.save(resolvedContent, contentModel);

        return indirectionURL + '/' + url;
    }

    public String registerRaw(FacesContext facesContext, Object ref,
            IContentInformation information) {

        IResolvedContent resolvedContent = null;
        if (ref instanceof IAdaptable) {
            resolvedContent = (IResolvedContent) ((IAdaptable) ref).getAdapter(
                    IResolvedContent.class, null);
        }

        if (resolvedContent == null) {
            resolvedContent = (IResolvedContent) adapterManager.getAdapter(ref,
                    IResolvedContent.class, null);
        }

        if (resolvedContent == null) {
            throw new FacesException("Can not transform raw object '"
                    + ref.getClass() + "' !");
        }

        String url = getRepository().save(resolvedContent, null);

        return indirectionURL + '/' + url;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ResolvedContentAtRequest extends
            AbstractResolvedContent implements Serializable {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -7807317078965658005L;

        private final IContentModel contentModel;

        private final String contentType;

        private final String suffix;

        private final Map parameters;

        private transient IResolvedContent resolvedContent;

        private transient boolean errorState;

        public ResolvedContentAtRequest(IContentModel contentModel) {
            this.contentModel = contentModel;

            this.contentType = (String) contentModel
                    .getAttribute(IContentModel.CONTENT_TYPE_PROPERTY);
            String suffix = (String) contentModel
                    .getAttribute(IContentModel.URL_SUFFIX_PROPERTY);

            if (suffix == null && contentType != null) {
                suffix = ImageAdapterFactory
                        .getSuffixByContentType(contentType);
            }

            this.suffix = suffix;
            this.parameters = contentModel.getAttributes();
        }

        public String getContentType() {
            return contentType;
        }

        public String getURLSuffix() {
            return suffix;
        }

        public InputStream getInputStream() throws IOException {
            return getResolvedContent().getInputStream();
        }

        public long getModificationDate() {
            return getResolvedContent().getModificationDate();
        }

        public int getLength() {
            return getResolvedContent().getLength();
        }

        protected synchronized IResolvedContent getResolvedContent() {
            if (errorState) {
                return null;
            }

            if (resolvedContent != null) {
                return resolvedContent;
            }

            try {

                Object wrappedData = contentModel.getWrappedData();

                if (wrappedData instanceof IAdaptable) {
                    resolvedContent = (IResolvedContent) ((IAdaptable) wrappedData)
                            .getAdapter(IResolvedContent.class, parameters);

                    if (resolvedContent != null) {
                        return resolvedContent;
                    }
                }

                RcfacesContext rcfacesContext = RcfacesContext
                        .getCurrentInstance();

                resolvedContent = (IResolvedContent) rcfacesContext
                        .getAdapterManager().getAdapter(wrappedData,
                                IResolvedContent.class, parameters);

                if (resolvedContent != null) {
                    return resolvedContent;
                }

                throw new FacesException(
                        "Can not transform wrappedData of content model '"
                                + wrappedData + "' !");

            } catch (RuntimeException ex) {

                errorState = true;

                throw ex;
            }
        }

        public boolean isProcessAtRequest() {
            return true;
        }

        public boolean isErrored() {
            IResolvedContent resolvedContent = getResolvedContent();

            return errorState || resolvedContent.isErrored();
        }

        public String getETag() {
            return getResolvedContent().getETag();
        }

        public String getHash() {
            return getResolvedContent().getHash();
        }

        public String getResourceKey() {
            if (isProcessAtRequest()) {
                // C'est traité sur la requete !

                if (contentModel instanceof IResourceKey) {
                    // L'objet peut tout de même savoir sa clef ???
                    return ((IResourceKey) contentModel).getResourceKey();
                }
                return null;
            }

            return getResolvedContent().getResourceKey();
        }

    }
}
