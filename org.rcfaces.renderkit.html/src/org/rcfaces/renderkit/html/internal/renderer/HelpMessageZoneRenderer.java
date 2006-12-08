/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.component.HelpMessageZoneComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HelpMessageZoneRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HELP_MESSAGE_ZONE;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        HelpMessageZoneComponent component = (HelpMessageZoneComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String text = component.getText(htmlWriter.getComponentRenderContext()
                .getFacesContext());
        if (text != null) {
            text = ParamUtils.formatMessage(component, text);

            if (text.length() > 0) {
                htmlWriter.writeText(text);
            }
        }

        htmlWriter.endElement("DIV");
        // Done in lazy mode by the HELP package itself
        // see package F_HELP in f_help.js
        // enableJavaScript(htmlWriter);
    }

    /*
     * Pour le client lourd...
     * 
     * protected void encodeJavaScript(IJavaScriptWriter htmlWriter) throws
     * WriterException {
     * 
     * super.encodeJavaScript(htmlWriter); UIComponent component =
     * htmlWriter.getWriter().getComponent(); htmlWriter.write("if (F_HELP)
     * F_HELP.SetHelpMessageZone(");
     * htmlWriter.write(htmlWriter.getComponentVarName()).writeln(");"); }
     */
}