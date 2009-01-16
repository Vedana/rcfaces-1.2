/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.tools.ComponentTools.IVarScope;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BindingTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(BindingTools.class);

    public static Object resolveBinding(FacesContext facesContext, Object object) {

        if (object instanceof ValueBinding) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            object = ((ValueBinding) object).getValue(facesContext);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get value of binding => " + object);
            }
        }

        return object;
    }

    public static boolean isBindingExpression(String id) {
        return UIComponentTag.isValueReference(id);
    }

    public static Object evalBinding(FacesContext facesContext,
            String expression, Class type) {

        Application application = facesContext.getApplication();

        ValueBinding valueBinding = application.createValueBinding(expression);

        return valueBinding.getValue(facesContext);
    }

    public static IVarScope processVariableScope(FacesContext facesContext,
            IVariableScopeCapability variableScopeCapability, PhaseId phaseId) {
        String var = variableScopeCapability.getScopeVar();
        if (var == null || var.length() < 1) {
            return null;
        }

        ITransientAttributesManager manager = (ITransientAttributesManager) variableScopeCapability;

        if (false) {
            /**
             * On peut pas mettre la valeur en cache !
             */
            Object ret = manager
                    .getTransientAttribute(ComponentTools.VARIABLE_SCOPE_VALUE);
            if (ret != null) {
                if (ret == ComponentTools.NONE_VARIABLE_SCOPE) {
                    ret = null;
                }

                Map requestMap = facesContext.getExternalContext()
                        .getRequestMap();

                Object old = requestMap.put(var, ret);

                return new ComponentTools.VarScope(var, old);
            }
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();

        Object ret = variableScopeCapability.getScopeValue();
        if (false) {
            /**
             * On peut pas mettre la valeur en cache !
             */

            if (ret == null) {
                manager.setTransientAttribute(
                        ComponentTools.VARIABLE_SCOPE_VALUE,
                        ComponentTools.NONE_VARIABLE_SCOPE);

            } else {
                manager.setTransientAttribute(
                        ComponentTools.VARIABLE_SCOPE_VALUE, ret);
            }
        }

        Object old = requestMap.put(var, ret);
        return new ComponentTools.VarScope(var, old);
    }

    public static void invokeActionListener(FacesContext facesContext,
            UICommand component, ActionEvent facesEvent) {

        // Notify the specified action listener method (if any)
        MethodBinding mb = component.getActionListener();
        if (mb != null) {
            mb.invoke(facesContext, new Object[] { facesEvent });
        }

    }

    public static void setBoundValue(FacesContext context,
            UIComponent component, String attributeName, Object localValue) {

        ValueBinding ve = component.getValueBinding(attributeName);
        if (ve == null) {
            return;
        }

        try {
            ve.setValue(context, localValue);

        } catch (Exception ex) {
            throw new FacesException("Can not set value", ex);
        }
    }

}
