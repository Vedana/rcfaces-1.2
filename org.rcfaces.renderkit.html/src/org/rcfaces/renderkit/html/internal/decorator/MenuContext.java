/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IPreloadedLevelDepthCapability;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class MenuContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    public MenuContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            UIComponent rootComponent, Object value, int depth) {
        super(renderer, componentRenderContext, rootComponent, value);

        UIComponent menuComponent = componentRenderContext.getComponent();

        // FacesContext facesContext = componentRenderContext.getFacesContext();

        if (menuComponent instanceof ICheckedValuesCapability) {
            ICheckedValuesCapability checkedValuesCapability = (ICheckedValuesCapability) menuComponent;

            Object checkValues = checkedValuesCapability.getCheckedValues();
            if (checkValues != null) {
                initializeCheckValue(checkValues);
            }
        }

        if (menuComponent instanceof IPreloadedLevelDepthCapability) {
            int preloadLevel = ((IPreloadedLevelDepthCapability) menuComponent)
                    .getPreloadedLevelDepth();

            if (preloadLevel >= 0) {
                preloadLevel += depth;

                setPreloadedLevelDepth(preloadLevel);
            }
        }
    }
}
