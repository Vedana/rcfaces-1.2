/*
 * $Id$
 */

package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TabbedPaneRenderer extends CardBoxRenderer {
    private static final String REVISION = "$Revision$";

    protected static final String TITLE_CLASSNAME = "_title";

    protected static final String CONTENT_CLASSNAME = "_content";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TABBED_PANE;
    }

    protected void renderTabHeader(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TabbedPaneComponent tabbedPaneComponent = (TabbedPaneComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement(IHtmlWriter.TABLE);

        htmlWriter.writeRole(IAccessibilityRoles.TAB);

        htmlWriter.writeClass(getTitleClassName(htmlWriter));
        htmlWriter.writeCellPadding(0);
        htmlWriter.writeCellSpacing(0);
        htmlWriter.writeln();

        htmlWriter.startElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.TR);

        htmlWriter.startElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.TR);

        htmlWriter.endElement(IHtmlWriter.TABLE);

        htmlWriter.startElement(IHtmlWriter.DIV);
        /*
         * String w = tabbedPaneComponent.getWidth(); if (w != null) {
         * htmlWriter.writeAttribute("width", w); }
         */
        htmlWriter.writeClass(getContentClassName(htmlWriter));

        String width = tabbedPaneComponent.getWidth(facesContext);
        String height = tabbedPaneComponent.getHeight(facesContext);

        /*
         * if (tabbedPaneComponent.getAsyncRenderMode(facesContext) !=
         * IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) { if (width ==
         * null || height == null) { throw new FacesException( "TabbedPane '" +
         * tabbedPaneComponent.getId() + "' can not have interactiveRender
         * enable without settings of attributes width and height !", null); } }
         */

        ICssWriter cssWriter = htmlWriter.writeStyle(16);
        if (width != null) {
            cssWriter.writeWidth("100%");
        }
        if (height != null) {
            // style += getPixelSize(height, 23) + "px;";
        }
    }

    protected String getTitleClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + TITLE_CLASSNAME;
    }

    protected String getContentClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + CONTENT_CLASSNAME;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement(IHtmlWriter.DIV);

        super.encodeEnd(htmlWriter);
    }

    protected boolean useComponentIdVarAllocation() {
        return true;
    }

}