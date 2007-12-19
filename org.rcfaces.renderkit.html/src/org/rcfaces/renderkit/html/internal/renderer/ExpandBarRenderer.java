/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ExpandBarComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.ICollapsableCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
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

    private static final String TITLE_TEXT_ATTRIBUTE = "org.rcfaces.html.EXPAND_BAR_TITLE";

    private static final String HEAD_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "head";

    private static final String BODY_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "body";

    private static final String CONTENT_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "content";

    private static final String LABEL_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "label";

    private static final String BUTTON_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "button";

    private static final String BUTTON_IMAGE_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "image";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.UL);
        writeCoreAttributes(htmlWriter);
        writeTitle(htmlWriter, expandBarComponent);
        writeHelp(htmlWriter, expandBarComponent);
        writeClientData(htmlWriter, expandBarComponent);

        writeJavaScriptAttributes(htmlWriter);

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);
        writeCssAttributes(htmlWriter);

        String normalText = expandBarComponent.getText(facesContext);
        if (normalText != null) {
            normalText = ParamUtils.formatMessage(expandBarComponent,
                    normalText);
        }

        String collapsedText = expandBarComponent
                .getCollapsedText(facesContext);
        if (collapsedText != null) {
            collapsedText = ParamUtils.formatMessage(expandBarComponent,
                    collapsedText);
        }

        String text = normalText;
        if (collapsed) {
            htmlWriter.writeAttribute("v:collapsed", true);

            if (collapsedText != null) {
                text = collapsedText;

                if (normalText != null) {
                    htmlWriter.writeAttribute("v:text", normalText);
                }
            }
        } else if (collapsedText != null) {
            htmlWriter.writeAttribute("v:collapsedText", collapsedText);
        }

        componentContext.setAttribute(TITLE_TEXT_ATTRIBUTE, text);

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

        htmlWriter.startElement(IHtmlWriter.LI);
        htmlWriter.writeId(getHeadId(htmlWriter));
        htmlWriter.writeClass(getHeadClassName(htmlWriter, collapsed));

        writeHeader(htmlWriter);

        htmlWriter.endElement(IHtmlWriter.LI).writeln();

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        boolean asyncRenderEnable = htmlRenderContext.isAsyncRenderEnable();
        if (asyncRenderEnable == false) {
            // ??? return;
        }

        int asyncRender = IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE;
        if (collapsed) {
            if (asyncRenderEnable) {
                asyncRender = htmlRenderContext
                        .getAsyncRenderMode(expandBarComponent);

                if (asyncRender != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    htmlWriter.writeAttribute("v:asyncRender", true);

                    htmlWriter.getHtmlComponentRenderContext()
                            .getHtmlRenderContext()
                            .pushInteractiveRenderComponent(htmlWriter, null);
                }
            }
        }
        setAsyncRenderer(htmlWriter, expandBarComponent, asyncRender);

        htmlWriter.startElement(IHtmlWriter.LI);
        htmlWriter.writeId(getBodyId(htmlWriter));
        htmlWriter.writeClass(getBodyClassName(htmlWriter, collapsed));

        htmlWriter.startElement(IHtmlWriter.DIV);
        htmlWriter.writeId(getContentId(htmlWriter));
        htmlWriter.writeClass(getContentClassName(htmlWriter, collapsed));
        if (collapsed) {
            // On masque le LI le Javascript masquera le DIV !
        }

        htmlWriter.getJavaScriptEnableMode().enableOnInit(); 
        // Il faut positionner les hovers en javascript
    }

    protected String getHeadId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + HEAD_ID_SUFFIX;
    }

    protected String getBodyId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + BODY_ID_SUFFIX;
    }

    protected String getContentId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + CONTENT_ID_SUFFIX;
    }

    protected String getLabelId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + LABEL_ID_SUFFIX;
    }

    protected String computeComponentStyleClass(UIComponent component,
            String classSuffix) {
        classSuffix = super.computeComponentStyleClass(component, classSuffix);

        if (component instanceof ICollapsableCapability) {
            if (((ICollapsableCapability) component).isCollapsed()) {
                classSuffix += "_collapsed";
            }
        }

        return classSuffix;
    }

    protected String getContentClassName(IHtmlWriter writer, boolean collapsed) {
        if (collapsed) {
            return getMainStyleClassName() + "_content_collapsed";
        }
        return getMainStyleClassName() + "_content";
    }

    protected String getBodyClassName(IHtmlWriter writer, boolean collapsed) {
        if (collapsed) {
            return getMainStyleClassName() + "_body_collapsed";
        }
        return getMainStyleClassName() + "_body";
    }

    protected String getHeadClassName(IHtmlWriter writer, boolean collapsed) {
        return getMainStyleClassName() + "_head";
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

    protected String getInputImageClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_image";
    }

    private void writeHeader(IHtmlWriter htmlWriter) throws WriterException {

        IComponentRenderContext componentContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        String buttonClientId = componentContext.getComponentClientId()
                + BUTTON_ID_SUFFIX;

        htmlWriter.startElement(IHtmlWriter.A);
        htmlWriter.writeId(buttonClientId);
        // Il faut le laisser pour le lazy FOCUS
        htmlWriter.writeHRef("javascript:void(0)");
        htmlWriter.writeClass(getInputClassName(htmlWriter));

        htmlWriter.addSubFocusableComponent(buttonClientId);

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);

        htmlWriter.writeWidth(getButtonImageWidth(htmlWriter));
        htmlWriter.writeHeight(getButtonImageHeight(htmlWriter));

        writeAccessKey(htmlWriter, expandBarComponent);
        writeTabIndex(htmlWriter, expandBarComponent);

        htmlWriter.startElement(IHtmlWriter.IMG);
        String buttonImageClientId = componentContext.getComponentClientId()
                + BUTTON_IMAGE_ID_SUFFIX;
        htmlWriter.writeId(buttonImageClientId);
        htmlWriter.writeClass(getInputImageClassName(htmlWriter));

        htmlWriter.writeWidth(getButtonImageWidth(htmlWriter));
        htmlWriter.writeHeight(getButtonImageHeight(htmlWriter));

        IContentAccessor buttonImageAccessor = getButtonImage(htmlWriter,
                collapsed);
        if (buttonImageAccessor != null) {
            String url = buttonImageAccessor.resolveURL(facesContext, null,
                    null);
            if (url != null) {
                htmlWriter.writeSrc(url);
            }
        }

        htmlWriter.endElement(IHtmlWriter.IMG);

        htmlWriter.endElement(IHtmlWriter.A);

        htmlWriter.startElement(IHtmlWriter.LABEL);
        htmlWriter.writeId(getLabelId(htmlWriter));
        htmlWriter.writeFor(buttonClientId);
        htmlWriter.writeClass(getLabelClassName(htmlWriter));
        writeTextDirection(htmlWriter, expandBarComponent);

        String text = (String) htmlWriter.getComponentRenderContext()
                .getAttribute(TITLE_TEXT_ATTRIBUTE);
        if (text != null) {
            HtmlTools.writeSpanAccessKey(htmlWriter, expandBarComponent, text,
                    false);
        }

        htmlWriter.endElement(IHtmlWriter.LABEL);

        /*
         * UIComponent component = expandBarComponent.getFacet(HEAD_FACET_NAME);
         * if (component != null) {
         * 
         * htmlWriter.startElement(IHtmlWriter.DIV");
         * htmlWriter.writeClass(getFacetClassName(htmlWriter));
         * 
         * htmlWriter.flush();
         * 
         * ComponentTools.encodeRecursive(facesContext, component);
         * 
         * htmlWriter.endElement(IHtmlWriter.DIV"); }
         */
    }

    protected int getButtonImageWidth(IHtmlWriter htmlWriter) {
        return DEFAULT_BUTTON_IMAGE_WIDTH;
    }

    protected int getButtonImageHeight(IHtmlWriter htmlWriter) {
        return DEFAULT_BUTTON_IMAGE_HEIGHT;
    }

    protected IContentAccessor getButtonImage(IHtmlWriter htmlWriter,
            boolean collapsed) {

        return htmlWriter.getHtmlComponentRenderContext()
                .getHtmlRenderContext().getHtmlProcessContext()
                .getStyleSheetContentAccessor(BLANK_IMAGE_URL,
                        IContentType.IMAGE);
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

        htmlWriter.endElement(IHtmlWriter.DIV);
        htmlWriter.endElement(IHtmlWriter.LI);
        htmlWriter.endElement(IHtmlWriter.UL);

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

        String collapsedText = componentData.getStringProperty("collapsedText");
        if (collapsedText != null) {
            String old = expandBarComponent.getCollapsedText(context
                    .getFacesContext());

            if (collapsedText.equals(old) == false) {
                expandBarComponent.setCollapsedText(collapsedText);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.COLLAPSED_TEXT, old, collapsedText));
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

    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentRenderContext
                .getComponent();
        if (expandBarComponent.getCollapseEffect(componentRenderContext
                .getFacesContext()) != null) {

            javaScriptRenderContext.appendRequiredClass(
                    JavaScriptClasses.EXPAND_BAR, "effect");
        }
    }
}