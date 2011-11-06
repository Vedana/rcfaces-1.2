/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.renderer.AbstractGridRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TooltipService extends AbtractGridService {

    private static final Log LOG = LogFactory
            .getLog(TooltipService.class);


    private void writeTooltips(FacesContext facesContext,
            PrintWriter printWriter, IGridComponent gridComponent,
            AbstractGridRenderer dgr, String rowValue, String rowIndex)
            throws WriterException {

        CharArrayWriter cw = null;
        PrintWriter pw = printWriter;
        if (LOG.isTraceEnabled()) {
            cw = new CharArrayWriter(2000);
            pw = new PrintWriter(cw);
        }

        Object states[] = dgr
                .getTooltipsRenderContextState(gridComponent);

        IHtmlRenderContext renderContext = HtmlRenderContext
                .restoreRenderContext(facesContext, states[0], true);

        renderContext.pushComponent((UIComponent) gridComponent,
                ((UIComponent) gridComponent).getClientId(facesContext));

        dgr.renderTooltip(renderContext, pw, gridComponent,
                RESPONSE_CHARSET, rowValue, rowIndex);

        if (LOG.isTraceEnabled()) {
            pw.flush();

            LOG.trace(cw.toString());

            printWriter.write(cw.toCharArray());
        }
    }


	@Override
	protected void writeElement(FacesContext facesContext,
			PrintWriter printWriter, IGridComponent component,
			AbstractGridRenderer gridRenderer, String rowValue, String rowIndex)
			throws WriterException {
		
		writeTooltips(facesContext, printWriter,
                (IGridComponent) component, gridRenderer, rowValue,
                rowIndex);
		
	}
}
