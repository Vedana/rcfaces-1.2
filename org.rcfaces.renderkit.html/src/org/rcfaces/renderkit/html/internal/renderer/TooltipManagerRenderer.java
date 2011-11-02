/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.component.TooltipManagerComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author jbmeslin@vadana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TooltipManagerRenderer extends AbstractJavaScriptRenderer {


    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IHtmlComponentRenderContext htmlComponentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();
        if (htmlComponentRenderContext.getHtmlRenderContext()
                .getJavaScriptRenderContext().isCollectorMode() == false) {

            htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
            writeHtmlAttributes(htmlWriter);
            writeJavaScriptAttributes(htmlWriter);

            IHtmlComponentRenderContext componentRenderContext = htmlWriter.getHtmlComponentRenderContext();
            
            
            TooltipManagerComponent tooltipManagerComponent = (TooltipManagerComponent) componentRenderContext
                    .getComponent();
            
             int delay =  tooltipManagerComponent.getDelay(componentRenderContext.getFacesContext());
            
            if (delay < 0) {
                htmlWriter.writeAttribute("v:delay", delay);

            }

            htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

            if (true) {
                declareLazyJavaScriptRenderer(htmlWriter);
            }

        } else {
            htmlWriter.enableJavaScript();
        }

        super.encodeEnd(htmlWriter);
    }

    protected void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {
        super.encodeJavaScript(jsWriter);
        
        IHtmlComponentRenderContext componentRenderContext = jsWriter.getHtmlComponentRenderContext();
        
        TooltipManagerComponent tooltipManagerComponent = (TooltipManagerComponent) componentRenderContext
                .getComponent();

        if (jsWriter.getJavaScriptRenderContext().isCollectorMode() == false) {
            return;
        }

        jsWriter.setIgnoreComponentInitialization();

        jsWriter.writeCall(getJavaScriptClassName(), "Get");
        
        int delay =  tooltipManagerComponent.getDelay(componentRenderContext.getFacesContext());
        
        if (delay >= 0) {
        	jsWriter.writeInt(delay);
        }
        
        jsWriter.writeln(");");
     
    }


    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TOOLTIP_MANAGER;
    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return false;
    }
}
