/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.image.ImageContentInformation;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.AbstractContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentStorage.ContentStorageServlet;
import org.rcfaces.core.internal.contentStorage.IContentStorageEngine;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.util.ClassLocator;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageContentAccessorHandlerImpl extends
        ImageContentAccessorHandler {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageContentAccessorHandlerImpl.class);

    private final Map operationsById = new HashMap(32);

    private final Map validContentTypes = new HashMap(8);

    private final FileNameMap fileNameMap;

    private boolean contentAccessorAvailable;

    private RcfacesContext rcfacesContext;

    public ImageContentAccessorHandlerImpl() {
        fileNameMap = URLConnection.getFileNameMap();
    }

    public String getId() {
        return "ImageContentAccessorHandler";
    }

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        rcfacesContext = RcfacesContext.getInstance(facesContext);

        contentAccessorAvailable = ContentStorageServlet
                .getContentStorageBaseURI(facesContext.getExternalContext()
                        .getApplicationMap()) != null;

        if (contentAccessorAvailable == false) {
            LOG.info("ImageContentAccessorImpl is not available");

        } else {
            LOG.debug("ImageContentAccessorImpl available");
        }
    }

    public void configureRules(Digester digester) {
        super.configureRules(digester);

        digester.addRule("rcfaces-config/image-operations/operation",
                new Rule() {
                    private static final String REVISION = "$Revision$";

                    public void begin(String namespace, String name,
                            Attributes attributes) throws Exception {

                        super.digester.push(new OperationBean());
                    }

                    public void end(String namespace, String name)
                            throws Exception {
                        OperationBean operationBean = (OperationBean) super.digester
                                .pop();

                        declareOperation(operationBean);
                    }
                });
        digester.addBeanPropertySetter(
                "rcfaces-config/image-operations/operation/operation-id", "id");
        digester.addBeanPropertySetter(
                "rcfaces-config/image-operations/operation/operation-name",
                "name");
        digester.addBeanPropertySetter(
                "rcfaces-config/image-operations/operation/operation-class",
                "className");
        digester
                .addBeanPropertySetter(
                        "rcfaces-config/image-operations/operation/operation-force-suffix",
                        "forceSuffix");
        digester
                .addBeanPropertySetter(
                        "rcfaces-config/image-operations/operation/operation-external-contentType",
                        "externalContentType");
        digester
                .addBeanPropertySetter(
                        "rcfaces-config/image-operations/operation/operation-internal-contentType",
                        "internalContentType");
    }

    private void declareOperation(OperationBean operationBean) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialize imageOperation '" + operationBean.getId()
                    + "', name='" + operationBean.getName() + "', classname='"
                    + operationBean.getClassName() + "'.");
        }

        Class clazz;
        try {
            clazz = ClassLocator.load(operationBean.getClassName(), null,
                    FacesContext.getCurrentInstance());

        } catch (ClassNotFoundException ex) {
            throw new FacesException("Can not load class '"
                    + operationBean.getClassName()
                    + "' specified by imageOperation id='"
                    + operationBean.getId() + "'.", ex);
        }

        if (IImageOperation.class.isAssignableFrom(clazz) == false) {
            throw new FacesException("Class '" + operationBean.getClassName()
                    + "' specified by imageOperation id='"
                    + operationBean.getId()
                    + "' must implement interface 'IImageOperation'.");
        }

        if ((clazz.getModifiers() & Modifier.ABSTRACT) > 0) {
            throw new FacesException("Class '" + operationBean.getClassName()
                    + "' specified by imageOperation id='"
                    + operationBean.getId() + "' is abstract !");
        }

        Constructor constructor;

        try {
            constructor = clazz.getConstructor(null);

        } catch (NoSuchMethodException ex) {
            throw new FacesException(
                    "Can not get constructor for imageOperation id='"
                            + operationBean.getId() + "' class='"
                            + operationBean.getClassName() + "'.", ex);
        }

        IImageOperation operation;
        try {
            operation = (IImageOperation) constructor.newInstance(null);

        } catch (Throwable ex) {
            throw new FacesException("Can not instanciate class '"
                    + operationBean.getClassName()
                    + "' specified by imageOperation id='"
                    + operationBean.getId() + "' using constructor '"
                    + constructor + "'.", ex);
        }

        if (operationBean.getName() != null) {
            operation.setName(operationBean.getName());
        }

        if (operationBean.getExternalContentType() != null) {
            operation.setExternalContentType(operationBean
                    .getExternalContentType());
        }

        if (operationBean.getInternalContentType() != null) {
            operation.setInternalContentType(operationBean
                    .getInternalContentType());
        }

        if (operationBean.getForceSuffix() != null) {
            operation.setForceSuffix(operationBean.getForceSuffix());
        }

        LOG.trace("addImageOperation(" + operationBean.getId() + ","
                + operation + ")");

        operation.configure(operationBean.getParameterMap());

        operationsById.put(operationBean.getId(), operation);
    }

    public IImageOperation getImageOperation(String operationId) {
        IImageOperation imageOperation = (IImageOperation) operationsById
                .get(operationId);

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Operation id='" + operationId + "' => "
                            + imageOperation);
        }

        return imageOperation;
    }

    public IContentAccessor formatImageURL(FacesContext facesContext,
            IFiltredImageAccessor contentAccessor,
            ImageContentInformation imageContentInformation) {

        String filter = contentAccessor.getFilter();
        String operationId = filter;
        String parameters = null;
        int pf = operationId.indexOf('(');
        if (pf >= 0) {
            int pfe = operationId.lastIndexOf(')');
            if (pfe < 0) {
                parameters = operationId.substring(pf + 1);
            } else {
                parameters = operationId.substring(pf + 1, pfe);
            }

            operationId = operationId.substring(0, pf);
        }

        IImageOperation imageOperation = getImageOperation(operationId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Image operation id='" + operationId + "' filter='"
                    + contentAccessor.getFilter() + "' => " + imageOperation);
        }

        String resourceURL = (String) contentAccessor.getContentRef();
        int resourcePathType = contentAccessor.getPathType();

        if (resourceURL == null) {
            IContentAccessor rootAccessor = contentAccessor.getParentAccessor();
            resourceURL = (String) rootAccessor.getContentRef();
            resourcePathType = rootAccessor.getPathType();

        }

        String externalContentType = null;
        String internalContentType = null;
        if (imageOperation != null) {
            externalContentType = imageOperation.getExternalContentType();
            internalContentType = imageOperation.getInternalContentType();
        }
        if (externalContentType == null) {
            externalContentType = getContentType(resourceURL);
        }
        if (internalContentType == null) {
            internalContentType = externalContentType;
        }

        if (imageContentInformation != null) {
            imageContentInformation.setContentType(externalContentType);
        }

        if (isValidContenType(internalContentType) == false) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Not supported content type '" + internalContentType
                        + "' for url '" + contentAccessor + "'.");
            }

            if (contentAccessor.getParentAccessor() == null) {
                return null;
            }
        }

        IContentStorageEngine contentStorageEngine = rcfacesContext
                .getContentStorageEngine();

        // Il nous faut un path en relatif !
        switch (resourcePathType) {
        case IContentAccessor.EXTERNAL_PATH_TYPE:
            throw new FacesException(
                    "Can not make operation on an external URL !");

        case IContentAccessor.CONTEXT_PATH_TYPE:
            break;

        case IContentAccessor.ABSOLUTE_PATH_TYPE:
            String relativeURL = AbstractContentAccessor.removeContextPath(
                    facesContext, resourceURL);

            if (relativeURL == null) {
                throw new FacesException(
                        "Can not transform Absolute path to Context path !");
            }

            resourceURL = relativeURL;
            break;

        case IContentAccessor.RELATIVE_PATH_TYPE:
            IProcessContext processContext = AbstractProcessContext
                    .getProcessContext(facesContext);

            resourceURL = processContext.getAbsolutePath(resourceURL, false);
            break;

        default:
            throw new FacesException("Invalid state !");
        }

        String versionId = null;
        if (Constants.RESOURCE_CONTENT_VERSION_SUPPORT) {
            versionId = rcfacesContext.getResourceVersionHandler()
                    .getResourceVersion(facesContext, resourceURL, null);
        }

        IContentAccessor newContentAccessor = contentStorageEngine
                .registerContentModel(facesContext,
                        new ImageOperationContentModel(resourceURL,
                                externalContentType, imageOperation
                                        .getForceSuffix(), versionId,
                                operationId, parameters, contentAccessor
                                        .getAttributes(), imageOperation),
                        imageContentInformation, contentAccessor.getType());

        // pas de versionning dans ce content Accessor !

        return newContentAccessor;
    }

    public String getContentType(String url) {
        int idx = url.lastIndexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        return fileNameMap.getContentTypeFor(url);
    }

    public boolean isProviderEnabled() {
        return contentAccessorAvailable;
    }

    public boolean isValidContenType(String contentType) {
        Boolean valid;

        synchronized (validContentTypes) {
            valid = (Boolean) validContentTypes.get(contentType);

            if (valid == null) {
                Iterator it = ImageIO.getImageWritersByMIMEType(contentType);
                valid = (it.hasNext()) ? Boolean.TRUE : Boolean.FALSE;

                validContentTypes.put(contentType, valid);
            }
        }

        return valid.booleanValue();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class OperationBean {
        private static final String REVISION = "$Revision$";

        private String name;

        private String id;

        private String className;

        private String forceSuffix;

        private String externalContentType;

        private String internalContentType;

        private Map parameters = new HashMap();

        public String getClassName() {
            return className;
        }

        public Map getParameterMap() {
            return parameters;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExternalContentType() {
            return externalContentType;
        }

        public void setExternalContentType(String forceContentType) {
            this.externalContentType = forceContentType;
        }

        public String getInternalContentType() {
            return internalContentType;
        }

        public void setInternalContentType(String forceContentType) {
            this.internalContentType = forceContentType;
        }

        public String getForceSuffix() {
            return forceSuffix;
        }

        public void setForceSuffix(String forceSuffix) {
            this.forceSuffix = forceSuffix;
        }

        public void addParameter(String name, String value) {
            parameters.put(name, value);
        }
    }
}
