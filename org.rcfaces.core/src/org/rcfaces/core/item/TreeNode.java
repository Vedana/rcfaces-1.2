/*
 * $Id$
 */
package org.rcfaces.core.item;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.rcfaces.core.component.TreeNodeComponent;
import org.rcfaces.core.internal.component.IExpandImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeNode extends SelectItemGroup implements ITreeNode {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8687718500434714577L;

    protected static final SelectItem SELECT_ITEMS_EMPTY_ARRAY[] = new SelectItem[0];

    private Map serverDatas;

    private Map clientDatas;

    private String imageURL;

    private String disabledImageURL;

    private String hoverImageURL;

    private String selectedImageURL;

    private String expandedImageURL;

    private int inputType;

    private String styleClass;

    private String groupName;

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
        super(treeNode.getLabel(), treeNode.getDescription(), treeNode
                .isDisabled(), treeNode.getSelectItems());

        setInputType(treeNode.getInputType());

        Object itemValue = treeNode.getValue();
        if (itemValue != null) {
            setValue(itemValue);
        }

        String groupName = treeNode.getGroupName();
        if (groupName != null) {
            setGroupName(groupName);
        }

        setImageURL(treeNode.getImageURL());
        setDisabledImageURL(treeNode.getDisabledImageURL());
        setHoverImageURL(treeNode.getHoverImageURL());
        setSelectedImageURL(treeNode.getSelectedImageURL());
        setExpandedImageURL(treeNode.getExpandedImageURL());
        setStyleClass(treeNode.getStyleClass());

        Map map = treeNode.getClientDataMap();
        if (map.isEmpty() == false) {
            getClientDataMap().putAll(map);
        }

        map = treeNode.getServerDataMap();
        if (map.isEmpty() == false) {
            getServerDataMap().putAll(map);
        }
    }

    public TreeNode(TreeNodeComponent treeNodeComponent) {
        super(treeNodeComponent.getItemLabel(), treeNodeComponent
                .getItemDescription(), treeNodeComponent.isDisabled(),
                SELECT_ITEMS_EMPTY_ARRAY);
        FacesContext facesContext = FacesContext.getCurrentInstance();

        setInputType(treeNodeComponent.getInputType());

        Object itemValue = treeNodeComponent.getItemValue();
        if (itemValue != null) {
            setValue(itemValue);
        }

        String groupName = treeNodeComponent.getGroupName(facesContext);
        if (groupName != null) {
            setGroupName(groupName);
        }

        setStyleClass(treeNodeComponent.getStyleClass(facesContext));

        IExpandImageAccessors imageAccessors = (IExpandImageAccessors) treeNodeComponent
                .getImageAccessors();

        if (imageAccessors != null) {
            IContentAccessor contentAccessor = imageAccessors
                    .getImageAccessor();
            if (contentAccessor != null) {
                setImageURL(contentAccessor
                        .resolveURL(facesContext, null, null));
            }

            contentAccessor = imageAccessors.getDisabledImageAccessor();
            if (contentAccessor != null) {
                setDisabledImageURL(contentAccessor.resolveURL(facesContext,
                        null, null));
            }

            contentAccessor = imageAccessors.getHoverImageAccessor();
            if (contentAccessor != null) {
                setHoverImageURL(contentAccessor.resolveURL(facesContext, null,
                        null));
            }

            contentAccessor = imageAccessors.getSelectedImageAccessor();
            if (contentAccessor != null) {
                setSelectedImageURL(contentAccessor.resolveURL(facesContext,
                        null, null));
            }

            contentAccessor = imageAccessors.getExpandedImageAccessor();
            if (contentAccessor != null) {
                setExpandedImageURL(contentAccessor.resolveURL(facesContext,
                        null, null));
            }
        }

        if (treeNodeComponent.getServerDataCount() > 0) {
            Map map = treeNodeComponent.getServerDataMap();

            getServerDataMap().putAll(map);
        }

        if (treeNodeComponent.getClientDataCount() > 0) {
            Map map = treeNodeComponent.getClientDataMap();

            getClientDataMap().putAll(map);
        }
    }

    public String getImageURL() {
        return imageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getHoverImageURL()
     */
    public String getHoverImageURL() {
        return hoverImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getSelectedImageURL()
     */
    public String getSelectedImageURL() {
        return selectedImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getExpandedImageURL()
     */
    public String getExpandedImageURL() {
        return expandedImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getDisabledImageURL()
     */
    public String getDisabledImageURL() {
        return disabledImageURL;
    }

    public void setDisabledImageURL(String disabledImageURL) {
        this.disabledImageURL = disabledImageURL;
    }

    public void setExpandedImageURL(String expandedImageURL) {
        this.expandedImageURL = expandedImageURL;
    }

    public void setHoverImageURL(String hoverImageURL) {
        this.hoverImageURL = hoverImageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSelectedImageURL(String selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public final String getStyleClass() {
        return styleClass;
    }

    public final void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public boolean isServerDataEmpty() {
        if (serverDatas == null) {
            return true;
        }

        return serverDatas.isEmpty();
    }

    public Map getServerDataMap() {
        if (serverDatas == null) {
            serverDatas = new HashMap();
        }

        return serverDatas;
    }

    public boolean isClientDataEmpty() {
        if (clientDatas == null) {
            return true;
        }

        return clientDatas.isEmpty();
    }

    public Map getClientDataMap() {
        if (clientDatas == null) {
            clientDatas = new HashMap();
        }

        return clientDatas;
    }

    public final String getGroupName() {
        return groupName;
    }

    public final void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
