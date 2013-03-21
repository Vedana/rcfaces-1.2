/*
 * $Id$
 */
package org.rcfaces.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.item.BasicImagesSelectItem;
import org.rcfaces.core.item.BasicSelectItem;
import org.rcfaces.core.item.BasicSelectItemPath;
import org.rcfaces.core.item.ISelectItem;
import org.rcfaces.core.item.ISelectItemGroup;
import org.rcfaces.core.item.ISelectItemPath;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredCollection;
import org.rcfaces.core.model.IFiltredCollection.IFiltredIterator;
import org.rcfaces.core.model.IFiltredCollection2;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemTools {

    private static final Log LOG = LogFactory.getLog(SelectItemTools.class);

    private static final ISelectItemNodeFactory DEFAULT_FACTORY = new ISelectItemNodeFactory() {

        public SelectItemNode newSelectItemNode(SelectItemNode parent,
                String id, SelectItem selectItem, int depth) {
            return new SelectItemNode(parent, id, selectItem, depth);
        }
    };

    public static SelectItemNode listSelectItems(UIComponent component) {
        return listSelectItems(null, component, null, null, null);
    }

    public static SelectItemNode listSelectItems(FacesContext facesContext,
            UIComponent component) {
        return listSelectItems(facesContext, component, null, null, null);
    }

    public static SelectItemNode listSelectItems(FacesContext facesContext,
            UIComponent component, ISelectItemNodeFactory factory,
            IFilterProperties filterProperties, ISelectItemNodeHandler handler) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }
        if (factory == null) {
            factory = DEFAULT_FACTORY;
        }

        SelectItemWalker walker = new SelectItemWalker(facesContext, component,
                factory, filterProperties, handler);

        SelectItemNode root = factory.newSelectItemNode(null, null, null, 0);

        if (handler != null) {
            handler.startTree(root);
        }

        List<UIComponent> children = component.getChildren();
        for (UIComponent child : children) {
            walker.listSelectItems(child, root);
        }

        if (handler != null) {
            handler.endTree(root);
        }

        return root;
    }

    public static void traverseSelectItems(FacesContext facesContext,
            UIComponent component, ISelectItemNodeHandler handler,
            IFilterProperties properties) {

    }

    public static SelectItem convertToSelectItem(FacesContext facesContext,
            Object value) {
        if ((value == null) || (value instanceof SelectItem)) {
            return (SelectItem) value;
        }

        if (value instanceof ISelectItem) {
            if (value instanceof ISelectItemGroup) {
                ISelectItemGroup selectItemGroup = (ISelectItemGroup) value;

                SelectItemGroup sig = new SelectItemGroup(
                        selectItemGroup.getLabel(),
                        selectItemGroup.getDescription(),
                        selectItemGroup.isDisabled(),
                        selectItemGroup.getSelectItems());

                sig.setValue(selectItemGroup.getValue());

                return sig;
            }

            ISelectItem selectItem = (ISelectItem) value;

            return new SelectItem(selectItem.getValue(), selectItem.getLabel(),
                    selectItem.getDescription(), selectItem.isDisabled());
        }

        if (value instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) value;

            SelectItem selectItem = adaptable
                    .getAdapter(SelectItem.class, null);
            if (selectItem != null) {
                return selectItem;
            }
        }

        if (Constants.ADAPT_SELECT_ITEMS) {
            RcfacesContext rcfacesContext = RcfacesContext
                    .getInstance(facesContext);

            SelectItem selectItem = rcfacesContext.getAdapterManager()
                    .getAdapter(value, SelectItem.class, null);
            if (selectItem != null) {
                return selectItem;
            }
        }

        return null;
    }

    public interface ISelectItemNodeFactory {
        SelectItemNode newSelectItemNode(SelectItemNode parent, String id,
                SelectItem selectItem, int depth);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface ISelectItemNodeHandler {
        void startTree(SelectItemNode root);

        void beginNode(SelectItemNode node);

        void endNode(SelectItemNode node);

        void endTree(SelectItemNode root);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class SelectItemNode {
        private static final SelectItemNode[] EMPTY_ARRAY = new SelectItemNode[0];

        private final SelectItem selectItem;

        private List<SelectItemNode> children = null;

        private final String id;

        private final int depth;

        private final SelectItemNode parent;

        private ISelectItemPath path;

        protected SelectItemNode(SelectItemNode parent, String id,
                SelectItem selectItem, int depth) {
            this.parent = parent;
            this.selectItem = selectItem;
            this.id = id;
            this.depth = depth;
        }

        public SelectItemNode addChild(ISelectItemNodeFactory factory,
                String id, SelectItem selectItem) {
            SelectItemNode child = factory.newSelectItemNode(this, id,
                    selectItem, depth + 1);

            if (children == null) {
                children = new ArrayList<SelectItemNode>();
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
            return children.toArray(new SelectItemNode[children.size()]);
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + depth;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
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

        public ISelectItemPath getPath() {
            if (path != null) {
                return path;
            }

            List<SelectItem> segments = new LinkedList<SelectItem>();
            for (SelectItemNode node = this; node != null; node = node
                    .getParent()) {

                segments.add(0, node.getSelectItem());
            }

            path = new BasicSelectItemPath(segments);

            return path;
        }
    }

    public static SelectItem searchId(SelectItemNode node, String id) {
        SelectItemNode sn = node.searchById(id);
        if (sn == null) {
            return null;
        }

        return sn.getSelectItem();
    }

    private static class SelectItemWalker {

        private final FacesContext facesContext;

        private final UIComponent mainComponent;

        private final ISelectItemNodeFactory factory;

        private final IFilterProperties filterProperties;

        private final ISelectItemNodeHandler handler;

        private final Set<String> selectItemIds = new HashSet<String>();

        public SelectItemWalker(FacesContext facesContext,
                UIComponent mainComponent, ISelectItemNodeFactory factory,
                IFilterProperties filterProperties,
                ISelectItemNodeHandler handler) {

            this.facesContext = facesContext;
            this.mainComponent = mainComponent;
            this.factory = factory;
            this.filterProperties = filterProperties;
            this.handler = handler;
        }

        protected void listSelectItems(UIComponent currentComponent,
                SelectItemNode parentNode) {
            String componentClientId = currentComponent
                    .getClientId(facesContext);

            if (currentComponent instanceof UISelectItem) {
                UISelectItem uiSelectItem = (UISelectItem) currentComponent;

                SelectItemNode singleItemNode = null;

                Object value = uiSelectItem.getValue();
                if (value instanceof SelectItem) {
                    singleItemNode = addSelectItem(parentNode,
                            currentComponent, componentClientId,
                            (SelectItem) value);

                } else if (value instanceof SelectItem[]) {
                    SelectItem selectItems[] = (SelectItem[]) value;
                    for (int i = 0; i < selectItems.length; i++) {
                        addSelectItem(parentNode, currentComponent,
                                componentClientId, selectItems[i]);
                    }

                } else if (value instanceof SelectItemGroup[]) {
                    SelectItemGroup selectItems[] = (SelectItemGroup[]) value;
                    for (int i = 0; i < selectItems.length; i++) {
                        addSelectItem(parentNode, currentComponent,
                                componentClientId, selectItems[i]);
                    }

                } else if ((value instanceof IFiltredCollection)
                        || (value instanceof IFiltredCollection2)) {

                    Iterator< ? > it;
                    if (value instanceof IFiltredCollection2) {
                        it = ((IFiltredCollection2< ? >) value).iterator(
                                currentComponent, filterProperties, -1);
                    } else {
                        it = ((IFiltredCollection< ? >) value).iterator(
                                filterProperties, -1);
                    }

                    try {
                        for (; it.hasNext();) {
                            Object item = it.next();
                            if ((item instanceof SelectItem) == false) {
                                item = convertToSelectItem(facesContext, item);

                                if (item == null) {
                                    continue;
                                }
                            }

                            addSelectItem(parentNode, currentComponent,
                                    componentClientId, (SelectItem) item);
                        }

                    } finally {
                        if (it instanceof IFiltredIterator) {
                            ((IFiltredIterator< ? >) it).release();
                        }
                    }

                } else if (value == null) {
                    SelectItem selectItem;
                    if (uiSelectItem instanceof IImageCapability) {
                        selectItem = new BasicImagesSelectItem(uiSelectItem);

                    } else {
                        selectItem = new BasicSelectItem(uiSelectItem);
                    }

                    singleItemNode = addSelectItem(parentNode,
                            currentComponent, componentClientId, selectItem);
                }

                if (singleItemNode != null
                        && currentComponent.getChildCount() > 0) {
                    List<UIComponent> children = currentComponent.getChildren();
                    for (UIComponent child : children) {

                        listSelectItems(child, singleItemNode);
                    }
                }

            } else if (currentComponent instanceof UISelectItems) {
                Object value = ((UISelectItems) currentComponent).getValue();
                if (value instanceof SelectItem[]) {
                    SelectItem selectItems[] = (SelectItem[]) value;
                    for (int i = 0; i < selectItems.length; i++) {
                        addSelectItem(parentNode, currentComponent,
                                componentClientId, selectItems[i]);
                    }
                }
            }
        }

        private SelectItemNode addSelectItem(SelectItemNode parentNode,
                UIComponent component, String componentClientId,
                SelectItem selectItem) {

            String selectItemId = componentClientId;

            if (selectItemIds.add(componentClientId) == false) {
                selectItemId = componentClientId + "::" + selectItemIds.size();
                selectItemIds.add(componentClientId);
            }

            SelectItemNode node = parentNode.addChild(factory, selectItemId,
                    selectItem);

            if (handler != null) {
                handler.beginNode(node);
            }

            if (selectItem instanceof SelectItemGroup) {
                SelectItemGroup group = (SelectItemGroup) selectItem;

                for (SelectItem child : group.getSelectItems()) {
                    addSelectItem(node, component, componentClientId, child);
                }

            }

            if (handler != null) {
                handler.endNode(node);
            }

            return node;
        }
    }
}
