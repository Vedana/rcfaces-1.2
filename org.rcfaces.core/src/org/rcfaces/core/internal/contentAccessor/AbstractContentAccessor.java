/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Collections;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractContentAccessor implements IContentAccessor {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractContentAccessor.class);

    private final IContentAccessor parentContentAccessor;

    private final IContentType type;

    private IContentVersionHandler contentVersionHandler;

    private int pathType;

    protected AbstractContentAccessor(IContentType type,
            IContentVersionHandler contentVersionHandler) {
        this(type, null, contentVersionHandler);
    }

    protected AbstractContentAccessor(IContentAccessor contentAccessor) {
        this(contentAccessor.getType(), contentAccessor, contentAccessor
                .getContentVersionHandler());
    }

    protected AbstractContentAccessor(IContentType type,
            IContentAccessor contentAccessor,
            IContentVersionHandler contentVersionHandler) {
        this.parentContentAccessor = contentAccessor;
        this.type = type;
        this.contentVersionHandler = contentVersionHandler;
    }

    public IContentAccessor getParentAccessor() {
        return parentContentAccessor;
    }

    public final IContentType getType() {
        return type;
    }

    public IContentVersionHandler getContentVersionHandler() {
        return contentVersionHandler;
    }

    public void setContentVersionHandler(
            IContentVersionHandler contentVersionHandler) {
        this.contentVersionHandler = contentVersionHandler;
    }

    public final String resolveURL(FacesContext facesContext,
            IContentInformation contentInformation,
            IFilterProperties filterProperties) {

        IContentAccessor contentAccessor = ContentAccessorEngine.resolveURL(
                facesContext, this, contentInformation, filterProperties);
        if (contentAccessor == null) {
            return null;
        }

        Object resolvedURL = contentAccessor.getContentRef();
        if ((resolvedURL instanceof String) == false) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Resolved URL is not a String: " + resolvedURL
                        + " (pathType="
                        + getPathTypeName(contentAccessor.getPathType()) + ")");
            }
            return null;
        }

        String resolvedURLs = (String) resolvedURL;

        if (LOG.isDebugEnabled()) {
            LOG.debug("Resolved URL: " + resolvedURLs + " (pathType="
                    + getPathTypeName(contentAccessor.getPathType()) + ")");
        }

        if (contentAccessor.getPathType() == IContentAccessor.CONTEXT_PATH_TYPE) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            String contextPath = facesContext.getExternalContext()
                    .getRequestContextPath();
            if (contextPath.endsWith("/")) {
                if (resolvedURLs.startsWith("/")) {
                    return contextPath + resolvedURLs.substring(1);
                }

                return contextPath + resolvedURLs;
            }

            if (resolvedURLs.startsWith("/")) {
                return contextPath + resolvedURLs;
            }

            return contextPath + "/" + resolvedURLs;
        }

        return (String) resolvedURL;
    }

    public Object getAttribute(String attributeName) {
        if (parentContentAccessor != null) {
            return parentContentAccessor.getAttribute(attributeName);
        }

        return null;
    }

    public Map getAttributes() {
        if (parentContentAccessor != null) {
            return parentContentAccessor.getAttributes();
        }

        return Collections.EMPTY_MAP;
    }

    public int getPathType() {
        if (pathType > 0) {
            return pathType;
        }

        if (parentContentAccessor != null) {
            return parentContentAccessor.getPathType();
        }

        return 0;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }

    protected final String resolvePath(FacesContext facesContext, String url) {
        if (url == null || url.length() < 1) {
            throw new FacesException("Invalid url '" + url + "'.");
        }

        int slash = url.indexOf('/');
        if (url.length() > 1 && url.charAt(0) == '$') {
            if (url.startsWith(CONTEXT_KEYWORD) && (slash < 0 || slash == CONTEXT_KEYWORD.length())) {
                setPathType(CONTEXT_PATH_TYPE);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Macro "+CONTEXT_KEYWORD+" for url '" + url + "'.");
                }

                if (slash > 0) {
                    return url.substring(slash + 1);
                }

                return "";
            }

            // Invalid macro
            if (LOG.isDebugEnabled()) {
                LOG.debug("Invalid macro type for '" + url + "'.");
            }

            setPathType(UNDEFINED_PATH_TYPE);
            return "";
        }

        // C'est soit absolute, soit context
        if (slash == 0) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Return absolute path type for '" + url + "'.");
            }
            setPathType(ABSOLUTE_PATH_TYPE);

            return url;
        }

        int colon = url.indexOf(':');
        if (colon < 0) {
            // Ca commence pas par un '/' et y a pas de ':'
            if (LOG.isDebugEnabled()) {
                LOG.debug("Return relative path type for '" + url + "'.");
            }

            setPathType(RELATIVE_PATH_TYPE);
            return url;
        }
        if (colon == 0) {
            // Invalide
            if (LOG.isDebugEnabled()) {
                LOG.debug("Invalid path type for '" + url + "'.");
            }
            setPathType(UNDEFINED_PATH_TYPE);

            return url;
        }

        int doubleColon = url.indexOf("::", colon);
        if (doubleColon > 0 && (slash < 0 || slash > doubleColon)
                && (colon == doubleColon)) {
            // Y a un doubleColon
            // un slash n'est pas avant
            // un colon seul n'est pas avant

            if (LOG.isDebugEnabled()) {
                LOG.debug("Filter path type for '" + url + "'.");
            }
            setPathType(FILTER_PATH_TYPE);
            return url;
        }

        if (colon > 0 && (slash < 0 || slash > colon)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("External path type for '" + url + "'.");
            }
            setPathType(EXTERNAL_PATH_TYPE);

            return url;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Relative path type (default) for '" + url + "'.");
        }
        setPathType(RELATIVE_PATH_TYPE);
        return url;
    }

    public static String removeContextPath(FacesContext facesContext, String url) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        String requestContextPath = facesContext.getExternalContext()
                .getRequestContextPath();

        if (url.startsWith(requestContextPath) == false) {
            return null;
        }

        return url.substring(requestContextPath.length());
    }

    public String toString() {
        return "[AbstractContentAccessor contentType=" + type + " pathType="
                + getPathTypeName(pathType) + " versionHandler="
                + contentVersionHandler + " root=" + parentContentAccessor
                + "]";
    }

    public static final String getPathTypeName(int pathType) {
        switch (pathType) {
        case IContentAccessor.UNDEFINED_PATH_TYPE:
            return "undefined";

        case IContentAccessor.ABSOLUTE_PATH_TYPE:
            return "absolute";

        case IContentAccessor.CONTEXT_PATH_TYPE:
            return "context";

        case IContentAccessor.EXTERNAL_PATH_TYPE:
            return "external";

        case IContentAccessor.RELATIVE_PATH_TYPE:
            return "relative";

        case IContentAccessor.FILTER_PATH_TYPE:
            return "filter";
        }

        return "*** Invalid (" + pathType + ") ***";
    }
}