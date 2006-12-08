/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.HyperLinkComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HyperLinkRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeEnd(IComponentWriter writer) throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        HyperLinkComponent component = (HyperLinkComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("A");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (component.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("DISABLED");
        }

        /*
         * Le Javascript s'occupe de ca ! htmlWriter.writeAttribute("href",
         * "javascript:void(0)");
         */

        String text = component.getText(facesContext);
        if (text != null) {
            if (text != null) {
                text = ParamUtils.formatMessage(component, text);
            }

            htmlWriter.writeText(text);
        }

        htmlWriter.endElement("A");

        htmlWriter.enableJavaScript();

        super.encodeEnd(writer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HYPER_LINK;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }
}
