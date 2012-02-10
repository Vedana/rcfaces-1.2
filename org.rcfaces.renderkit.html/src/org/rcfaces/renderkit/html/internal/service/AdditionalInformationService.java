/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.faces.FacesException;
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
public class AdditionalInformationService extends AbtractGridService {

    private static final Log LOG = LogFactory
            .getLog(AdditionalInformationService.class);

    private static final String ADDITIONAL_INFORMATION_SERVICE_VERSION = "1.0.0";

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private void writeAdditionalInformations(FacesContext facesContext,
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
                .getAdditionalInformationsRenderContextState(gridComponent);
        if (states == null) {
            throw new FacesException(
                    "Can not get render context state for additional informations of gridComponent='"
                            + gridComponent + "'");
        }

        IHtmlRenderContext renderContext = HtmlRenderContext
                .restoreRenderContext(facesContext, states[0], true);

        renderContext.pushComponent((UIComponent) gridComponent,
                ((UIComponent) gridComponent).getClientId(facesContext));

        dgr.renderAdditionalInformation(renderContext, pw, gridComponent,
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

        writeAdditionalInformations(facesContext, printWriter,
                (IGridComponent) component, gridRenderer, rowValue, rowIndex);
    }
}
