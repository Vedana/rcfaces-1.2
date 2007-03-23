/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.SpinnerComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SpinnerRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    protected static final int SPINNER_BUTTON_WIDTH = 16;

    protected static final int SPINNER_BUTTON_HEIGHT = 10;

    protected boolean isNameEqualsId() {
        return false;
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        SpinnerComponent spinner = (SpinnerComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement("TABLE");

        htmlWriter.writeCellPadding(0);
        htmlWriter.writeCellSpacing(0);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeValidatorAttributes(htmlWriter);
        writeSpinnerAttributes(htmlWriter);

        htmlWriter.startElement("COL");

        int spinnerButtonWidth = getSpinnerButtonWidth();

        int inputWidth = -1;

        String width = spinner.getWidth(facesContext);
        if (width == null) {
            htmlWriter.writeWidth("1*");
        } else {
            inputWidth = computeSize(width, 0, -spinnerButtonWidth);

            if (inputWidth > 0) {
                htmlWriter.writeWidth(inputWidth);
            }
        }

        htmlWriter.endElement("COL");

        htmlWriter.startElement("COL");
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.endElement("COL");

        htmlWriter.startElement("TR");
        htmlWriter.writeVAlign("middle");

        htmlWriter.startElement("TD");
        if (inputWidth > 0) {
            htmlWriter.writeWidth(inputWidth);
        }

        htmlWriter.startElement("INPUT");
        if (inputWidth > 0) {
            htmlWriter.writeStyle().writeWidth(inputWidth + "px");
        }
        htmlWriter.writeClass(getInputClassName(htmlWriter));
        writeInputAttributes(htmlWriter);
        writeTextEntryAttributes(htmlWriter);
        writeValueAttributes(htmlWriter);
        htmlWriter.endElement("INPUT");

        htmlWriter.endElement("TD");

        htmlWriter.startElement("TD");
        htmlWriter.writeWidth(spinnerButtonWidth);

        boolean disabled = spinner.isDisabled(htmlWriter
                .getComponentRenderContext().getFacesContext());

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();
        String blankImageURL = htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetURI(BLANK_IMAGE_URL, true);

        int spinnerButtonHeight = getSpinnerButtonHeight();

        htmlWriter.startElement("IMG");
        htmlWriter.writeClass(getUpButtonClassName(htmlWriter, disabled));
        htmlWriter.writeSrc(blankImageURL);
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.writeHeight(spinnerButtonHeight);
        htmlWriter.endElement("IMG");

        htmlWriter.startElement("IMG");
        htmlWriter.writeClass(getDownButtonClassName(htmlWriter, disabled));
        htmlWriter.writeSrc(blankImageURL);
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.writeHeight(spinnerButtonHeight);
        htmlWriter.endElement("IMG");

        htmlWriter.endElement("TD");

        htmlWriter.endElement("TR");

        htmlWriter.endElement("TABLE");

        htmlWriter.enableJavaScript();

    }

    protected String getWAIRole() {
        return IAccessibilityRoles.SPIN_BUTTON;
    }

    protected int getSpinnerButtonWidth() {
        return SPINNER_BUTTON_WIDTH;
    }

    protected int getSpinnerButtonHeight() {
        return SPINNER_BUTTON_HEIGHT;
    }

    protected String getInputClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName() + "_input";
    }

    protected String getUpButtonClassName(IHtmlWriter htmlWriter,
            boolean disabled) {
        String className = getMainStyleClassName() + "_up";
        if (disabled) {
            className += "_disabled";
        }

        return className;
    }

    protected String getDownButtonClassName(IHtmlWriter htmlWriter,
            boolean disabled) {
        String className = getMainStyleClassName() + "_down";
        if (disabled) {
            className += "_disabled";
        }

        return className;
    }

    protected void writeSpinnerAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        SpinnerComponent spinnerComponent = (SpinnerComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        if (spinnerComponent.isMinimumSetted()) {
            double minimum = spinnerComponent.getMinimum(facesContext);

            htmlWriter.writeAttribute("v:minimum", String.valueOf(minimum));
        }

        if (spinnerComponent.isMaximumSetted()) {
            double maximum = spinnerComponent.getMaximum(facesContext);

            htmlWriter.writeAttribute("v:maximum", String.valueOf(maximum));
        }

        /*
         * if (spinnerComponent.isDefaultValueSetted()) { double defaultValue =
         * spinnerComponent.getDefaultValue(facesContext);
         * 
         * htmlWriter.writeAttribute("v:maximum", String.valueOf(maximum)); }
         */

        if (spinnerComponent.isStepSetted()) {
            String step = spinnerComponent.getStep(facesContext);

            if (step != null && step.length() > 0) {
                htmlWriter.writeAttribute("v:step", step);
            }
        }

        if (spinnerComponent.isCycleValue(facesContext)) {
            htmlWriter.writeAttribute("v:cycle", "true");

        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SPINNER;
    }
}