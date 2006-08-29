/*
 * $Id$
 * 
 * $Log$
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

import org.rcfaces.core.internal.renderkit.IExternalContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IHtmlExternalContext extends IExternalContext {

    String HTML_FLAT_IDENTIFIER_PARAMETER = Constants.getPackagePrefix()
            + ".FLAT_IDENTIFIER";

    String HTML_SEPARATOR_CHAR_PARAMETER = Constants.getPackagePrefix()
            + ".SEPARATOR_CHAR";

    String USE_META_CONTENT_SCRIPT_TYPE_PARAMETER = Constants
            .getPackagePrefix()
            + ".USE_META_CONTENT_SCRIPT_TYPE";

    String USE_META_CONTENT_STYLE_TYPE_PARAMETER = Constants.getPackagePrefix()
            + ".USE_META_CONTENT_STYLE_TYPE";

    String USE_SCRIPT_CDATA_PARAMETER = Constants.getPackagePrefix()
            + ".USE_SCRIPT_CDATA";

    String DEBUG_MODE_APPLICATION_PARAMETER = Constants.getPackagePrefix()
            + ".client.DEBUG_MODE";

    String PROFILER_MODE_APPLICATION_PARAMETER = Constants.getPackagePrefix()
            + ".client.PROFILER_MODE";

    String getBaseURI();

    String getBaseURI(String uri);

    boolean isFlatIdentifierEnabled();

    String getStyleSheetURI(String uri);

    boolean useMetaContentScriptType();

    boolean useMetaContentStyleType();

    boolean useScriptCData();

    void changeBaseHREF(String base);
}
