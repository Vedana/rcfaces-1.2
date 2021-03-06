/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.Writer;

import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

public interface IAdditionalInformationGridRenderer {

    Object[] getAdditionalInformationsRenderContextState(
            IGridComponent gridComponent);

    void renderAdditionalInformation(IHtmlRenderContext renderContext,
            Writer pw, IGridComponent gridComponent, String responseCharset,
            String rowValue, String rowIndex) throws WriterException;

}
