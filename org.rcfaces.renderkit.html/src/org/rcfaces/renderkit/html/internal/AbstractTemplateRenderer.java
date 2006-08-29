/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractTemplateRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    public boolean getDecodesChildren() {
        return true;
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
    }

    // On le met sur le end, car des clientsDatas ... et autres peuvent survenir
    // ...
    public void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected abstract void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException;
}