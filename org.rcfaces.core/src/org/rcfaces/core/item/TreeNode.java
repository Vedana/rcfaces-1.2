/*
 * $Id$
 */
package org.rcfaces.core.item;

import javax.faces.model.SelectItem;

import org.rcfaces.core.component.TreeNodeComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeNode extends DefaultItem implements ITreeNode {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8687718500434714577L;

    public TreeNode() {
    }

    public TreeNode(String label) {
        super(label);
    }

    public TreeNode(String label, String description, boolean disabled,
            SelectItem items[]) {
        super(label, description, disabled, items);
    }

    public TreeNode(ITreeNode treeNode) {
        super(treeNode);
    }

    public TreeNode(TreeNodeComponent treeNodeComponent) {
        super(treeNodeComponent);
    }
}
