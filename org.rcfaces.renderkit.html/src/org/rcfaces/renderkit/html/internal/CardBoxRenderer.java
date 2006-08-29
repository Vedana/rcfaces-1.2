/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.9  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.8  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.7  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.6  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.5  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.3  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.1  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.preference.IComponentPreference;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CardBoxRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CARD_BOX;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        CardBoxComponent cardBoxComponent = (CardBoxComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        IComponentPreference preference = cardBoxComponent
                .getPreference(facesContext);
        if (preference != null) {
            preference.loadPreference(facesContext, cardBoxComponent);
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.enableJavaScript();

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        CardComponent cardComponent = cardBoxComponent
                .getSelectedCard(facesContext);
        if (cardComponent != null) {
            IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) htmlWriter
                    .getComponentRenderContext().getRenderContext();

            String cardComponentId = htmlRenderContext.getComponentId(
                    facesContext, cardComponent);

            htmlWriter.writeAttribute("v:selectedCard", cardComponentId);
        }

        String className = getStyleClassName(componentRenderContext,
                cardBoxComponent);

        renderTabHeader(htmlWriter, className);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(writer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#encodeJavaScript(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        super.encodeJavaScript(writer);

        writer.writeCall(null, "_updateCards").writeln(");");
    }

    protected void renderTabHeader(IHtmlWriter writer, String className)
            throws WriterException {
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        CardBoxComponent cardBoxComponent = (CardBoxComponent) component;

        String selectedId = componentData.getStringProperty("selected");
        if (selectedId != null) {
            ICardIterator it = cardBoxComponent.listCards();
            for (; it.hasNext();) {
                CardComponent card = it.next();

                String cardId = context.getComponentId(card);

                if (selectedId.equals(cardId) == false) {
                    continue;
                }

                cardBoxComponent.select(card);
                break;
            }
        }

        IComponentPreference preference = cardBoxComponent
                .getPreference(facesContext);
        if (preference != null) {
            preference.savePreference(facesContext, cardBoxComponent);
        }
    }
}