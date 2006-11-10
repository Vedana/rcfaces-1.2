/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeRenderContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    private final boolean checkable;

    private final boolean userExpandable;

    private final boolean selectable;

    private final boolean writeSelectionState;

    private final boolean writeCheckState;

    private String lastInteractiveParent = null;

    public TreeRenderContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            TreeComponent treeComponent, int depth, boolean sendFullStates,
            String containerVarId) {
        super(renderer, componentRenderContext, treeComponent, null);

        FacesContext facesContext = componentRenderContext.getFacesContext();

        checkable = treeComponent.isCheckable(facesContext);
        if (checkable) {
            writeCheckState = (treeComponent
                    .isClientCheckFullState(facesContext) == false);
        } else {
            writeCheckState = false;
        }

        selectable = treeComponent.isSelectable(facesContext);
        if (selectable) {
            writeSelectionState = (treeComponent
                    .isClientSelectionFullState(facesContext) == false);
        } else {
            writeSelectionState = false;
        }

        userExpandable = treeComponent.isUserExpandable(facesContext);

        int preloadLevel = treeComponent.getPreloadedLevelDepth(facesContext);

        if (preloadLevel > 0) {
            preloadLevel += depth;

            setPreloadedLevelDepth(preloadLevel);
        }

        Object value = treeComponent.getValue();
        Object values[] = null;
        if (value != null) {
            if (value.getClass().isArray() == false) {
                values = new Object[] { value };

            } else {
                values = (Object[]) value;
            }
        }

        initializeTreeValue(facesContext, treeComponent, values);

        if (containerVarId != null) {
            pushVarId(containerVarId);
        }
    }

    protected void initializeValue(UIComponent component, Object value) {
    }

    protected void initializeTreeValue(FacesContext facesContext,
            TreeComponent treeComponent, Object values[]) {
        if (checkable) {
            // L'attribut checkValue est prioritaire, par contre lors de la mise
            // à jour,
            // la valeur de checkValue sera mise à NULL, et l'état check sera
            // mis dans value ! (pour le submit)

            Object checkValues = treeComponent.getCheckedValues(facesContext);
            if (checkValues != null) {
                initializeCheckValue(checkValues);

            } else {
                initializeCheckValue(values);
            }

            if (selectable) {
                initializeSelectionValue(treeComponent
                        .getSelectedValues(facesContext));
            }

        } else if (selectable) {
            Object selectionValues = treeComponent
                    .getSelectedValues(facesContext);
            if (selectionValues != null) {
                initializeSelectionValue(selectionValues);

            } else {
                initializeSelectionValue(values);
            }
        }

        initializeExpansionValue(treeComponent.getExpansionValues(facesContext));
    }

    public final boolean writeCheckFullState() {
        return writeCheckState;
    }

    public final boolean writeSelectionFullState() {
        return writeSelectionState;
    }

    public final boolean isUserExpandable() {
        return userExpandable;
    }

    public boolean isFirstInteractiveChild(String parentVarId) {
        if (lastInteractiveParent == parentVarId) {
            return false;
        }

        lastInteractiveParent = parentVarId;
        return true;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean isSelectable() {
        return selectable;
    }
}
