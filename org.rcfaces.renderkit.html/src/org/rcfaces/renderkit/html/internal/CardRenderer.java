/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.9  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
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
 * Revision 1.4  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
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
 * Revision 1.7  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.5  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.4  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.3  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.2  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.1  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardRenderer extends AbstractCssRenderer implements IAsyncRenderer {
    private static final String REVISION = "$Revision$";

    private static final IJavaScriptComponent CARD_JAVASCRIPT_COMPONENT = new IJavaScriptComponent() {
        private static final String REVISION = "$Revision$";

        public void initializeJavaScript(IJavaScriptWriter javaScriptWriter)
                throws WriterException {
            javaScriptWriter.getJavaScriptRenderContext()
                    .initializeJavaScriptDocument(javaScriptWriter);
        }

        public void initializeJavaScriptComponent(
                IJavaScriptWriter javaScriptWriter) {
        }

        public void releaseJavaScript(IJavaScriptWriter javaScriptWriter) {
        }

        public void initializePendingComponents(IJavaScriptWriter writer) {
        }
    };

    private static final String CARD_SUFFIX = "_card";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        CardComponent cardComponent = (CardComponent) writer
                .getComponentRenderContext().getComponent();
        CardBoxComponent cardBoxComponent = cardComponent.getCardBox();

        String cardClassName = getCardStyleClass(facesContext, cardComponent);

        CardComponent selectedCard = cardBoxComponent.getSelectedCard();
        boolean selected = false;
        if (selectedCard == null) {
            cardBoxComponent.select(cardComponent);
            selected = true;

        } else if (selectedCard == cardComponent) {
            selected = true;
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(htmlWriter);

        htmlRenderContext.removeJavaScriptWriter(htmlWriter);

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        // Il faut calculer les dependances
        javascriptRenderContext.computeRequires(htmlWriter, this);

        IJavaScriptWriter js = htmlRenderContext.getJavaScriptWriter(
                htmlWriter, CARD_JAVASCRIPT_COMPONENT);

        initializePendingComponents(js);

        String cardBoxVarId = htmlRenderContext.getComponentClientId(
                facesContext, cardBoxComponent);

        String var;
        boolean declare[] = new boolean[1];
        var = javascriptRenderContext.allocateComponentVarId(cardBoxVarId,
                declare);
        if (declare[0]) {
            js.write("var ").write(var).write('=').writeCall("f_core",
                    "GetElementById").writeString(cardBoxVarId).writeln(
                    ", document, true);");
        }

        boolean asyncRender = declareCard(js, cardComponent, var, selected);

        js.end();

        htmlWriter.writeln();

        htmlWriter.startElement("DIV");

        /*
         * String w = tabbedPane.getWidth(); if (w != null) {
         * writer.writeAttribute("width", w); }
         */

        if (asyncRender) {
            htmlWriter.writeAttribute("v:asyncRender", "true");
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        htmlWriter.writeAttribute("class", cardClassName);
        
        ICssWriter cssWriter = new CssWriter(htmlWriter, 128);
        if (selected == false) {
            cssWriter.writeDisplay("none");
        }

        if (cardBoxComponent.getWidth(facesContext) != null) {
            cssWriter.writeWidth("100%");
        }

        if (cardBoxComponent.getHeight(facesContext) != null) {
            cssWriter.writeHeight("100%");
        }

        String textAlignement = cardComponent.getTextAlignment(facesContext);
        if (textAlignement != null) {
            cssWriter.writeTextAlign(textAlignement);
        }

        String verticalAlignement = cardComponent
                .getVerticalAlignment(facesContext);
        if (verticalAlignement != null) {
            cssWriter.writeVerticalAlign(verticalAlignement);
        }

        cssWriter.close();
    }

    protected String getCardStyleClass(FacesContext facesContext,
            CardComponent cardComponent) {

        String className = cardComponent.getStyleClass(facesContext);
        if (className != null) {
            return className;
        }

        CardBoxComponent cardBoxComponent = cardComponent.getCardBox();
        className = cardBoxComponent.getStyleClass(facesContext);
        if (className == null) {
            className = getDefaultCardStyleClassPrefix();
        }

        String suffix = getCardStyleClassSuffix();
        if (suffix == null || suffix.length() < 1) {
            return className;
        }

        return className + suffix;
    }

    protected String getDefaultCardStyleClassPrefix() {
        return JavaScriptClasses.CARD_BOX;
    }

    protected String getCardStyleClassSuffix() {
        return CARD_SUFFIX;
    }

    protected boolean declareCard(IJavaScriptWriter js,
            CardComponent cardComponent, String var, boolean selected)
            throws WriterException {

        CardBoxComponent cardBoxComponent = cardComponent.getCardBox();

        IHtmlWriter writer = js.getWriter();

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(writer);
        FacesContext facesContext = js.getFacesContext();

        js.writeCall(var, "f_declareCard").writeString(
                writer.getComponentRenderContext().getComponentClientId());

        int pred = 0;
        if (selected) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }

            js.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        boolean asyncRender = false;
        if (selected == false) {
            if (htmlRenderContext.isAsyncRenderEnable()) {
                if (cardBoxComponent.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    getHtmlRenderContext(writer)
                            .pushInteractiveRenderComponent(writer);

                    asyncRender = true;
                }
            }
        }

        setAsyncRenderer(writer, cardComponent, asyncRender);

        js.writeln(");");

        return asyncRender;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(writer);
    }

    protected void encodeEndJavaScript(IJavaScriptWriter writer) {
        // Pas de JAVASCRIPT !
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CARD;
    }
}
