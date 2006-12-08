/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptComponent;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardRenderer extends AbstractCssRenderer implements IAsyncRenderer {
    private static final String REVISION = "$Revision$";

    private static final String CARD_SUFFIX = "_card";

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

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        // super.encodeBegin(writer); // ???

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

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        htmlRenderContext.removeJavaScriptWriter(htmlWriter);

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        // Il faut calculer les dependances
        javascriptRenderContext.computeRequires(htmlWriter, this);

        IJavaScriptWriter js = htmlRenderContext.getJavaScriptWriter(
                htmlWriter, CARD_JAVASCRIPT_COMPONENT);

        initializePendingComponents(js);

        String cardBoxVarId = htmlRenderContext
                .getComponentClientId(cardBoxComponent);

        String var;
        boolean declare[] = new boolean[1];
        var = javascriptRenderContext.allocateComponentVarId(cardBoxVarId,
                declare);
        if (declare[0]) {
            js.write("var ").write(var).write('=').writeCall("f_core",
                    "GetElementById").writeString(cardBoxVarId).writeln(
                    ", document, true);");
        }

        int asyncRender = declareCard(js, cardComponent, var, selected);

        js.end();

        htmlWriter.writeln();

        htmlWriter.startElement("DIV");
        htmlWriter.writeRole("tabcontent");

        /*
         * String w = tabbedPane.getWidth(); if (w != null) {
         * writer.writeAttribute("width", w); }
         */

        if (asyncRender != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
            htmlWriter.writeAttribute("v:asyncRender", "true");
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        htmlWriter.writeAttribute("class", cardClassName);

        ICssWriter cssWriter = htmlWriter.writeStyle(128);
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
    }

    protected String getCardStyleClass(FacesContext facesContext,
            CardComponent cardComponent) {

        String className = cardComponent.getStyleClass(facesContext);
        if (className != null) {
            return className;
        }

        // CardBoxComponent cardBoxComponent = cardComponent.getCardBox();
        className = getDefaultCardStyleClassPrefix();

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

    protected int declareCard(IJavaScriptWriter js,
            CardComponent cardComponent, String var, boolean selected)
            throws WriterException {

        CardBoxComponent cardBoxComponent = cardComponent.getCardBox();

        IHtmlWriter writer = js.getWriter();

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        IHtmlRenderContext htmlRenderContext = componentRenderContext
                .getHtmlRenderContext();
        FacesContext facesContext = js.getFacesContext();

        js.writeCall(var, "f_declareCard").writeString(
                componentRenderContext.getComponentClientId());

        int pred = 0;
        if (selected) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }

            js.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        int asyncRender = IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE;
        if (selected == false) {
            if (htmlRenderContext.isAsyncRenderEnable()) {
                asyncRender = cardBoxComponent.getAsyncRenderMode(facesContext);

                if (asyncRender != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    writer.getHtmlComponentRenderContext()
                            .getHtmlRenderContext()
                            .pushInteractiveRenderComponent(writer);
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
