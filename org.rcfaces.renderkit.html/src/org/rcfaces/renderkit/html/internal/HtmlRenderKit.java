/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Map;

import javax.faces.context.ExternalContext;

/**
 * Non implementé pour l'instant !
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HtmlRenderKit /* extends RenderKit */{
    private static final String REVISION = "$Revision$";

    private static final String HTML_EXTERNAL_CONTEXT_PROPERTY = "org.rcfaces.renderkit.html.HTML_EXTERNAL_CONTEXT";

    public static IHtmlExternalContext getExternalContext(
            ExternalContext externalContext) {

        Map requestMap = externalContext.getRequestMap();
        IHtmlExternalContext htmlExternalContext = (IHtmlExternalContext) requestMap
                .get(HTML_EXTERNAL_CONTEXT_PROPERTY);
        if (htmlExternalContext != null) {
            return htmlExternalContext;
        }

        htmlExternalContext = new HtmlExternalContextImpl(externalContext);
        requestMap.put(HTML_EXTERNAL_CONTEXT_PROPERTY, htmlExternalContext);

        return htmlExternalContext;
    }
}
