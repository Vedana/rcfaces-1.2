/**
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.17  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.16  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
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
 * Revision 1.13  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.12  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.11  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.10  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.9  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.8  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.7  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.6  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextAreaComponent;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class TextAreaRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * public void encodeBegin(IWriter writer) throws WriterException { }
     */

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        TextAreaComponent textAreaComponent = (TextAreaComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement("TEXTAREA");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);

        int col = textAreaComponent.getColumnNumber(facesContext);
        if (col > 0) {
            htmlWriter.writeAttribute("cols", col);
        }
        int row = textAreaComponent.getRowNumber(facesContext);
        if (row > 0) {
            htmlWriter.writeAttribute("rows", row);
        }

        writeTextEntryAttributes(htmlWriter);

        String txt = textAreaComponent.getText(facesContext);
        if (txt != null) {
            htmlWriter.writeText(txt);
        }

        htmlWriter.endElement("TEXTAREA");

        if (textAreaComponent.isRequired()) {
            // Il nous faut le javascript, car c'est un traitement javascript !
            htmlWriter.enableJavaScript();
        }
    }

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_AREA;
    }
}