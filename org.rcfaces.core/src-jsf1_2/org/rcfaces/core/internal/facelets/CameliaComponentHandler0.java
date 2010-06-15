/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

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
    private static final String REVISION = "$Revision$";

    public CameliaComponentHandler0(ComponentConfig config) {
        super(config);
    }

    protected static void actionApplyMetaData(final FaceletContext ctx,
            UIComponent instance, String expression,
            IListenerType defaultListenerType, final TagAttribute tagAttribute) {

        ListenersTools1_2.parseAction(ctx.getFacesContext(), instance,
                defaultListenerType, expression,
                new ListenersTools.IMethodExpressionCreator() {

                    public MethodExpression create(String expression,
                            Class[] paramTypes) {

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
