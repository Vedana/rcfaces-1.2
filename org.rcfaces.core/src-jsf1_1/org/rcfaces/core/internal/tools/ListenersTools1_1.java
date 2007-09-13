/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ForwardMethodBinding;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListenersTools1_1 extends ListenersTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ListenersTools1_1.class);

    public static final void parseAction(FacesContext facesContext,
            UIComponent component, IListenerType listenerType, String expression) {
        expression = expression.trim();
        if (expression.length() < 1) {
            return;
        }

        Application application = facesContext.getApplication();

        if (component instanceof UICommand) {
            UICommand command = (UICommand) component;

            MethodBinding vb;
            if (UIComponentTag.isValueReference(expression)) {
                vb = application.createMethodBinding(expression, null);

            } else {
                vb = new ForwardMethodBinding(expression);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set command action to component '"
                        + component.getId() + "' : " + expression);
            }

            command.setAction(vb);
            return;
        }

        if (UIComponentTag.isValueReference(expression) == false) {
            expression = "#[" + expression + "]";
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add server listener to component '" + component.getId()
                    + "' : " + expression);
        }

        listenerType.addActionListener(component, application, expression);
    }
}
