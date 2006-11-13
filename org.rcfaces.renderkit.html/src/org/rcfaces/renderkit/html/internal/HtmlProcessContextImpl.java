/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.renderkit.html.internal.css.ICssConfig;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlProcessContextImpl extends AbstractProcessContext implements
        IHtmlProcessContext {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(HtmlProcessContextImpl.class);

    private String styleSheetURI;

    private String styleSheetURIWithContextPath;

    private final boolean useScriptCData;

    private final boolean useFlatIdentifier;

    private final String separatorChar;

    private final boolean useMetaContentScriptType;

    private final boolean useMetaContentStyleType;

    private boolean debugMode;

    private boolean profilerMode;

    public HtmlProcessContextImpl(FacesContext facesContext) {
        super(facesContext);

        ExternalContext externalContext = facesContext.getExternalContext();

        Map applicationMap = externalContext.getInitParameterMap();

        useMetaContentScriptType = "false"
                .equalsIgnoreCase((String) applicationMap
                        .get(USE_META_CONTENT_SCRIPT_TYPE_PARAMETER)) == false;

        useMetaContentStyleType = "false"
                .equalsIgnoreCase((String) applicationMap
                        .get(USE_META_CONTENT_STYLE_TYPE_PARAMETER)) == false;

        useScriptCData = "false".equalsIgnoreCase((String) applicationMap
                .get(USE_SCRIPT_CDATA_PARAMETER)) == false;

        useFlatIdentifier = "true".equalsIgnoreCase((String) applicationMap
                .get(HTML_FLAT_IDENTIFIER_PARAMETER));

        debugMode = "true".equalsIgnoreCase((String) applicationMap
                .get(DEBUG_MODE_APPLICATION_PARAMETER));

        profilerMode = "true".equalsIgnoreCase((String) applicationMap
                .get(DEBUG_MODE_APPLICATION_PARAMETER));

        separatorChar = getHtmlSeparatorChar(externalContext);

        ICssConfig cssConfig = StylesheetsServlet.getConfig(this);

        styleSheetURI = cssConfig.getDefaultStyleSheetURI();
        styleSheetURIWithContextPath = externalContext.getRequestContextPath()
                + styleSheetURI;

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Initialize htmlRenderExternalContext useMetaContentScriptType="
                            + useMetaContentScriptType
                            + ", useScriptCData="
                            + useScriptCData
                            + ", useFlatIdentifier="
                            + useFlatIdentifier
                            + ", separatorChar='"
                            + separatorChar + "'.");
        }
    }

    public IContentAccessor getStyleSheetContentAccessor(String uri,
            IContentType contentType) {
        String url = getStyleSheetURI(uri, false);
        if (url == null) {
            return null;
        }

        IContentAccessor contentAccessor = new BasicContentAccessor(
                getFacesContext(), url, contentType, null);

        contentAccessor.setPathType(IContentAccessor.CONTEXT_PATH_TYPE);

        return contentAccessor;
    }

    public final String getStyleSheetURI(String uri, boolean containsContextPath) {
        String ret = null;
        if (uri != null) {
            StringAppender u = new StringAppender(styleSheetURIWithContextPath
                    .length()
                    + uri.length() + 2);

            if (containsContextPath) {
                u.append(styleSheetURIWithContextPath);

            } else {
                u.append(styleSheetURI);
            }

            if (uri != null && uri.length() > 0) {
                if (uri.startsWith("/") == false) {
                    u.append('/');

                } else if (u.charAt(u.length() - 1) != '/') {
                    u.append('/');
                }
                u.append(uri);
            } else {
                u.append('/');
            }

            ret = u.toString();

        } else if (containsContextPath) {
            ret = styleSheetURIWithContextPath;

        } else {
            ret = styleSheetURI;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compute stylesheet uri '" + uri
                    + "' (containsContextPath=" + containsContextPath
                    + ") => '" + ret + "'.");
        }

        return ret;
    }

    public boolean isFlatIdentifierEnabled() {
        return useFlatIdentifier;
    }

    static final String getHtmlSeparatorChar(ExternalContext externalContext) {

        Map applicationMap = externalContext.getInitParameterMap();

        String separatorChar = (String) applicationMap
                .get(HTML_SEPARATOR_CHAR_PARAMETER);

        if (separatorChar != null && separatorChar.length() > 0) {
            return separatorChar;
        }

        return null; // NamingContainer.SEPARATOR_CHAR;
    }

    public String getNamingSeparator() {
        return separatorChar;
    }

    public boolean useMetaContentScriptType() {
        return useMetaContentScriptType;
    }

    public boolean useMetaContentStyleType() {
        return useMetaContentStyleType;
    }

    public boolean useScriptCData() {
        return useScriptCData;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public boolean getProfilerMode() {
        return profilerMode;
    }

    public static IHtmlProcessContext getHtmlProcessContext(
            FacesContext facesContext) {

        IHtmlProcessContext htmlProcessContext = (IHtmlProcessContext) getProcessContext(facesContext);
        if (htmlProcessContext != null) {
            return htmlProcessContext;
        }

        htmlProcessContext = new HtmlProcessContextImpl(facesContext);
        setProcessContext(htmlProcessContext);

        return htmlProcessContext;
    }
}
