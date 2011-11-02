/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.rcfaces.core.component.ButtonComponent;
import org.rcfaces.core.component.TooltipComponent;
import org.rcfaces.core.component.iterator.ITooltipIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.event.SelectionEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.TooltipTools;
import org.rcfaces.core.internal.util.ComponentIterators;
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

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
    	
    	IComponentRenderContext componentContext = htmlWriter
                   .getComponentRenderContext();
    	
        ButtonComponent button = (ButtonComponent) componentContext.getComponent();

        FacesContext facesContext = componentContext.getFacesContext();
        

        htmlWriter.startElement(IHtmlWriter.INPUT);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);
        writeTextDirection(htmlWriter, button);
        
        
        ITooltipIterator tooltips = TooltipTools.listTooltipss((UIComponent) button);
        String tooltipId =  button.getTooltipId(facesContext);
        if (tooltipId != null) {
	
			IRenderContext renderContext = componentContext
					.getRenderContext();
	
			String forId = renderContext.computeBrotherComponentClientId(
					button, button.getTooltipId(facesContext));
        
    	} else {
        	if (tooltips.count() > 0) {
        		TooltipComponent tooltipComponent = (TooltipComponent) tooltips.next();
        		tooltipId = tooltipComponent.getId();
        	}
        }
        
        if(tooltipId != null) {
        	 htmlWriter.writeAttribute("v:tooltipId",tooltipId);
        }
        
        String txt = button.getText(htmlWriter.getComponentRenderContext()
                .getFacesContext());
        if (txt != null) {
            txt = ParamUtils.formatMessage(button, txt);

            htmlWriter.writeValue(txt);
        }

        htmlWriter.endElement(IHtmlWriter.INPUT);

        htmlWriter.addSubFocusableComponent(htmlWriter
                .getComponentRenderContext().getComponentClientId());
    }
    
//    public static IColumnIterator listColumns(IGridComponent component,
//            Class filter) {
//        List list = ComponentIterators.list((UIComponent) component, filter);
//        if (list.isEmpty()) {
//            return EMPTY_COLUMNS_ITERATOR;
//        }
//
//        return new ColumnListIterator(list);
//    }

    protected boolean isNameEqualsId() {
        return true;
    }

    protected String getInputType(UIComponent component) {
        return IHtmlWriter.BUTTON_INPUT_TYPE;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        ButtonComponent buttonRenderer = (ButtonComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            String old = buttonRenderer.getText(facesContext);
            if (text.equals(old) == false) {
                buttonRenderer.setText(text);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.TEXT, old, text));
            }
        }

        if (componentData.isEventComponent()) {
            return;
        }

        // Si il n'y a pas d'evenement Camelia, on regarde les evenements HTML !
        String value = componentData.getComponentParameter();
        if (value != null) {
            ActionEvent actionEvent = new SelectionEvent(component, value,
                    null, null, 0);
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