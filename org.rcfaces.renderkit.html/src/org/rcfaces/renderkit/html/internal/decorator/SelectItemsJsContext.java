/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

class SelectItemsJsContext extends SelectItemsContext {
    private static final String REVISION = "$Revision$";

    private final List varIds = new ArrayList(8);

    private final boolean disabled;

    private String managerComponentId;

    public SelectItemsJsContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            UIComponent rootComponent, Object value) {
        super(renderer, componentRenderContext, rootComponent, value);

        UIComponent component = getRootComponent();
        if (component instanceof IDisabledCapability) {
            disabled = ((IDisabledCapability) component).isDisabled();

        } else {
            disabled = false;
        }
    }

    public final String getManagerVarId() {
        return managerComponentId;
    }

    public final void setManagerComponentId(String managerComponentId) {
        this.managerComponentId = managerComponentId;
    }

    public void popVarId() {
        varIds.remove(varIds.size() - 1);
    }

    public void pushVarId(String varId) {
        varIds.add(varId);
    }

    public String peekVarId() {
        if (varIds.isEmpty()) {
            throw new NullPointerException("No var available into stack !");
        }
        return (String) varIds.get(varIds.size() - 1);
    }

    public int countVarId() {
        return varIds.size();
    }

    public boolean isDisabled() {
        return disabled;
    }
}
