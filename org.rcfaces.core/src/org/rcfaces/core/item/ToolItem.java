/*
 * $Id$
 */
package org.rcfaces.core.item;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.rcfaces.core.component.ToolItemComponent;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolItem extends SelectItemGroup implements IToolItem {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 4088175556368874150L;

    private static final SelectItem SELECT_ITEMS_EMPTY_ARRAY[] = new SelectItem[0];

    private String imageURL;

    private String disabledImageURL;

    private String hoverImageURL;

    private String selectedImageURL;

    private String expandedImageURL;

    private String lookId;

    private int inputType;

    private int imageWidth;

    private int imageHeight;

    private Map clientDatas;

    private Map serverDatas;

    private boolean visible = true;

    public ToolItem() {
    }

    public ToolItem(String label) {
        super(label);
    }

    public ToolItem(String label, String description, boolean disabled,
            SelectItem items[]) {
        super(label, description, disabled, items);
    }

    public ToolItem(IToolItem toolItem) {
        super(toolItem.getLabel(), toolItem.getDescription(), toolItem
                .isDisabled(), toolItem.getSelectItems());

        setInputType(toolItem.getInputType());
        setValue(toolItem.getValue());
        setImageURL(toolItem.getImageURL());
        setDisabledImageURL(toolItem.getDisabledImageURL());
        setHoverImageURL(toolItem.getHoverImageURL());
        setSelectedImageURL(toolItem.getSelectedImageURL());
        setExpandedImageURL(toolItem.getExpandedImageURL());
        setLookId(toolItem.getLookId());
        setImageWidth(toolItem.getImageWidth());
        setImageHeight(toolItem.getImageHeight());

        setVisible(toolItem.isVisible());

        Map map = toolItem.getClientDataMap();
        if (map.isEmpty() == false) {
            getClientDataMap().putAll(map);
        }

        map = toolItem.getServerDataMap();
        if (map.isEmpty() == false) {
            getServerDataMap().putAll(map);
        }
    }

    public ToolItem(ToolItemComponent toolItemComponent) {
        super(toolItemComponent.getItemLabel(), toolItemComponent
                .getItemDescription(), toolItemComponent.isDisabled(),
                SELECT_ITEMS_EMPTY_ARRAY);

        FacesContext facesContext = FacesContext.getCurrentInstance();

        setInputType(toolItemComponent.getInputType(facesContext));

        setValue(toolItemComponent.getItemValue());

        IStatesImageAccessors imageAccessors = (IStatesImageAccessors) toolItemComponent
                .getImageAccessors(facesContext);

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
        }

        setImageWidth(toolItemComponent.getImageWidth(facesContext));
        setImageHeight(toolItemComponent.getImageHeight(facesContext));
        setLookId(toolItemComponent.getLookId(facesContext));

        setVisible(toolItemComponent.isVisible(facesContext));
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

    public final int getImageHeight() {
        return imageHeight;
    }

    public final void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public final int getImageWidth() {
        return imageWidth;
    }

    public final void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public final String getLookId() {
        return lookId;
    }

    public final void setLookId(String lookId) {
        this.lookId = lookId;
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

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

}
