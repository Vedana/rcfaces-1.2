/*
 * $Id$
 */
package org.rcfaces.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.item.BasicImagesSelectItem;
import org.rcfaces.core.item.BasicSelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemTools {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SelectItemTools.class);

    public static SelectItemNode listSelectItems(UIComponent component) {
        return listSelectItems(null, component);
    }

    public static SelectItemNode listSelectItems(FacesContext facesContext,
            UIComponent component) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        SelectItemNode root = new SelectItemNode(null, null, null, 0);

        Set selectItemIds = new HashSet();

        List children = component.getChildren();
        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            listSelectItems(facesContext, child, selectItemIds, root);
        }

        return root;
    }

    private static void listSelectItems(FacesContext facesContext,
            UIComponent component, Set selectItemIds, SelectItemNode root) {
        if (component instanceof UISelectItem) {
            UISelectItem uiSelectItem = (UISelectItem) component;

            SelectItemNode singleItemNode = null;

            Object value = uiSelectItem.getValue();
            if (value instanceof SelectItem) {

                singleItemNode = addSelectItem(facesContext, root, component,
                        selectItemIds, (SelectItem) value);

            } else if (value instanceof SelectItem[]) {
                SelectItem selectItems[] = (SelectItem[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }

            } else if (value instanceof SelectItemGroup[]) {
                SelectItemGroup selectItems[] = (SelectItemGroup[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }

            } else if (value == null) {
                SelectItem selectItem;
                if (uiSelectItem instanceof IImageCapability) {
                    selectItem = new BasicImagesSelectItem(uiSelectItem);

                } else {
                    selectItem = new BasicSelectItem(uiSelectItem);
                }

                singleItemNode = addSelectItem(facesContext, root, component,
                        selectItemIds, selectItem);
            }

            if (singleItemNode != null && component.getChildCount() > 0) {
                List children = component.getChildren();
                for (Iterator it = children.iterator(); it.hasNext();) {
                    UIComponent child = (UIComponent) it.next();

                    listSelectItems(facesContext, child, selectItemIds,
                            singleItemNode);
                }
            }

        } else if (component instanceof UISelectItems) {
            Object value = ((UISelectItems) component).getValue();
            if (value instanceof SelectItem[]) {
                SelectItem selectItems[] = (SelectItem[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }
            }
        }
    }

    private static SelectItemNode addSelectItem(FacesContext facesContext,
            SelectItemNode root, UIComponent component, Set selectItemIds,
            SelectItem value) {
        String id = component.getClientId(facesContext);

        if (selectItemIds.add(id) == false) {
            id += "::" + selectItemIds.size();
            selectItemIds.add(id);
        }

        SelectItemNode node = root.addChild(id, value);

        return node;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class SelectItemNode {
        private static final SelectItemNode[] EMPTY_ARRAY = new SelectItemNode[0];

        private final SelectItem selectItem;

        private List children = null;

        private final String id;

        private final int depth;

        private final SelectItemNode parent;

        SelectItemNode(SelectItemNode parent, String id, SelectItem selectItem,
                int depth) {
            this.parent = parent;
            this.selectItem = selectItem;
            this.id = id;
            this.depth = depth;
        }

        public SelectItemNode addChild(String id, SelectItem selectItem) {
            SelectItemNode child = new SelectItemNode(this, id, selectItem,
                    depth + 1);

            if (children == null) {
                children = new ArrayList();
            }
            children.add(child);

            return child;
        }

        public SelectItem getSelectItem() {
            return selectItem;
        }

        public SelectItemNode[] getChildren() {
            if (children == null) {
                return EMPTY_ARRAY;
            }
            return (SelectItemNode[]) children
                    .toArray(new SelectItemNode[children.size()]);
        }

        public String getId() {
            return id;
        }

        public SelectItemNode getParent() {
            return parent;
        }

        public int getDepth() {
            return depth;
        }

        public SelectItemNode searchById(String id) {
            if (id.equals(getId())) {
                return this;
            }

            SelectItemNode chilren[] = getChildren();
            for (int i = 0; i < chilren.length; i++) {
                SelectItemNode si = chilren[i].searchById(id);
                if (si != null) {
                    return si;
                }
            }

            return null;
        }

        public SelectItemNode searchByValue(Object value) {
            if (selectItem != null && value.equals(selectItem.getValue())) {
                return this;
            }

            SelectItemNode chilren[] = getChildren();
            for (int i = 0; i < chilren.length; i++) {
                SelectItemNode si = chilren[i].searchByValue(value);
                if (si != null) {
                    return si;
                }
            }

            return null;
        }

        public SelectItemNode getRoot() {
            SelectItemNode node = this;
            for (; node.getParent() != null; node = node.getParent()) {
            }

            return node;
        }

        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + depth;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof SelectItemNode)) {
                return false;
            }
            SelectItemNode other = (SelectItemNode) obj;
            if (depth != other.depth) {
                return false;
            }
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }

    }

    public static SelectItem searchId(SelectItemNode node, String id) {
        SelectItemNode sn = node.searchById(id);
        if (sn == null) {
            return null;
        }

        return sn.getSelectItem();
    }
}
