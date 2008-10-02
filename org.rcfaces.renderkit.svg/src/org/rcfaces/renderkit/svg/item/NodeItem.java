/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.rcfaces.renderkit.svg.component.NodeComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NodeItem extends SelectItemGroup implements INodeItem, StateHolder {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 8556900994943378471L;

    private static final SelectItem[] SELECT_ITEM_EMPTY_ARRAY = new SelectItem[0];

    private boolean transientValue;

    private boolean rendered;

    public NodeItem() {
    }

    public NodeItem(INodeItem nodeItem, INodeItem items[]) {
        super(null, null, false, convertToNodeItems(items));

        setValue(nodeItem.getTargetId());
        setRendered(nodeItem.isRendered());
    }

    private static SelectItem[] convertToNodeItems(INodeItem[] items) {
        if (items == null || (items instanceof SelectItem[])) {
            return (SelectItem[]) items;
        }

        if (items.length == 0) {
            return SELECT_ITEM_EMPTY_ARRAY;
        }

        SelectItem dest[] = new SelectItem[items.length];
        System.arraycopy(items, 0, dest, 0, items.length);

        return dest;
    }

    public NodeItem(NodeComponent component) {
        super(component.getItemLabel(), component.getItemDescription(),
                component.isItemDisabled(), SELECT_ITEM_EMPTY_ARRAY);

        setValue(component.getItemValue());

        setRendered(component.isRendered());
    }

    public String getTargetId() {
        return (String) getValue();
    }

    public void setTargetId(String id) {
        setValue(id);
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public void addNodeItem(INodeItem nodeItem) {
        SelectItem items[] = getSelectItems();
        if (items == null || items.length == 0) {
            setSelectItems(new SelectItem[] { (SelectItem) nodeItem });
            return;
        }

        List l = new ArrayList(items.length + 1);
        l.addAll(Arrays.asList(items));
        l.add(nodeItem);

        setSelectItems((SelectItem[]) l.toArray(new SelectItem[l.size()]));

    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean newTransientValue) {
        this.transientValue = newTransientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object states[] = (Object[]) state;

        setValue(states[0]);
        setLabel((String) states[1]);
        setDescription((String) states[2]);
        setDisabled(states[3] != null);

        setRendered(states[4] != null);

        if (states.length < 6) {
            setSelectItems(SELECT_ITEM_EMPTY_ARRAY);
            return;
        }

        SelectItem selectItems[] = new SelectItem[states.length - 5];
        for (int i = 0; i < selectItems.length; i++) {
            selectItems[i] = (SelectItem) UIComponentBase.restoreAttachedState(
                    context, states[i + 5]);
        }

        setSelectItems(selectItems);
    }

    public Object saveState(FacesContext context) {
        SelectItem children[] = getSelectItems();
        if (children == null) {
            children = SELECT_ITEM_EMPTY_ARRAY;
        }

        Object ret[] = new Object[5 + children.length];

        ret[0] = getValue();
        ret[1] = getLabel();
        ret[2] = getDescription();
        ret[3] = isDisabled() ? Boolean.TRUE : null;
        ret[4] = isRendered() ? Boolean.TRUE : null;

        for (int i = 0; i < children.length; i++) {
            ret[i + 5] = UIComponentBase
                    .saveAttachedState(context, children[i]);
        }

        return ret;
    }
}
