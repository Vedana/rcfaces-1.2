/*
 * $Id$
 */
package org.rcfaces.core.item;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.MenuCheckItemComponent;
import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.component.MenuRadioItemComponent;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.internal.component.IExpandImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuItem extends TreeNode implements IMenuItem {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 1262314873491593499L;

    private String accessKey;

    private String acceleratorKey;

    private boolean visible = true;

    public MenuItem() {
    }

    public MenuItem(String label, String description, boolean disabled,
            SelectItem items[]) {
        super(label, description, disabled, items);
    }

    public MenuItem(IMenuItem menuItem) {
        super(menuItem);

        setAccessKey(menuItem.getAccessKey());
        setAcceleratorKey(menuItem.getAcceleratorKey());
        setVisible(menuItem.isVisible());
    }

    public MenuItem(MenuItemComponent menuItemComponent) {
        super(menuItemComponent.getItemLabel(), menuItemComponent
                .getItemDescription(), menuItemComponent.isDisabled(),
                SELECT_ITEMS_EMPTY_ARRAY);
        FacesContext facesContext = FacesContext.getCurrentInstance();

        int type = IInputTypeCapability.AS_PUSH_BUTTON;
        if (menuItemComponent instanceof MenuCheckItemComponent) {
            type = IInputTypeCapability.AS_CHECK_BUTTON;

        } else if (menuItemComponent instanceof MenuRadioItemComponent) {
            type = IInputTypeCapability.AS_RADIO_BUTTON;
        }

        setAcceleratorKey(menuItemComponent.getAcceleratorKey(facesContext));
        setAccessKey(menuItemComponent.getAccessKey(facesContext));

        setInputType(type);

        Object itemValue = menuItemComponent.getItemValue();
        if (itemValue != null) {
            setValue(itemValue);
        }

        setStyleClass(menuItemComponent.getStyleClass(facesContext));

        IExpandImageAccessors imageAccessors = (IExpandImageAccessors) menuItemComponent
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

        if (menuItemComponent.getServerDataCount() > 0) {
            Map map = menuItemComponent.getServerDataMap();

            getServerDataMap().putAll(map);
        }

        if (menuItemComponent.getClientDataCount() > 0) {
            Map map = menuItemComponent.getClientDataMap();

            getClientDataMap().putAll(map);
        }
    }

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    public final String getAcceleratorKey() {
        return acceleratorKey;
    }

    public final void setAcceleratorKey(String acceleratorKey) {
        this.acceleratorKey = acceleratorKey;
    }

    public final String getAccessKey() {
        return accessKey;
    }

    public final void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

}
