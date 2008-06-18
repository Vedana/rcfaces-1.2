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

        SelectItemNode root = new SelectItemNode(null, null);

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

            Object value = uiSelectItem.getValue();
            if (value instanceof SelectItem) {
                addSelectItem(facesContext, root, component, selectItemIds,
                        (SelectItem) value);
                return;
            }

            if (value instanceof SelectItem[]) {
                SelectItem selectItems[] = (SelectItem[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }

                return;
            }

            if (value instanceof SelectItemGroup[]) {
                SelectItemGroup selectItems[] = (SelectItemGroup[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }

                return;
            }

            if (value == null) {
                SelectItem si;
                if (uiSelectItem instanceof IImageCapability) {
                    si = new BasicImagesSelectItem(uiSelectItem);
                } else {

                    si = new BasicSelectItem(uiSelectItem);
                }

                addSelectItem(facesContext, root, component, selectItemIds, si);
            }
        }

        if (component instanceof UISelectItems) {
            Object value = ((UISelectItems) component).getValue();
            if (value instanceof SelectItem[]) {
                SelectItem selectItems[] = (SelectItem[]) value;
                for (int i = 0; i < selectItems.length; i++) {
                    addSelectItem(facesContext, root, component, selectItemIds,
                            selectItems[i]);
                }

                return;
            }
        }
    }

    private static void addSelectItem(FacesContext facesContext,
            SelectItemNode root, UIComponent component, Set selectItemIds,
            SelectItem value) {
        String id = component.getClientId(facesContext);

        if (selectItemIds.add(id) == false) {
            id += "::" + selectItemIds.size();
            selectItemIds.add(id);
        }

        root.addChild(id, value);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class SelectItemNode {
        private final SelectItem selectItem;

        private final List children = new ArrayList();

        private final String id;

        SelectItemNode(String id, SelectItem selectItem) {
            this.selectItem = selectItem;
            this.id = id;
        }

        public void addChild(String id, SelectItem selectItem) {
            children.add(new SelectItemNode(id, selectItem));
        }

        public SelectItem getSelectItem() {
            return selectItem;
        }

        public SelectItemNode[] getChildren() {
            return (SelectItemNode[]) children
                    .toArray(new SelectItemNode[children.size()]);
        }

        public String getId() {
            return id;
        }
    }

    public static SelectItem searchId(SelectItemNode node, String id) {
        if (id.equals(node.getId())) {
            return node.getSelectItem();
        }

        SelectItemNode chilren[] = node.getChildren();
        for (int i = 0; i < chilren.length; i++) {
            SelectItem si = searchId(chilren[i], id);
            if (si != null) {
                return si;
            }
        }

        return null;
    }
}
