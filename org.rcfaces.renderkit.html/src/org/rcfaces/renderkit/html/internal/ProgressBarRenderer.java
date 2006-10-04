/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ProgressBarComponent;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ProgressBarRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final int BORDER_LEFT_WIDTH = 4;

    private static final int BORDER_RIGHT_WIDTH = 4;

    private static final int BORDER_HEIGHT = 16;

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PROGRESS_BAR;
    }

    // On le met sur le end, car des clientsDatas ... et autres peuvent survenir
    // ...
    public void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        ProgressBarComponent progressBar = (ProgressBarComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        String blankImageURL = htmlRenderContext.getHtmlExternalContext()
                .getStyleSheetURI(BLANK_IMAGE_URL);

        htmlWriter.startElement("TABLE");

        String styleClass = getStyleClassName(htmlWriter
                .getComponentRenderContext());

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        writeProgressBarAttributes(htmlWriter, progressBar);

        htmlWriter.writeAttribute("cellspacing", "0");
        htmlWriter.writeAttribute("cellpadding", "0");

        htmlWriter.startElement("COL");
        htmlWriter.writeAttribute("width", BORDER_LEFT_WIDTH);
        htmlWriter.endElement("COL");
        
        htmlWriter.startElement("COL");
        htmlWriter.writeAttribute("width", "*");
        htmlWriter.endElement("COL");
        
        htmlWriter.startElement("COL");
        htmlWriter.writeAttribute("width", BORDER_RIGHT_WIDTH);
        htmlWriter.endElement("COL");
        
        htmlWriter.writeln();

        htmlWriter.startElement("TBODY");

        htmlWriter.startElement("TR");

        htmlWriter.startElement("TD");
        htmlWriter.writeAttribute("class", styleClass + "_left");

        htmlWriter.startElement("IMG");
        htmlWriter.writeAttribute("width", BORDER_LEFT_WIDTH);
        htmlWriter.writeAttribute("height", BORDER_HEIGHT);
        htmlWriter.writeAttribute("src", blankImageURL);
        htmlWriter.endElement("IMG");

        htmlWriter.endElement("TD");

        htmlWriter.startElement("TD");
        htmlWriter.writeAttribute("class", styleClass + "_mid");
        htmlWriter.writeText(ISgmlWriter.NBSP);
        htmlWriter.endElement("TD");

        htmlWriter.startElement("TD");
        htmlWriter.writeAttribute("class", styleClass + "_right");

        htmlWriter.startElement("IMG");
        htmlWriter.writeAttribute("width", BORDER_RIGHT_WIDTH);
        htmlWriter.writeAttribute("height", BORDER_HEIGHT);
        htmlWriter.writeAttribute("src", blankImageURL);
        htmlWriter.endElement("IMG");

        htmlWriter.endElement("TD");

        htmlWriter.endElement("TR");

        htmlWriter.endElement("TBODY");

        htmlWriter.endElement("TABLE");
    }

    protected void writeProgressBarAttributes(IHtmlWriter htmlWriter,
            ProgressBarComponent progressBar) throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        double minValue = progressBar.getMinimum(facesContext);
        if (minValue != 0.0) {
            htmlWriter.writeAttribute("v:min", Double.toString(minValue));
        }

        double maxValue = progressBar.getMaximum(facesContext);
        if (maxValue != 0.0) {
            htmlWriter.writeAttribute("v:max", Double.toString(maxValue));
        }

        Object value = progressBar.getValue();
        if (value instanceof String) {
            String svalue = (String) value;

            if (svalue.length() > 0 && Character.isDigit(svalue.charAt(0))) {
                value = new Double(svalue);
            }
        }
        if (value instanceof Number) {
            Number num = (Number) value;

            if (num.doubleValue() != 0.0) {
                htmlWriter.writeAttribute("v:value", num.toString());
            }
        }
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        ProgressBarComponent progressBar = (ProgressBarComponent) component;

        Number value = componentData.getNumberProperty("value");
        if (value != null) {
            progressBar.setValue(value);
        }

        Number min = componentData.getNumberProperty("min");
        if (min != null) {
            double old = progressBar.getMinimum(facesContext);

            if (min.doubleValue() != old) {
                progressBar.setMinimum(min.doubleValue());

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.MINIMUM, new Double(old), min));
            }
        }

        Number max = componentData.getNumberProperty("max");
        if (max != null) {
            double old = progressBar.getMaximum(facesContext);

            if (max.doubleValue() != old) {
                progressBar.setMaximum(max.doubleValue());

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.MAXIMUM, new Double(old), max));
            }
        }

    }
}