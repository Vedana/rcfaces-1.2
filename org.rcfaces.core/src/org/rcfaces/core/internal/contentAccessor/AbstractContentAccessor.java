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
import org.rcfaces.core.lang.IContentFamily;

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

    private final IContentFamily contentFamily;

    private IContentVersionHandler contentVersionHandler;

    private int pathType;

    protected AbstractContentAccessor(IContentFamily type,
            IContentVersionHandler contentVersionHandler) {
        this(type, null, contentVersionHandler);
    }

    protected AbstractContentAccessor(IContentAccessor contentAccessor) {
        this(contentAccessor.getContentFamily(), contentAccessor,
                contentAccessor.getContentVersionHandler());
    }

    protected AbstractContentAccessor(IContentFamily type,
            IContentAccessor contentAccessor,
            IContentVersionHandler contentVersionHandler) {
        this.parentContentAccessor = contentAccessor;
        this.contentFamily = type;
        this.contentVersionHandler = contentVersionHandler;
    }

    public IContentAccessor getParentAccessor() {
        return parentContentAccessor;
    }

    public final IContentFamily getContentFamily() {
        return contentFamily;
    }

    public IContentVersionHandler getContentVersionHandler() {
        return contentVersionHandler;
    }

    public void setContentVersionHandler(
            IContentVersionHandler contentVersionHandler) {
        this.contentVersionHandler = contentVersionHandler;
    }

    public final String resolveURL(FacesContext facesContext,
            IGeneratedResourceInformation contentInformation,
            IGenerationResourceInformation generationInformation) {

        return resolveURL(facesContext, contentInformation,
                generationInformation, ABSOLUTE_PATH_TYPE | RELATIVE_PATH_TYPE);
    }

    public final String resolveURL(FacesContext facesContext,
            IGeneratedResourceInformation contentInformation,
            IGenerationResourceInformation generationInformation,
            int pathTypeMask) {

        IContentAccessor contentAccessor = ContentAccessorEngine.resolveURL(
                facesContext, this, contentInformation, generationInformation);
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

        int currentPathType = contentAccessor.getPathType();

        if ((pathTypeMask & IContentAccessor.RELATIVE_PATH_TYPE) > 0) {
            if (currentPathType == IContentAccessor.RELATIVE_PATH_TYPE) {
                return resolvedURLs;
            }
        }

        if ((pathTypeMask & IContentAccessor.ABSOLUTE_PATH_TYPE) > 0) {
            if (currentPathType == IContentAccessor.ABSOLUTE_PATH_TYPE) {
                return resolvedURLs;
            }

            if (currentPathType == IContentAccessor.CONTEXT_PATH_TYPE) {
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
        }

        if ((pathTypeMask & IContentAccessor.CONTEXT_PATH_TYPE) > 0) {
            if (currentPathType == IContentAccessor.CONTEXT_PATH_TYPE) {
                return resolvedURLs;
            }

            if (currentPathType == IContentAccessor.ABSOLUTE_PATH_TYPE) {
                // On peut peut-etre retirer le contextPath !

                if (facesContext == null) {
                    facesContext = FacesContext.getCurrentInstance();
                }

                String contextPath = facesContext.getExternalContext()
                        .getRequestContextPath();

                if (resolvedURLs.startsWith(contextPath)) {
                    resolvedURL = resolvedURLs.substring(contextPath.length());
                    if (resolvedURLs.startsWith("/") == false) {
                        resolvedURLs = "/" + resolvedURLs;
                    }

                    return resolvedURLs;
                }

                throw new FacesException(
                        "Absolute path type is not into the context '"
                                + resolvedURLs + "'.");
            }
        }

        throw new FacesException("Incompatible path type (requested=0x"
                + Integer.toHexString(pathTypeMask) + ", pathType=0x"
                + Integer.toHexString(currentPathType) + ", url='"
                + resolvedURL + "')");
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
            if (url.startsWith(CONTEXT_KEYWORD)
                    && (slash < 0 || slash == CONTEXT_KEYWORD.length())) {
                setPathType(CONTEXT_PATH_TYPE);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Macro " + CONTEXT_KEYWORD + " for url '" + url
                            + "'.");
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

        int doubleColon = url.indexOf(IContentAccessor.FILTER_SEPARATOR, colon);
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
        return "[AbstractContentAccessor contentType=" + contentFamily
                + " pathType=" + getPathTypeName(pathType) + " versionHandler="
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