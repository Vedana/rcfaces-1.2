/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.context.ExternalContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.renderkit.AbstractExternalContext;
import org.rcfaces.renderkit.html.internal.css.ICssConfig;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HtmlExternalContextImpl extends AbstractExternalContext implements
        IHtmlExternalContext {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(HtmlExternalContextImpl.class);

    // On encode pas car c'est lié au contexte utilisateur !
    private static final boolean ENCODE_URI = false;

    private String baseHREF;

    private String baseURI;

    private String styleSheetURI;

    private final boolean useScriptCData;

    private final boolean useFlatIdentifier;

    private final char separatorChar;

    private final boolean useMetaContentScriptType;

    private final boolean useMetaContentStyleType;

    private boolean debugMode;

    private boolean profilerMode;

    public HtmlExternalContextImpl(ExternalContext externalContext) {
        super(externalContext);

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

    public final String getStyleSheetURI(String uri) {
        String ret = null;
        if (uri != null) {
            StringAppender u = new StringAppender(styleSheetURI,
                    uri.length() + 2);

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
        } else {
            ret = styleSheetURI;
        }

        ret = getBaseURI(ret);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compute stylesheet uri '" + uri + "' => '" + ret + "'.");
        }

        return ret;
    }

    public final String getBaseURI() {
        if (baseURI != null) {
            return baseURI;
        }

        String contextPath = externalContext.getRequestContextPath();
        baseURI = contextPath;

        if (baseURI.endsWith("/")) {
            baseURI.substring(0, baseURI.length() - 1);
        }

        if (baseURI.equals(baseHREF)) {
            baseURI = "";
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Set baseURI to '" + baseURI + "' (contextPath='"
                    + contextPath + "').");
        }

        return baseURI;
    }

    public String getBaseURI(String uri) {
        String ret;
        if (uri == null) {
            ret = getBaseURI();

        } else {
            StringAppender u = new StringAppender(getBaseURI(),
                    uri.length() + 2);

            if (uri != null && uri.length() > 0) {
                if (uri.charAt(0) != '/') {
                    u.append('/');

                } else if (u.charAt(u.length() - 1) == '/') {
                    u.setLength(u.length() - 1);
                }

                u.append(uri);
            } else {
                u.append('/');
            }

            ret = u.toString();
        }

        if (ENCODE_URI) {
            ret = externalContext.encodeResourceURL(ret);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compute uri '" + uri + "' => '" + ret + "'.");
        }

        return ret;
    }

    public boolean isFlatIdentifierEnabled() {
        return useFlatIdentifier;
    }

    static final char getHtmlSeparatorChar(ExternalContext externalContext) {

        Map applicationMap = externalContext.getInitParameterMap();

        String separatorChar = (String) applicationMap
                .get(HTML_SEPARATOR_CHAR_PARAMETER);

        if (separatorChar != null && separatorChar.length() > 0) {
            return separatorChar.charAt(0);
        }

        return NamingContainer.SEPARATOR_CHAR;
    }

    public char getNamingSeparatorChar() {
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

    public String getBaseHREF() {
        return baseHREF;
    }

    public void changeBaseHREF(String baseHREF) {
        if (baseHREF != null && baseHREF.length() > 0) {
            if (baseHREF.endsWith("/")) {
                baseHREF = baseHREF.substring(0, baseHREF.length() - 1);
            }
            if (baseHREF.startsWith("/") == false) {
                baseHREF = "/" + baseHREF;
            }
        }

        this.baseHREF = baseHREF;
        baseURI = null; // On recalcule !
    }
}
