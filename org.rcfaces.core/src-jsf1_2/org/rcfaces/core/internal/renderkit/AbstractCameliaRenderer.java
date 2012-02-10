/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCameliaRenderer extends AbstractCameliaRenderer0 {

    private static final Log LOG = LogFactory
            .getLog(AbstractCameliaRenderer.class);

    private static final String VARIABLE_SCOPE_PROPERTY = "camelia.VARIABLE_SCOPE";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();
        if ((component instanceof IVariableScopeCapability) == false) {
            return;
        }

        IVariableScopeCapability variableScopeCapability = (IVariableScopeCapability) component;

        String var = variableScopeCapability.getScopeVar();
        if (var == null || var.length() < 1) {
            return;
        }

        Object value = variableScopeCapability.getScopeValue();
        if (value == null) {
            return;
        }

        ValueExpression valueExpression = component
                .getValueExpression(Properties.SCOPE_VALUE);

        writer.getComponentRenderContext()
                .getRenderContext()
                .pushScopeVar(var, value, valueExpression,
                        variableScopeCapability.isScopeSaveValue());

        writer.getComponentRenderContext().setAttribute(
                VARIABLE_SCOPE_PROPERTY, var);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        String scopeVar = (String) writer.getComponentRenderContext()
                .getAttribute(VARIABLE_SCOPE_PROPERTY);
        if (scopeVar != null) {
            writer.getComponentRenderContext().getRenderContext()
                    .popScopeVar(scopeVar);
        }

        super.encodeEnd(writer);
    }
}