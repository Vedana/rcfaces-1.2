/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.TreeNodeComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeTools {
    private static final String REVISION = "$Revision$";

    private static final Object OBJECT_EMPTY_ARRAY[] = new Object[0];

    public static void collapseAll(FacesContext facesContext,
            TreeComponent treeComponent) {
        treeComponent.setExpandedValues((Object[]) null);
    }

    public static void expandAll(FacesContext facesContext,
            TreeComponent treeComponent) {
        // TODO Auto-generated method stub

    }

    public static void setExpanded(FacesContext facesContext,
            TreeComponent treeComponent, Object itemValue, boolean expanded) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        if (treeComponent.isExpansionUseValue(facesContext) == false) {
            itemValue = getTreeNodeValue(facesContext, treeComponent);
        }

        if (itemValue == null) {
            return;
        }

        Set values = getExpansionValues(facesContext, treeComponent);

        if (expanded) {
            if (values.add(itemValue) == false) {
                return;
            }

        } else if (values.remove(itemValue) == false) {
            return;
        }

        if (values.isEmpty()) {
            treeComponent.setExpandedValues((Object[]) null);
            return;
        }

        treeComponent.setExpandedValues(values.toArray());
    }

    public static boolean isExpanded(FacesContext facesContext,
            TreeComponent treeComponent, Object itemValue) {

        if (treeComponent.isExpansionUseValue(facesContext) == false) {
            itemValue = getTreeNodeValue(facesContext, treeComponent);
        }

        if (itemValue == null) {
            return false;
        }

        Set values = getExpansionValues(facesContext, treeComponent);
        if (values == null || values.isEmpty()) {
            return false;
        }

        return values.contains(itemValue);
    }

    private static Object getTreeNodeValue(FacesContext facesContext,
            TreeComponent treeComponent) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Set getExpansionValues(FacesContext facesContext,
            TreeComponent treeComponent) {

        Object values = treeComponent.getExpandedValues(facesContext);

        return ValuesTools.valueToSet(values, true);
    }

    public static TreeComponent getTree(TreeNodeComponent component) {
        UIComponent parent = component.getParent();

        for (; parent != null; parent = parent.getParent()) {
            if (parent instanceof TreeComponent) {
                return (TreeComponent) parent;
            }

            if (parent instanceof UISelectItem) {
                continue;
            }

            throw new FacesException(
                    "Invalid parent of TreeNode component. (Parent must be a Tree or a UISelectItem).");
        }

        throw new FacesException("Parent of TreeNode component not found !");
    }

}
