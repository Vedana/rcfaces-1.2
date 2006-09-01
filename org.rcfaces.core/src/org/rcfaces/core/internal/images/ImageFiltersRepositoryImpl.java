/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.provider.IProvider;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageFiltersRepositoryImpl extends ImageFiltersRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageFiltersRepositoryImpl.class);

    private final Map operationsById = new HashMap(32);

    private final Map validContentTypes = new HashMap(8);

    private final FileNameMap fileNameMap;

    private String imageRepositoryURL;

    public ImageFiltersRepositoryImpl(IProvider provider) {
        super(provider);

        fileNameMap = URLConnection.getFileNameMap();

        /*
         * operationsById.put("disabled", new DisableOperation());
         * operationsById.put("gray", new GrayOperation());
         * operationsById.put("contrast", new ContrastOperation());
         * operationsById.put("brithness", new BrithnessOperation());
         * operationsById.put("colorRescale", new ContrastBrithnessOperation());
         * operationsById.put("hover", new HoverOperation());
         * operationsById.put("selected", new SelectedOperation());
         * operationsById.put("scale", new ScaleOperation());
         * operationsById.put("resize", new ResizeOperation());
         * operationsById.put("setSize", new SetSizeOperation()); //
         * operationsById.put("icon", new IEIconOperation());
         * 
         * for (Iterator it = operationsById.values().iterator(); it.hasNext();) {
         * IImageOperation imageOperation = (IImageOperation) it.next();
         * 
         * imageOperation.configure(Collections.EMPTY_MAP); }
         */
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

    public String formatImageURL(FacesContext facesContext, String filter,
            String url, boolean mainURL, ImageInformation imageInformation) {

        String operationId = filter;
        int pf = operationId.indexOf('(');
        if (pf >= 0) {
            operationId = operationId.substring(0, pf);
        }

        IImageOperation imageOperation = getImageOperation(operationId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Image operation id='" + operationId + "' filter='"
                    + filter + "' => " + imageOperation);
        }

        boolean addFilterServletPath = true;

        String externalContentType = null;
        String internalContentType = null;
        if (imageOperation != null) {
            externalContentType = imageOperation.getExternalContentType();
            internalContentType = imageOperation.getInternalContentType();
        }
        if (externalContentType == null) {
            externalContentType = getContentType(url);
        }
        if (internalContentType == null) {
            internalContentType = externalContentType;
        }

        if (imageInformation != null) {
            imageInformation.setMimeType(externalContentType);
        }

        if (isValidContenType(internalContentType) == false) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Not supported content type '" + internalContentType
                        + "' for url '" + url + "'.");
            }

            if (mainURL == false) {
                return null;
            }

            addFilterServletPath = false;
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        StringAppender sb = new StringAppender(256);
        sb.append(externalContext.getRequestContextPath());

        if (addFilterServletPath) {
            synchronized (this) {
                if (imageRepositoryURL == null) {
                    imageRepositoryURL = ImageFiltersServlet
                            .getImagesRepositoryURI(externalContext
                                    .getApplicationMap());
                }
            }

            sb.append(imageRepositoryURL);
            sb.append('/');

            sb.append(filter);
        }

        String servletPath = externalContext.getRequestServletPath();
        if (servletPath != null) {
            int idx = servletPath.lastIndexOf('/');
            if (idx > 0) {
                sb.append(servletPath.substring(0, idx));
            }
        }

        sb.append('/');
        URLFormCodec.appendURL(sb, url);

        if (imageOperation != null) {
            String suffix = imageOperation.getForceSuffix();
            if (suffix != null) {
                sb.append('.');
                sb.append(suffix);
            }
        }

        String ret = sb.toString();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Format image '" + url + "' with filter '" + filter
                    + "' returns url '" + ret + "'.");
        }

        return ret;
    }

    public String getContentType(String url) {
        int idx = url.lastIndexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        return fileNameMap.getContentTypeFor(url);
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
     * @author Olivier Oeuillot
     * @version $Revision$
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
