/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.rcfaces.core.component.ButtonComponent;
import org.rcfaces.core.event.SelectionEvent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ButtonRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    // On le met sur le end, car des clientsDatas ... et autres peuvent survenir
    // ...
    public void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        ButtonComponent button = (ButtonComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        htmlWriter.startElement("INPUT");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);

        String txt = button.getText(htmlWriter.getComponentRenderContext()
                .getFacesContext());
        if (txt != null) {
            txt = ParamUtils.formatMessage(button, txt);

            htmlWriter.writeValue(txt);
        }

        htmlWriter.endElement("INPUT");
    }

    protected boolean isNameEqualsId() {
        return true;
    }

    protected String getInputType(UIComponent component) {
        return BUTTON_TYPE;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        if (componentData.isEventComponent()) {
            return;
        }

        // Si il n'y a pas d'evenement Camelia, on regarde les evenements HTML !
        String value = componentData.getComponentParameter();
        if (value != null) {
            ActionEvent actionEvent = new SelectionEvent(component, null, null,
                    0);
            actionEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            component.queueEvent(actionEvent);

            return;
        }

        // Position X Y ?
        String id = component.getId();

        String x = componentData.getParameter(id + ".x");
        if (x == null) {
            return;
        }

        String y = componentData.getParameter(id + ".y");
        if (y == null) {
            return;
        }

        int buttons = SelectionEvent.UNKNOWN_BUTTONS;
        int modifiers = SelectionEvent.UNKNOWN_MODIFIERS;

        int px = SelectionEvent.UNKNOWN_POSITION;
        int py = SelectionEvent.UNKNOWN_POSITION;

        if (x.length() > 0) {
            try {
                px = Integer.parseInt(x);
            } catch (NumberFormatException ex) {
                FacesContext.getCurrentInstance().getExternalContext().log(
                        "Can not parse X position '" + x + "'.", ex);
            }
        }

        if (y.length() > 0) {
            try {
                py = Integer.parseInt(y);

            } catch (NumberFormatException ex) {
                FacesContext.getCurrentInstance().getExternalContext().log(
                        "Can not parse Y position '" + y + "'.", ex);
            }
        }

        ActionEvent actionEvent = new SelectionEvent(component, 0, px, py,
                buttons, modifiers);
        actionEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
        component.queueEvent(actionEvent);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.BUTTON;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }
}