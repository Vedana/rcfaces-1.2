/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.PagerComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PagerRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PAGER;
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        PagerComponent pagerComponent = (PagerComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");
        if (pagerComponent.getWidth(facesContext) == null
                && pagerComponent.getHeight(facesContext) == null) {
            htmlWriter.writeStyle().writeDisplay("inline");
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String forValue = pagerComponent.getFor(facesContext);
        if (forValue == null) {
            throw new WriterException("'for' attribute must be specified !",
                    null, pagerComponent);
        }

        htmlWriter.writeAttribute("v:for", forValue);

        String message = pagerComponent.getMessage(facesContext);
        if (message != null) {
            message = ParamUtils.formatMessage(pagerComponent, message);

            htmlWriter.writeAttribute("v:message", message);

            String zeroResultMessage = pagerComponent
                    .getZeroResultMessage(facesContext);
            if (zeroResultMessage != null) {
                zeroResultMessage = ParamUtils.formatMessage(pagerComponent,
                        zeroResultMessage);

                htmlWriter.writeAttribute("v:zeroResultMessage",
                        zeroResultMessage);
            }

            String oneResultMessage = pagerComponent
                    .getOneResultMessage(facesContext);
            if (oneResultMessage != null) {
                oneResultMessage = ParamUtils.formatMessage(pagerComponent,
                        oneResultMessage);

                htmlWriter.writeAttribute("v:oneResultMessage",
                        oneResultMessage);
            }

            String manyResultsMessage = pagerComponent
                    .getManyResultsMessage(facesContext);
            if (manyResultsMessage != null) {
                manyResultsMessage = ParamUtils.formatMessage(pagerComponent,
                        manyResultsMessage);

                htmlWriter.writeAttribute("v:manyResultMessage",
                        manyResultsMessage);
            }
        }

        String noPagedMessage = pagerComponent.getNoPagedMessage(facesContext);
        if (noPagedMessage != null) {
            noPagedMessage = ParamUtils.formatMessage(pagerComponent,
                    noPagedMessage);

            htmlWriter.writeAttribute("v:noPagedMessage", noPagedMessage);
        }

        htmlWriter.endElement("DIV");

        htmlWriter.enableJavaScript();
    }
}
