/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ForwardMethodExpression;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListenersTools1_2 extends ListenersTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ListenersTools1_2.class);

    private static final Class[] NO_PARAMETER = new Class[0];

    public static void parseListener(FacesContext facesContext,
            UIComponent component, IListenerType listenerType,
            ValueExpression expression) {
        parseListener(facesContext, component, listenerType, expression, false);
    }

    public static void parseListener(FacesContext facesContext,
            UIComponent component, IListenerType listenerType,
            ValueExpression expression, boolean defaultAction) {
        parseListener(facesContext, component, listenerType, expression
                .getExpressionString(), defaultAction);
    }

    public static final void parseAction(FacesContext facesContext,
            UIComponent component, IListenerType listenerType,
            ValueExpression expression) {
        parseAction(facesContext, component, listenerType, expression
                .getExpressionString());
    }

    public static final void parseAction(FacesContext facesContext,
            UIComponent component, IListenerType listenerType, String expression) {
        expression = expression.trim();

        if (LOG.isDebugEnabled()) {
            LOG.debug("ParseAction  component='" + component
                    + "' listenerType='" + listenerType + "' expression='"
                    + expression + "'.");
        }

        if (expression.length() < 1) {
            return;
        }

        Application application = facesContext.getApplication();

        if (component instanceof UICommand) {
            UICommand command = (UICommand) component;

            MethodExpression vb;
            if (BindingTools.isBindingExpression(expression)) {
                vb = application.getExpressionFactory().createMethodExpression(
                        facesContext.getELContext(), expression, null,
                        NO_PARAMETER);

            } else {
                vb = new ForwardMethodExpression(expression);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set command action to component '"
                        + component.getId() + "' : " + expression);
            }

            command.setActionExpression(vb);
            return;
        }

        if (BindingTools.isBindingExpression(expression) == false) {
            expression = "#[" + expression + "]";
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add server listener to component '" + component.getId()
                    + "' : " + expression);
        }

        listenerType.addActionListener(component, application, expression);
    }
}
