/*
 * $Id$
 *
 * $Log$
 * Revision 1.3  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.18  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.17  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.16  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.15  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.14  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.13  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.12  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.11  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.10  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.9  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.8  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        // En dernier car les clientDatas / converter ne sont peut �tre pas
        // encore positionn�s !
        TextComponent textComponent = (TextComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("LABEL");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String ac = textComponent.getFor();
        if (ac != null) {
            // On peut pas calculer la véritable ID car le composant est peut
            // etre pas encore présent.
            // Il faut le calculer coté Javascript

            IComponentRenderContext componentRenderContext = htmlWriter
                    .getComponentRenderContext();

            IRenderContext renderContext = componentRenderContext
                    .getRenderContext();

            String forId = renderContext
                    .computeBrotherComponentClientId(componentRenderContext
                            .getFacesContext(), textComponent, ac);

            if (forId != null) {
                htmlWriter.writeAttribute("for", forId);
            }
        }

        if (writeText(htmlWriter, textComponent)) {
            // Un accessKey est postionné !

            htmlWriter.enableJavaScript();
        }

        htmlWriter.endElement("LABEL");

        super.encodeEnd(htmlWriter);
    }

    protected boolean writeText(IHtmlWriter htmlWriter,
            TextComponent textComponent) throws WriterException {
        String text = textComponent.getText(htmlWriter
                .getComponentRenderContext().getFacesContext());
        if (text == null || text.trim().length() < 1) {
            return false;
        }

        return HtmlTools.writeSpanAccessKey(htmlWriter, textComponent, text,
                true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT;
    }
}