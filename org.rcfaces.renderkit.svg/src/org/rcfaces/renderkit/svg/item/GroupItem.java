/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.item;

import org.rcfaces.renderkit.svg.component.GroupComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GroupItem extends NodeItem implements IGroupItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -3180958048094512452L;

    public GroupItem() {
    }

    public GroupItem(IGroupItem groupItem, INodeItem nodeItems[]) {
        super(groupItem, nodeItems);
    }

    public GroupItem(GroupComponent component) {
        super(component);
    }

}
