/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.images.ImageAdapterFactory;
import org.rcfaces.core.internal.lang.LimitedMap;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.lang.IAdaptable;
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

    private static final Log LOG = LogFactory
            .getLog(ContentStorageEngineImpl.class);

    private static final FileNameMap fileNameMap = URLConnection
            .getFileNameMap();

    private static final String DISABLE_CACHE_PARAMETER = "org.rcfaces.core.contentStorage.DISABLE_CACHE";

    private final IContentStorageRepository contentStorageRepository = new BasicContentStorageRepository();

    private int contentStorageServletPathType;

    private String contentStorageServletURL;

    private IAdapterManager adapterManager;

    private boolean disableCache = false;

    private final LimitedMap registredContents = new LimitedMap(
            Constants.CONTENT_STORAGE_CACHE_SIZE);

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        if (rcfacesContext.getContentStorageEngine() == null) {
            rcfacesContext.setContentStorageEngine(this);
        }

        contentStorageServletURL = ContentStorageServlet
                .getContentStorageBaseURI(facesContext.getExternalContext()
                        .getApplicationMap());

        if (contentStorageServletURL == null) {
            LOG
                    .info("Content storage engine is disabled. (No started Content Storage Servlet)");
        }

        disableCache = "true"
                .equalsIgnoreCase(facesContext.getExternalContext()
                        .getInitParameter(DISABLE_CACHE_PARAMETER));
        if (disableCache) {
            LOG.info("Content storage cache is disabled. (Parameter '"
                    + DISABLE_CACHE_PARAMETER + "' is setted to true.)");
        }

        contentStorageServletPathType = IContentAccessor.CONTEXT_PATH_TYPE;

        adapterManager = rcfacesContext.getAdapterManager();
    }

    public IContentStorageRepository getRepository() {
        return contentStorageRepository;
    }

    public String getId() {
        return "ContentStorageEngine";
    }

    public IContentAccessor registerContentModel(FacesContext facesContext,
            IContentModel contentModel, IContentInformation contentInformation,
            IContentType contentType) {

        if (contentStorageServletURL == null) {
            LOG
                    .info("ContentStorage is not initialized. (Servlet path is invalid)");

            return ContentAccessorFactory.UNSUPPORTED_CONTENT_ACCESSOR;
        }

        Content content = null;
        boolean contentEngineMustBeRegistred = false;

        IContentStorageRepository repository = getRepository();

        IResolvedContent resolvedContent = null;

        String contentEngineId = null;
        if (disableCache == false) {
            contentEngineId = contentModel.getContentEngineId();
            if (contentEngineId == null
                    && (contentInformation == null || contentInformation
                            .isTransient() == false)) {
                content = new Content(contentModel, contentInformation,
                        contentType);

                contentEngineId = (String) registredContents.get(content);

                contentEngineMustBeRegistred = (contentEngineId == null);
            }
        }

        if (contentEngineId != null) {
            if (contentModel.checkNotModified()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ContentModel '" + contentModel
                            + "' is not modified !");
                }
                resolvedContent = repository.load(contentEngineId);
            }

            if (resolvedContent == null) {
                contentEngineId = null;
                contentModel.setContentEngineId(null);

                if (content != null) {
                    registredContents.remove(content);
                }
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
                            + contentModel.getClass()
                            + "' to IResolvedContentModel !");
                }

            }
        }

        String url = repository.save(resolvedContent, contentModel);

        if (contentEngineMustBeRegistred) {
            contentEngineId = contentModel.getContentEngineId();
            if (contentEngineId != null) {
                registredContents.put(content, contentEngineId);
            }
        }

        if (contentType == null) {
            contentType = IContentType.USER;
        }

        IContentAccessor contentAccessor = new BasicContentAccessor(null,
                contentStorageServletURL + '/' + url, contentType, null);

        contentAccessor.setPathType(contentStorageServletPathType);

        return contentAccessor;
    }

    public IContentAccessor registerRaw(FacesContext facesContext, Object ref,
            IContentInformation information, IContentType contentType) {

        if (contentStorageServletURL == null) {
            LOG
                    .info("ContentStorage is not initialized. (Servlet path is invalid)");

            return ContentAccessorFactory.UNSUPPORTED_CONTENT_ACCESSOR;
        }

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
                    + ref.getClass() + "' to IResolvedContent !");
        }

        String url = getRepository().save(resolvedContent, null);

        if (contentType == null) {
            contentType = IContentType.USER;
        }

        IContentAccessor contentAccessor = new BasicContentAccessor(
                facesContext, contentStorageServletURL + '/' + url,
                contentType, null);

        contentAccessor.setPathType(contentStorageServletPathType);

        return contentAccessor;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ResolvedContentAtRequest extends
            AbstractResolvedContent implements IResolvedContentWrapper,
            Serializable {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -7807317078965658005L;

        private final IContentModel contentModel;

        private final String suffix;

        // private final Map parameters;

        private transient IResolvedContent resolvedContent;

        private transient boolean errorState;

        public ResolvedContentAtRequest(IContentModel contentModel) {
            this.contentModel = contentModel;

            String contentType = (String) contentModel
                    .getAttribute(IContentModel.CONTENT_TYPE_PROPERTY);

            String suffix = (String) contentModel
                    .getAttribute(IContentModel.URL_SUFFIX_PROPERTY);

            if (suffix == null && contentType != null) {
                suffix = ImageAdapterFactory
                        .getSuffixByContentType(contentType);

            } else if (contentType == null && suffix != null) {
                contentType = fileNameMap.getContentTypeFor("x." + suffix);
            }

            this.suffix = suffix;
            // this.parameters = contentModel.getAttributes();
        }

        public String getContentType() {
            // Le contentType peut changer ...
            return getResolvedContent().getContentType();
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

        public void appendHashInformations(StringAppender sa) {
            // Il faut les infos du content model ???

            getResolvedContent().appendHashInformations(sa);
        }

        public synchronized IResolvedContent getResolvedContent() {
            if (errorState) {
                return null;
            }

            if (resolvedContent != null) {
                return resolvedContent;
            }

            try {
                Object wrappedData = contentModel.getWrappedData();

                Map parameters = contentModel.getAttributes();

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

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class Content {
        private static final String REVISION = "$Revision$";

        private final IContentModel contentModel;

        private final IContentInformation information;

        private final IContentType contentType;

        public Content(IContentModel contentModel,
                IContentInformation information, IContentType contentType) {
            this.contentModel = contentModel;
            this.information = information;
            this.contentType = contentType;
        }

        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((contentModel == null) ? 0 : contentModel.hashCode());
            result = prime * result
                    + ((contentType == null) ? 0 : contentType.hashCode());
            result = prime * result
                    + ((information == null) ? 0 : information.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Content other = (Content) obj;
            if (contentModel == null) {
                if (other.contentModel != null)
                    return false;
            } else if (!contentModel.equals(other.contentModel))
                return false;
            if (contentType == null) {
                if (other.contentType != null)
                    return false;
            } else if (!contentType.equals(other.contentType))
                return false;
            if (information == null) {
                if (other.information != null)
                    return false;
            } else if (!information.equals(other.information))
                return false;
            return true;
        }

    }
}
