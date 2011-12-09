/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCameliaRenderer1 extends AbstractCameliaRenderer0 {

    @Override
    public final void decode(FacesContext context, UIComponent component) {

        IRequestContext requestContext = getRequestContext(context);

        String requestComponentId = getRequestComponentId(requestContext,
                component);

        IComponentData componentData = requestContext.getComponentData(
                component, requestComponentId, this);

        if (component instanceof ClientBehaviorHolder) {
            decodeBehaviors(requestContext, component, componentData);
        }

        decode(requestContext, component, componentData);
    }

    protected void decodeBehaviors(IRequestContext requestContext,
            UIComponent component, IComponentData componentData) {
    }

}
