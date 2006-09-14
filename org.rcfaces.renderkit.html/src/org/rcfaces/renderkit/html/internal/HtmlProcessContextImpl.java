/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:14  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
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
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private final boolean useScriptCData;

    private final boolean useFlatIdentifier;

    private final char separatorChar;

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

        styleSheetURI = externalContext.getRequestContextPath()
                + cssConfig.getDefaultStyleSheetURI();

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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compute stylesheet uri '" + uri + "' => '" + ret + "'.");
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
