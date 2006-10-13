/*
 * $Id$
 */

package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TabbedPaneRenderer extends CardBoxRenderer {
    private static final String REVISION = "$Revision$";

    protected static final String TITLE = "_title";

    protected static final String CONTENT = "_content";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TABBED_PANE;
    }

    protected void renderTabHeader(IHtmlWriter writer, String className)
            throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        TabbedPaneComponent tabbedPaneComponent = (TabbedPaneComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        writer.startElement("TABLE");
        writer.writeAttribute("class", className + TITLE);
        writer.writeAttribute("cellspacing", "0");
        writer.writeAttribute("cellpadding", "0");
        writer.writeln();

        writer.startElement("TR");
        writer.endElement("TR");

        writer.startElement("TR");
        writer.endElement("TR");

        writer.endElement("TABLE");

        writer.startElement("DIV");
        /*
         * String w = tabbedPaneComponent.getWidth(); if (w != null) {
         * htmlWriter.writeAttribute("width", w); }
         */
        writer.writeAttribute("class", className + CONTENT);

        String width = tabbedPaneComponent.getWidth(facesContext);
        String height = tabbedPaneComponent.getHeight(facesContext);

        /*
        if (tabbedPaneComponent.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
            if (width == null || height == null) {
                throw new FacesException(
                        "TabbedPane '"
                                + tabbedPaneComponent.getId()
                                + "' can not have interactiveRender enable without settings of attributes width and height !",
                        null);
            }
        }
        */

        String style = "";
        if (width != null) {
            style += "width:100%;";
        }
        if (height != null) {
            // style += getPixelSize(height, 23) + "px;";
        }
        if (style.length() > 0) {
            writer.writeAttribute("style", style);
        }
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(htmlWriter);
    }

    protected boolean useComponentIdVarAllocation() {
        return true;
    }

}