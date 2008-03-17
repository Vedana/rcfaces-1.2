/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.AbstractCompositeContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.AbstractContentAccessor;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorsRegistryImpl;
import org.rcfaces.core.internal.contentAccessor.FiltredContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.contentAccessor.IFiltredContentAccessor;
import org.rcfaces.core.internal.contentStorage.ContentStorageServlet;
import org.rcfaces.core.internal.contentStorage.IContentStorageEngine;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.style.Constants;
import org.rcfaces.core.internal.style.IStyleContentAccessorHandler;
import org.rcfaces.core.internal.style.IStyleOperation;
import org.rcfaces.core.model.IContentModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleContentAccessorHandler extends
        AbstractCompositeContentAccessorHandler implements
        IStyleContentAccessorHandler {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(StyleContentAccessorHandler.class);

    private static final String CSS_PARSER_ENABLED = Constants
            .getPackagePrefix()
            + ".MERGE_STYLE_FILES";

    private final Map operationsById = new HashMap(32);

    private final FileNameMap fileNameMap;

    private ICssParser cssParser = null;

    private boolean contentAccessorAvailable;

    private RcfacesContext rcfacesContext;

    public StyleContentAccessorHandler() {
        fileNameMap = URLConnection.getFileNameMap();

        operationsById.put("merge", new MergeLinkedStylesOperation());
    }

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        rcfacesContext = RcfacesContext.getInstance(facesContext);

        if ("true".equalsIgnoreCase(facesContext.getExternalContext()
                .getInitParameter(CSS_PARSER_ENABLED))) {

            cssParser = CssParserFactory.getCssParser();

            if (cssParser != null) {
                ((ContentAccessorsRegistryImpl) rcfacesContext
                        .getContentAccessorRegistry())
                        .declareContentAccessorHandler(IContentType.STYLE, this);
            }
        }

        contentAccessorAvailable = ContentStorageServlet
                .getContentStorageBaseURI(facesContext.getExternalContext()
                        .getApplicationMap()) != null;

        if (contentAccessorAvailable == false) {
            LOG.info("StyleContentAccessor is not available");

        } else {
            LOG.debug("StyleContentAccessor available");
        }
    }

    public String getId() {
        return "StyleContentAccessor";
    }

    public IContentAccessor handleContent(FacesContext facesContext,
            IContentAccessor contentAccessor,
            IContentInformation[] contentInformation,
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

        IFiltredContentAccessor modifiedContentAccessor = null;

        int idx = url.indexOf(IContentAccessor.FILTER_SEPARATOR);
        String filter = url.substring(0, idx);

        if (idx == url.length() - 2) { // Filtre tout seul !
            throw new FacesException("You can not specify a filter only.");
        }

        String newURL = url.substring(idx
                + IContentAccessor.FILTER_SEPARATOR.length());

        modifiedContentAccessor = new FiltredContentAccessor(filter,
                new BasicContentAccessor(facesContext, newURL, contentAccessor,
                        IContentAccessor.UNDEFINED_PATH_TYPE));

        IContentAccessor formattedContentAccessor = formatStyleURL(
                facesContext, modifiedContentAccessor);

        if (LOG.isDebugEnabled()) {
            LOG.debug("formattedContentAccessor=" + formattedContentAccessor);
        }

        return formattedContentAccessor;
    }

    public IContentAccessor formatStyleURL(FacesContext facesContext,
            IFiltredContentAccessor contentAccessor) {

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

        IStyleOperation styleOperation = getStyleOperation(operationId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Style operation id='" + operationId + "' filter='"
                    + contentAccessor.getFilter() + "' => " + styleOperation);
        }

        String resourceURL = (String) contentAccessor.getContentRef();
        int resourcePathType = contentAccessor.getPathType();

        if (resourceURL == null) {
            IContentAccessor rootAccessor = contentAccessor.getParentAccessor();
            resourceURL = (String) rootAccessor.getContentRef();
            resourcePathType = rootAccessor.getPathType();
        }

        String contentType = getContentType(resourceURL);

        if (isValidContenType(contentType) == false) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Not supported content type '" + contentType
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
        if (org.rcfaces.core.internal.Constants.RESOURCE_CONTENT_VERSION_SUPPORT) {
            versionId = rcfacesContext.getResourceVersionHandler()
                    .getResourceVersion(facesContext, resourceURL, null);
        }

        IContentModel contentModel = new CssOperationContentModel(resourceURL,
                contentType, versionId, operationId, parameters,
                contentAccessor.getAttributes(), styleOperation, cssParser);

        IContentAccessor newContentAccessor = contentStorageEngine
                .registerContentModel(facesContext, contentModel, null,
                        contentAccessor.getType());

        // pas de versionning dans ce content Accessor !

        return newContentAccessor;
    }

    public String getContentType(String url) {
        int idx = url.lastIndexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        String typeMime = fileNameMap.getContentTypeFor(url);
        if (typeMime != null) {
            return typeMime;
        }

        if (url.toLowerCase().endsWith(".css")) {
            return "text/css";
        }

        return null;
    }

    public boolean isProviderEnabled() {
        return contentAccessorAvailable;
    }

    public boolean isValidContenType(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("text/css");
    }

    public IStyleOperation getStyleOperation(String operationId) {
        IStyleOperation styleOperation = (IStyleOperation) operationsById
                .get(operationId);

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Operation id='" + operationId + "' => "
                            + styleOperation);
        }

        return styleOperation;
    }
}
