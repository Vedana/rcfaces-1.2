/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
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

	private static final Object OBJECT_EMPTY_ARRAY[] = new Object[0];

	public static void collapseAll(FacesContext facesContext,
			TreeComponent treeComponent) {
		treeComponent.setExpansionValues((Object[]) null);
	}

	public static void expandAll(FacesContext facesContext,
			TreeComponent treeComponent) {
		// TODO Auto-generated method stub

	}

	public static void setExpanded(FacesContext facesContext,
			TreeComponent treeComponent, Object itemValue, boolean expanded) {

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
			treeComponent.setExpansionValues((Object[]) null);
			return;
		}

		treeComponent.setExpansionValues(values.toArray());
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

		Object values[] = treeComponent.getExpansionValues(facesContext);

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
