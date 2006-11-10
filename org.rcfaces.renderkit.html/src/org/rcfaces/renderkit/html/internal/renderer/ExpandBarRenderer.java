/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ExpandBarComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExpandBarRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    // private static final String COLLAPSED_BUTTON_IMAGE_URL =
    // "expandBar/arrow_collapsed.gif";

    // private static final String EXPANDED_BUTTON_IMAGE_URL =
    // "expandBar/arrow_expanded.gif";

    private static final String HEAD_FACET_NAME = "head";

    private static final int DEFAULT_BUTTON_IMAGE_WIDTH = 16;

    private static final int DEFAULT_BUTTON_IMAGE_HEIGHT = 16;

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("UL");
        writeCoreAttributes(htmlWriter);
        writeTitle(htmlWriter, expandBarComponent);
        writeHelp(htmlWriter, expandBarComponent);
        writeClientData(htmlWriter, expandBarComponent);

        writeJavaScriptAttributes(htmlWriter);

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);
        writeCssAttributes(htmlWriter, (collapsed) ? "_collapsed" : null,
                CSS_ALL_MASK);

        if (collapsed) {
            htmlWriter.writeAttribute("v:collapsed", "true");
            htmlWriter.writeAttribute("v:className",
                    getStyleClassName(expandBarComponent));
        }

        String accessKey = expandBarComponent.getAccessKey(facesContext);
        if (accessKey != null && accessKey.length() > 0) {
            htmlWriter.writeAttribute("v:accessKey", accessKey);
        }

        String effect = expandBarComponent.getCollapseEffect(facesContext);
        if (effect != null) {
            htmlWriter.writeAttribute("v:effect", effect);
        }

        String groupName = expandBarComponent.getGroupName(facesContext);
        if (groupName != null && groupName.length() > 0) {
            htmlWriter.writeAttribute("v:groupName", groupName);
        }

        htmlWriter.startElement("LI");
        htmlWriter.writeClass(getHeadClassName(htmlWriter));

        writeHeader(htmlWriter);

        htmlWriter.endElement("LI").writeln();

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(htmlWriter);

        boolean asyncRenderEnable = htmlRenderContext.isAsyncRenderEnable();
        if (asyncRenderEnable == false) {
            // ??? return;
        }

        boolean asyncRender = false;

        if (collapsed) {
            if (asyncRenderEnable) {
                if (expandBarComponent.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    htmlWriter.writeAttribute("v:asyncRender", "true");

                    getHtmlRenderContext(htmlWriter)
                            .pushInteractiveRenderComponent(htmlWriter);

                    asyncRender = true;
                }
            }
        }
        setAsyncRenderer(htmlWriter, expandBarComponent, asyncRender);

        htmlWriter.startElement("LI");
        htmlWriter.writeClass(getTBodyClassName(htmlWriter));
        if (collapsed) {
            htmlWriter.writeStyle().writeDisplay("none");
        }

        htmlWriter.startElement("DIV");
        htmlWriter.writeClass(getBodyClassName(htmlWriter));
        if (collapsed) {
            // On masque le LI le Javascript masquera le DIV !
        }

        htmlWriter.enableJavaScript();
    }

    protected String getBodyClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_body";
    }

    protected String getTBodyClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_tbody";
    }

    protected String getHeadClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_head";
    }

    private void writeHeader(IHtmlWriter htmlWriter) throws WriterException {

        IComponentRenderContext componentContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        String buttonId = componentContext.getComponentClientId() + "__button";

        htmlWriter.startElement("INPUT");
        htmlWriter.writeType("IMAGE");
        htmlWriter.writeClass(getInputClassName(htmlWriter));
        htmlWriter.writeId(buttonId);

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);

        htmlWriter.writeAttribute("src", getButtonImage(htmlWriter, collapsed));

        htmlWriter.writeWidth(getButtonImageWidth(htmlWriter));
        htmlWriter.writeHeight(getButtonImageHeight(htmlWriter));

        writeAccessKey(htmlWriter, expandBarComponent);
        writeTabIndex(htmlWriter, expandBarComponent);

        htmlWriter.endElement("INPUT");

        htmlWriter.startElement("LABEL");
        htmlWriter.writeFor(buttonId);
        htmlWriter.writeClass(getLabelClassName(htmlWriter));

        String text = expandBarComponent.getText(facesContext);
        if (text != null && text.length() > 0) {
            HtmlTools.writeSpanAccessKey(htmlWriter, expandBarComponent, text,
                    false);
        }

        htmlWriter.endElement("LABEL");

        UIComponent component = expandBarComponent.getFacet(HEAD_FACET_NAME);
        if (component != null) {

            htmlWriter.startElement("SPAN");
            htmlWriter.writeClass(getFacetClassName(htmlWriter));

            htmlWriter.flush();

            ComponentTools.encodeRecursive(facesContext, component);

            htmlWriter.endElement("SPAN");
        }
    }

    protected String getFacetClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_hfacet";
    }

    protected String getLabelClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_label";
    }

    protected String getInputClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_button";
    }

    protected int getButtonImageWidth(IHtmlWriter htmlWriter) {
        return DEFAULT_BUTTON_IMAGE_WIDTH;
    }

    protected int getButtonImageHeight(IHtmlWriter htmlWriter) {
        return DEFAULT_BUTTON_IMAGE_HEIGHT;
    }

    protected String getButtonImage(IHtmlWriter htmlWriter, boolean collapsed) {

        return getHtmlRenderContext(htmlWriter).getHtmlProcessContext()
                .getStyleSheetURI(BLANK_IMAGE_URL, true);
        /*
         * String imageURL; if (collapsed) { imageURL =
         * COLLAPSED_BUTTON_IMAGE_URL; } else { imageURL =
         * EXPANDED_BUTTON_IMAGE_URL; }
         */
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        // IComponentRenderContext componentContext =
        // writer.getComponentRenderContext();

        // ExpandBarComponent expandBarComponent = (ExpandBarComponent)
        // componentContext.getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");
        htmlWriter.endElement("LI");
        htmlWriter.endElement("UL");

        super.encodeEnd(htmlWriter);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            String old = expandBarComponent.getText(context.getFacesContext());

            if (text.equals(old) == false) {
                expandBarComponent.setText(text);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.TEXT, old, text));
            }
        }

        Boolean collapsed = componentData.getBooleanProperty("collapsed");
        if (collapsed != null) {
            Boolean old = Boolean.valueOf(expandBarComponent
                    .isCollapsed(context.getFacesContext()));

            if (collapsed.equals(old) == false) {
                expandBarComponent.setCollapsed(collapsed.booleanValue());

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.COLLAPSED, old, text));
            }
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.EXPAND_BAR;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentRenderContext
                .getComponent();
        if (expandBarComponent.getCollapseEffect(componentRenderContext
                .getFacesContext()) != null) {

            IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                    htmlWriter).getJavaScriptRenderContext();

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.EXPAND_BAR, "effect");
        }
    }
}