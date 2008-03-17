/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.component.JavaScriptCollectorComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptCollectorRenderer extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptCollectorRenderer.class);

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        FacesContext facesContext = htmlRenderContext.getFacesContext();

        JavaScriptCollectorComponent javaScriptCollectorComponent = (JavaScriptCollectorComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        boolean mergeScripts = javaScriptCollectorComponent
                .isMergeScripts(facesContext);

        IJavaScriptRenderContext newJavaScriptRenderContext = new JavaScriptCollectorRenderContext(
                htmlRenderContext.getFacesContext(), mergeScripts);

        htmlRenderContext.pushInteractiveRenderComponent(htmlWriter,
                newJavaScriptRenderContext);
    }

    public IAsyncRenderer getAsyncRenderer(FacesContext facesContext) {
        return null;
    }
}