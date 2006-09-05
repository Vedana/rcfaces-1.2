/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
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
 */
package org.rcfaces.core.provider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IURLRewritingProvider extends IProvider {

    String URL_REWRITING_PROVIDER_ID = "org.rcfaces.core.URL_REWRITING_PROVIDER";

    int IMAGE_URL_TYPE = 1;

    int HELP_URL_TYPE = 2;

    int SCRIPT_URL_TYPE = 3;

    int STYLE_URL_TYPE = 4;

    int RESERVED_TYPE = 1000;

    String computeURL(FacesContext facesContext, UIComponent component,
            int type, String attributeName, String attributeValue,
            String rootURL, IURLRewritingInformation rewritingInformation);

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IURLRewritingInformation {
        IURLRewritingInformation getParent();

        String getRootURL();

        String getContentType();
    }
}
