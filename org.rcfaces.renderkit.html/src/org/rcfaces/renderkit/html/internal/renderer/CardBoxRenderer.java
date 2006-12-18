/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

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
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardBoxRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CARD_BOX;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {

        super.encodeBegin(writer);

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
        htmlWriter.writeRole(IAccessibilityRoles.TAB_PANEL);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        CardComponent cardComponent = cardBoxComponent
                .getSelectedCard(facesContext);
        if (cardComponent != null) {
            IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) htmlWriter
                    .getComponentRenderContext().getRenderContext();

            String cardComponentId = htmlRenderContext
                    .getComponentClientId(cardComponent);

            htmlWriter.writeAttribute("v:selectedCard", cardComponentId);
        }

        renderTabHeader(htmlWriter);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(writer);
    }

    protected void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        super.encodeJavaScript(writer);

        writer.writeMethodCall("f_updateCards").writeln(");");
    }

    protected void renderTabHeader(IHtmlWriter writer) throws WriterException {
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

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        CardBoxComponent cardBoxComponent = (CardBoxComponent) componentRenderContext
                .getComponent();

        if (cardBoxComponent.getWidth(facesContext) != null
                && cardBoxComponent.getHeight(facesContext) != null) {
            cssWriter.writeOverflow("hidden");
        }
    }
}