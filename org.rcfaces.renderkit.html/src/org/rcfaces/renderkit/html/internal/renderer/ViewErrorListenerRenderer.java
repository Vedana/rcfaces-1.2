/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ViewErrorListenerRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IHtmlComponentRenderContext htmlComponentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();
        if (htmlComponentRenderContext.getHtmlRenderContext()
                .getJavaScriptRenderContext().isCollectorMode() == false) {

            htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
            writeHtmlAttributes(htmlWriter);
            writeJavaScriptAttributes(htmlWriter);

            htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

            declareLazyJavaScriptRenderer(htmlWriter);

        } else {
            htmlWriter.enableJavaScript();
        }
    }

    protected void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {
        super.encodeJavaScript(jsWriter);

        if (jsWriter.getJavaScriptRenderContext().isCollectorMode() == false) {
            return;
        }

        jsWriter.setIgnoreComponentInitialization();

        String varName = jsWriter.getJavaScriptRenderContext()
                .allocateVarName();
        jsWriter.setComponentVarName(varName);

        jsWriter.write(varName).write('=').writeCall(getJavaScriptClassName(),
                "f_newInstance").writeln(");");
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.VIEW_ERROR_LISTENER;
    }

    protected boolean encodeEventsInAttributes(IHtmlWriter writer) {
        return writer.getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext().isCollectorMode() == false;
    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return false;
    }
}