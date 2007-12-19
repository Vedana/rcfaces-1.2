/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.TabComponent;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TabRenderer extends CardRenderer {
    private static final String REVISION = "$Revision$";

    private static final String TAB = "_tab";

    protected void writeCardAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        super.writeCardAttributes(htmlWriter);

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        FacesContext facesContext = htmlRenderContext.getFacesContext();

        TabComponent tab = (TabComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        if (htmlRenderContext
                .containsAttribute(TabbedPaneRenderer.TABBED_PANE_JSF12_PROPERTY)) {

            htmlWriter.writeAttribute("v:tabbedPaneId", tab.getTabbedPane()
                    .getClientId(facesContext));

            Object value = tab.getValue();
            String clientValue = null;
            if (value instanceof String) {
                clientValue = (String) value;

            } else if (value != null) {
                clientValue = convertValue(facesContext, tab, value);
            }

            if (clientValue != null) {
                htmlWriter.writeAttribute("v:value", clientValue);
            }

            if (isCardSelected(tab)) {
                htmlWriter.writeAttribute("v:selected", true);
            }

            String text = tab.getText(facesContext);
            if (text != null) {
                text = ParamUtils.formatMessage(tab, text);

                htmlWriter.writeAttribute("v:text", text);
            }

            String accessKey = tab.getAccessKey(facesContext);
            if (accessKey != null) {
                htmlWriter.writeAttribute("v:accessKey", accessKey);
            }

            if (tab.isDisabled(facesContext)) {
                htmlWriter.writeAttribute("v:disabled", true);
            }

            IContentAccessors contentAccessors = tab
                    .getImageAccessors(facesContext);

            if (contentAccessors instanceof IImageAccessors) {
                IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;

                IContentAccessor contentAccessor = imageAccessors
                        .getImageAccessor();

                if (contentAccessor != null) {
                    htmlWriter.writeAttribute("v:imageURL", contentAccessor
                            .resolveURL(facesContext, null, null));
                }

                if (imageAccessors instanceof IStatesImageAccessors) {
                    IStatesImageAccessors statesImageAccessors = (IStatesImageAccessors) imageAccessors;

                    contentAccessor = statesImageAccessors
                            .getDisabledImageAccessor();
                    if (contentAccessor != null) {
                        htmlWriter.writeAttribute("v:disabledImageURL",
                                contentAccessor.resolveURL(facesContext, null,
                                        null));
                    }

                    contentAccessor = statesImageAccessors
                            .getHoverImageAccessor();
                    if (contentAccessor != null) {
                        htmlWriter.writeAttribute("v:hoverImageURL",
                                contentAccessor.resolveURL(facesContext, null,
                                        null));
                    }

                    contentAccessor = statesImageAccessors
                            .getSelectedImageAccessor();
                    if (contentAccessor != null) {
                        htmlWriter.writeAttribute("v:selectedImageURL",
                                contentAccessor.resolveURL(facesContext, null,
                                        null));
                    }
                }
            }
        }
    }

    protected String getDefaultCardStyleClassPrefix() {
        return JavaScriptClasses.TABBED_PANE;
    }

    protected String getCardStyleClassSuffix() {
        return TAB;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TAB;
    }

    protected void declareCard(IJavaScriptWriter js,
            CardComponent cardComponent, String tabbedPaneClientId,
            boolean selected) throws WriterException {

        boolean declare[] = new boolean[1];
        String var = js.getJavaScriptRenderContext().allocateComponentVarId(
                tabbedPaneClientId, declare);
        if (declare[0]) {
            js.write("var ").write(var).write('=').writeCall("f_core",
                    "GetElementByClientId").writeString(tabbedPaneClientId)
                    .writeln(", document, true);");
        }

        TabComponent tab = (TabComponent) cardComponent;
        // TabbedPaneComponent tabbedPane = tab.getTabbedPane();

        IHtmlWriter writer = js.getWriter();

        // IHtmlRenderContext htmlRenderContext =
        // writer.getHtmlComponentRenderContext().getHtmlRenderContext();

        FacesContext facesContext = js.getFacesContext();

        String tadComponentId = writer.getComponentRenderContext()
                .getComponentClientId();

        js.writeCall(var, "f_declareCard");

        IObjectLiteralWriter objectLiteralWriter = js.writeObjectLiteral(false);

        if (js.getComponentRenderContext().getRenderContext()
                .containsAttribute(
                        TabbedPaneRenderer.TABBED_PANE_JSF12_PROPERTY)) {
            objectLiteralWriter.writeProperty("_titleGenerated").writeBoolean(
                    true);
        }

        objectLiteralWriter.writeProperty("_id").writeString(tadComponentId);

        Object value = tab.getValue();
        String clientValue = null;
        if (value instanceof String) {
            clientValue = (String) value;

        } else if (value != null) {
            clientValue = convertValue(facesContext, cardComponent, value);
        }

        if (clientValue != null) {
            objectLiteralWriter.writeProperty("_value")
                    .writeString(clientValue);
        }

        if (selected) {
            objectLiteralWriter.writeProperty("_selected").writeBoolean(true);
        }

        String text = tab.getText(facesContext);
        if (text != null) {
            text = ParamUtils.formatMessage(tab, text);

            objectLiteralWriter.writeProperty("_text").writeString(text);
        }

        String accessKey = tab.getAccessKey(facesContext);
        if (accessKey != null) {
            objectLiteralWriter.writeProperty("_accessKey").writeString(
                    accessKey);
        }

        if (tab.isDisabled(facesContext)) {
            objectLiteralWriter.writeProperty("_disabled").writeBoolean(true);
        }

        IContentAccessors contentAccessors = tab
                .getImageAccessors(facesContext);

        if (contentAccessors instanceof IImageAccessors) {
            IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;

            IContentAccessor contentAccessor = imageAccessors
                    .getImageAccessor();

            if (contentAccessor != null) {
                objectLiteralWriter.writeProperty("_imageURL").writeString(
                        contentAccessor.resolveURL(facesContext, null, null));
            }

            if (imageAccessors instanceof IStatesImageAccessors) {
                IStatesImageAccessors statesImageAccessors = (IStatesImageAccessors) imageAccessors;

                contentAccessor = statesImageAccessors
                        .getDisabledImageAccessor();
                if (contentAccessor != null) {
                    objectLiteralWriter.writeProperty("_disabledImageURL")
                            .writeString(
                                    contentAccessor.resolveURL(facesContext,
                                            null, null));
                }

                contentAccessor = statesImageAccessors.getHoverImageAccessor();
                if (contentAccessor != null) {
                    objectLiteralWriter.writeProperty("_hoverImageURL")
                            .writeString(
                                    contentAccessor.resolveURL(facesContext,
                                            null, null));
                }

                contentAccessor = statesImageAccessors
                        .getSelectedImageAccessor();
                if (contentAccessor != null) {
                    objectLiteralWriter.writeProperty("_selectedImageURL")
                            .writeString(
                                    contentAccessor.resolveURL(facesContext,
                                            null, null));
                }
            }
        }

        objectLiteralWriter.end();

        js.writeln(");");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TabComponent tabComponent = (TabComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            String old = tabComponent.getText(facesContext);
            if (text.equals(old) == false) {
                tabComponent.setText(text);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.TEXT, old, text));
            }
        }
    }
}
