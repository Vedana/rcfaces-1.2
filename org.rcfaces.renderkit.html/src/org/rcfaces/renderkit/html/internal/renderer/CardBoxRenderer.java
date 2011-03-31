/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.preference.GridPreferences;
import org.rcfaces.core.preference.IComponentPreferences;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardBoxRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String TABBED_PANE_JSF12_PROPERTY = "org.rcfaces.renderkit.html.TABBED_PANE_JSF1_2";

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

        IComponentPreferences preference = cardBoxComponent
                .getPreferences(facesContext);
        if (preference != null) {
            preference.loadPreferences(facesContext, cardBoxComponent);
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        // htmlWriter.enableJavaScript(); // Pourquoi ?

        htmlWriter.startElement(IHtmlWriter.DIV);

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

            htmlWriter.writeAttributeNS("selectedCard", cardComponentId);
        }

        renderTabHeader(htmlWriter);
    }

    protected static void setCardBoxJSF12_Generation(
            IRenderContext renderContext) {

        renderContext.setAttribute(TABBED_PANE_JSF12_PROPERTY, Boolean.TRUE);
    }

    protected static boolean isCardBoxJSF12_Generation(
            IRenderContext renderContext) {
        return Boolean.TRUE.equals(renderContext
                .getAttribute(TABBED_PANE_JSF12_PROPERTY));
    }

    protected String getWAIRole() {
        return IAccessibilityRoles.TAB_PANEL;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement(IHtmlWriter.DIV);

        htmlWriter.getJavaScriptEnableMode().enableOnInit();

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

        String selectedId = componentData
                .getStringProperty(Properties.SELECTED);
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

        IComponentPreferences preferences = cardBoxComponent
                .getPreferences(facesContext);

        if (preferences == null && cardBoxComponent.isPreferencesSetted()) {
            preferences = new GridPreferences();

            cardBoxComponent.setPreferences(preferences);
        }

        if (preferences != null) {
            preferences.savePreferences(facesContext, cardBoxComponent);
        }
    }

    protected void addUnlockProperties(Set unlockedProperties) {
        super.addUnlockProperties(unlockedProperties);

        unlockedProperties.add(Properties.SELECTED);
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
            cssWriter.writeOverflow(ICssWriter.HIDDEN);
        }
    }

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties
                .addAttributes(null, new String[] { "selectedCard" });
    }
}