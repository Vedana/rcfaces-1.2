/*
 * $Id$
 */
package org.rcfaces.core.model;

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

    private static final SelectItem SELECT_ITEMS_EMPTY_ARRAY[] = new SelectItem[0];

    private String imageURL;

    private String disabledImageURL;

    private String hoverImageURL;

    private String selectedImageURL;

    private String expandedImageURL;

    private int inputType;

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
        setValue(treeNode.getValue());
        setImageURL(treeNode.getImageURL());
        setDisabledImageURL(treeNode.getDisabledImageURL());
        setHoverImageURL(treeNode.getHoverImageURL());
        setSelectedImageURL(treeNode.getSelectedImageURL());
        setExpandedImageURL(treeNode.getExpandedImageURL());
    }

    public TreeNode(TreeNodeComponent treeNodeComponent) {
        super(treeNodeComponent.getItemLabel(), treeNodeComponent
                .getItemDescription(), treeNodeComponent.isDisabled(),
                SELECT_ITEMS_EMPTY_ARRAY);

        setInputType(treeNodeComponent.getInputType());

        setValue(treeNodeComponent.getItemValue());

        IExpandImageAccessors imageAccessors = (IExpandImageAccessors) treeNodeComponent
                .getImageAccessors();

        if (imageAccessors != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();

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

}
