/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.util.Map;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

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

        // La deserialisation génére cette classe !
        if (object instanceof ValueBinding) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }
            object = ((ValueBinding) object).getValue(facesContext);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get value of binding => " + object);
            }

            return object;
        }
        
        if (object instanceof ValueExpression) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }
            object = ((ValueExpression) object).getValue(facesContext
                    .getELContext());

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get value of binding => " + object);
            }
        }

        return object;
    }

    public static boolean isBindingExpression(String value) {
        if (value.length() < 4) {
            return false;
        }

        int pos = value.indexOf("#{");

        if (pos >= 0 && pos < value.indexOf('}')) {
            return true;
        }

        return false;
    }

    public static Object evalBinding(FacesContext facesContext,
            String expression, Class type) {

        Application application = facesContext.getApplication();

        ELContext elContext = facesContext.getELContext();

        ValueExpression valueExpression = application.getExpressionFactory()
                .createValueExpression(elContext, expression, type);

        return valueExpression.getValue(elContext);
    }

    public static IVarScope processVariableScope(FacesContext facesContext,
            IVariableScopeCapability variableScopeCapability) {
        String var = variableScopeCapability.getScopeVar();
        if (var == null || var.length() < 1) {
            return null;
        }

        ITransientAttributesManager manager = (ITransientAttributesManager) variableScopeCapability;

        if (false) {
            /**
             * On peut pas mettre la valeur en cache ! Car elle peut changer
             * entre 2 appels !
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

        Object ret = variableScopeCapability.getScopeValue();

        Map requestMap = facesContext.getExternalContext().getRequestMap();

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
        MethodExpression me = component.getActionExpression();
        if (me != null) {
            me.invoke(facesContext.getELContext(), new Object[] { facesEvent });
        }
    }
}
