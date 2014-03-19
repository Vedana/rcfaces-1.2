/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IComponentLifeCycle;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;
import org.rcfaces.core.internal.tools.ListenersTools1_2;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.TagMethodExpression;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaComponentHandler0 extends ComponentHandler {

    private static final Log LOG = LogFactory
            .getLog(CameliaComponentHandler0.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    public CameliaComponentHandler0(ComponentConfig config) {
        super(config);
    }

    protected UIComponent createComponent(FaceletContext ctx) {
        TagAttribute binding = getAttribute("binding");
        if (binding == null) {
            UIComponent component = super.createComponent(ctx);

            if (component instanceof IComponentLifeCycle) {
                IComponentLifeCycle componentLifeCycle = (IComponentLifeCycle) component;

                componentLifeCycle
                        .initializePhase(ctx.getFacesContext(), false);
            }

            if (debugEnabled) {
                LOG.debug("Create component for id '" + getId(ctx)
                        + "' returns '" + component + "'.");
            }

            return component;
        }

        ValueExpression ve = binding.getValueExpression(ctx, Object.class);

        Object bindingValue = ve.getValue(ctx);

        UIComponent component = super.createComponent(ctx);

        if (component instanceof IComponentLifeCycle) {
            IComponentLifeCycle componentLifeCycle = (IComponentLifeCycle) component;

            componentLifeCycle.initializePhase(ctx.getFacesContext(),
                    bindingValue != null);
        }

        if (debugEnabled) {
            LOG.debug("Create component for id '" + getId(ctx) + "' returns '"
                    + component + "'.");
        }

        return component;
    }

    protected static void actionApplyMetaData(final FaceletContext ctx,
            UIComponent instance, String expression,
            IListenerType defaultListenerType, final TagAttribute tagAttribute) {

        ListenersTools1_2.parseAction(ctx.getFacesContext(), instance,
                defaultListenerType, expression,
                new ListenersTools.IMethodExpressionCreator() {

                    public MethodExpression create(String expression,
                            Class< ? >[] paramTypes) {

                        ExpressionFactory expressionFactory = ctx
                                .getExpressionFactory();

                        MethodExpression methodExpression = expressionFactory
                                .createMethodExpression(ctx, expression, null,
                                        paramTypes);

                        return new TagMethodExpression(tagAttribute,
                                methodExpression);
                    }
                });
    }
}
