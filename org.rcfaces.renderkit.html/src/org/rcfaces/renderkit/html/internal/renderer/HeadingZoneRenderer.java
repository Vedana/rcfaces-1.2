/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.component.HeadingZoneComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlElements;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.util.HeadingTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HeadingZoneRenderer extends AbstractCssRenderer {

    public final void encodeBegin(IComponentWriter writer)
            throws WriterException {

        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        HeadingZoneComponent headingZoneComponent = (HeadingZoneComponent) writer
                .getComponentRenderContext().getComponent();

        String text = headingZoneComponent.getCaption(writer
                .getComponentRenderContext().getFacesContext());
        if (text != null && text.length() > 0) {
            int ariaLevel = HeadingTools
                    .computeHeadingLevel(headingZoneComponent);

            if (ariaLevel > 0) {
                if (ariaLevel > IHtmlElements.MAX_HEADING_LEVEL) {
                    ariaLevel = IHtmlElements.MAX_HEADING_LEVEL;
                }

                String tagName = IHtmlElements.H_BASE + ariaLevel;
                htmlWriter.startElement(tagName);
                htmlWriter.writeClass("f_headingZone_text");
                htmlWriter.writeText(text);

                htmlWriter.endElement(tagName);
            }

        }

    }

}
