/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.SpinnerComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.ISubInputClientIdRenderer;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SpinnerRenderer extends TextEntryRenderer implements
        ISubInputClientIdRenderer {
    private static final String REVISION = "$Revision$";

    protected static final int SPINNER_BUTTON_WIDTH = 16;

    protected static final int SPINNER_BUTTON_HEIGHT = 10;

    private static final String UP_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "up";

    private static final String DOWN_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "down";

    public static final String INPUT_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "input";

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

        htmlWriter.startElement(IHtmlWriter.TABLE);

        htmlWriter.writeCellPadding(0);
        htmlWriter.writeCellSpacing(0);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeValidatorAttributes(htmlWriter); // Le validateur peut
        // impfluencer le format !
        writeCssAttributes(htmlWriter);
        writeSpinnerAttributes(htmlWriter);

        htmlWriter.startElement(IHtmlWriter.COL);

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

        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.COL);
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.TR);
        htmlWriter.writeVAlign("middle");

        htmlWriter.startElement(IHtmlWriter.TD);
        if (inputWidth > 0) {
            htmlWriter.writeWidth(inputWidth);
        }

        htmlWriter.startElement(IHtmlWriter.INPUT);
        String inputId = getInputClientId(htmlWriter);
        htmlWriter.writeId(inputId);
        htmlWriter.addSubFocusableComponent(inputId);

        if (inputWidth > 0) {
            htmlWriter.writeStyle().writeWidthPx(inputWidth);
        }
        htmlWriter.writeClass(getInputClassName(htmlWriter));
        writeInputAttributes(htmlWriter);
        writeTextEntryAttributes(htmlWriter);
        writeTextDirection(htmlWriter, spinner);
        writeValueAttributes(htmlWriter);
        htmlWriter.endElement(IHtmlWriter.INPUT);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeWidth(spinnerButtonWidth);

        boolean disabled = spinner.isDisabled(htmlWriter
                .getComponentRenderContext().getFacesContext());

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();
        String blankImageURL = htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetURI(BLANK_IMAGE_URL, true);

        int spinnerButtonHeight = getSpinnerButtonHeight();

        htmlWriter.startElement(IHtmlWriter.IMG);
        htmlWriter.writeId(getUpButtonClientId(htmlWriter));
        htmlWriter.writeClass(getUpButtonClassName(htmlWriter, disabled));
        htmlWriter.writeSrc(blankImageURL);
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.writeHeight(spinnerButtonHeight);
        htmlWriter.endElement(IHtmlWriter.IMG);

        htmlWriter.startElement(IHtmlWriter.IMG);
        htmlWriter.writeId(getDownButtonClientId(htmlWriter));
        htmlWriter.writeClass(getDownButtonClassName(htmlWriter, disabled));
        htmlWriter.writeSrc(blankImageURL);
        htmlWriter.writeWidth(spinnerButtonWidth);
        htmlWriter.writeHeight(spinnerButtonHeight);
        htmlWriter.endElement(IHtmlWriter.IMG);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.endElement(IHtmlWriter.TR);

        htmlWriter.endElement(IHtmlWriter.TABLE);

        htmlWriter.getJavaScriptEnableMode().enableOnInit(); // Pour les
        // fleches !
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

    protected String getUpButtonClientId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + UP_ID_SUFFIX;
    }

    protected String getDownButtonClassName(IHtmlWriter htmlWriter,
            boolean disabled) {
        String className = getMainStyleClassName() + "_down";
        if (disabled) {
            className += "_disabled";
        }

        return className;
    }

    protected String getDownButtonClientId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + DOWN_ID_SUFFIX;
    }

    protected String getInputClientId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + INPUT_ID_SUFFIX;
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
            htmlWriter.writeAttribute("v:cycle", true);

        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SPINNER;
    }

    public String computeSubInputClientId(IRenderContext renderContext,
            UIComponent component, String clientId) {
        return clientId + INPUT_ID_SUFFIX;
    }
}