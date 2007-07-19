package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ItemsToolFolderTag extends AbstractInputTag implements Tag {

    private static final Log LOG = LogFactory.getLog(ItemsToolFolderTag.class);

    private String doubleClickListeners;

    private String textPosition;

    private String borderType;

    private String selectionListeners;

    private String checkListeners;

    private String checkedValues;

    private String readOnly;

    private String verticalAlignment;

    private String showDropDownMark;

    private String itemHiddenMode;

    public String getComponentType() {
        return ItemsToolFolderComponent.COMPONENT_TYPE;
    }

    public final String getDoubleClickListener() {
        return doubleClickListeners;
    }

    public final void setDoubleClickListener(String doubleClickListeners) {
        this.doubleClickListeners = doubleClickListeners;
    }

    public final String getTextPosition() {
        return textPosition;
    }

    public final void setTextPosition(String textPosition) {
        this.textPosition = textPosition;
    }

    public final String getBorderType() {
        return borderType;
    }

    public final void setBorderType(String borderType) {
        this.borderType = borderType;
    }

    public final String getSelectionListener() {
        return selectionListeners;
    }

    public final void setSelectionListener(String selectionListeners) {
        this.selectionListeners = selectionListeners;
    }

    public final String getCheckListener() {
        return checkListeners;
    }

    public final void setCheckListener(String checkListeners) {
        this.checkListeners = checkListeners;
    }

    public final String getCheckedValues() {
        return checkedValues;
    }

    public final void setCheckedValues(String checkedValues) {
        this.checkedValues = checkedValues;
    }

    public final String getReadOnly() {
        return readOnly;
    }

    public final void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public final String getVerticalAlignment() {
        return verticalAlignment;
    }

    public final void setVerticalAlignment(String verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public final String getShowDropDownMark() {
        return showDropDownMark;
    }

    public final void setShowDropDownMark(String showDropDownMark) {
        this.showDropDownMark = showDropDownMark;
    }

    public final String getItemHiddenMode() {
        return itemHiddenMode;
    }

    public final void setItemHiddenMode(String itemHiddenMode) {
        this.itemHiddenMode = itemHiddenMode;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (ItemsToolFolderComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  textPosition='" + textPosition + "'");
            LOG.debug("  borderType='" + borderType + "'");
            LOG.debug("  checkedValues='" + checkedValues + "'");
            LOG.debug("  readOnly='" + readOnly + "'");
            LOG.debug("  verticalAlignment='" + verticalAlignment + "'");
            LOG.debug("  showDropDownMark='" + showDropDownMark + "'");
            LOG.debug("  itemHiddenMode='" + itemHiddenMode + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof ItemsToolFolderComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'ItemsToolFolderComponent'.");
        }

        ItemsToolFolderComponent component = (ItemsToolFolderComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (doubleClickListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.DOUBLE_CLICK_LISTENER_TYPE,
                    doubleClickListeners);
        }

        if (textPosition != null) {
            if (isValueReference(textPosition)) {
                ValueBinding vb = application.createValueBinding(textPosition);
                component.setValueBinding(Properties.TEXT_POSITION, vb);

            } else {
                component.setTextPosition(textPosition);
            }
        }

        if (borderType != null) {
            if (isValueReference(borderType)) {
                ValueBinding vb = application.createValueBinding(borderType);
                component.setValueBinding(Properties.BORDER_TYPE, vb);

            } else {
                component.setBorderType(borderType);
            }
        }

        if (selectionListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
        }

        if (checkListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
        }

        if (checkedValues != null) {
            ValueBinding vb = application.createValueBinding(checkedValues);
            component.setValueBinding(Properties.CHECKED_VALUES, vb);
        }

        if (readOnly != null) {
            if (isValueReference(readOnly)) {
                ValueBinding vb = application.createValueBinding(readOnly);
                component.setValueBinding(Properties.READ_ONLY, vb);

            } else {
                component.setReadOnly(getBool(readOnly));
            }
        }

        if (verticalAlignment != null) {
            if (isValueReference(verticalAlignment)) {
                ValueBinding vb = application
                        .createValueBinding(verticalAlignment);
                component.setValueBinding(Properties.VERTICAL_ALIGNMENT, vb);

            } else {
                component.setVerticalAlignment(verticalAlignment);
            }
        }

        if (showDropDownMark != null) {
            if (isValueReference(showDropDownMark)) {
                ValueBinding vb = application
                        .createValueBinding(showDropDownMark);
                component.setValueBinding(Properties.SHOW_DROP_DOWN_MARK, vb);

            } else {
                component.setShowDropDownMark(getBool(showDropDownMark));
            }
        }

        if (itemHiddenMode != null) {
            if (isValueReference(itemHiddenMode)) {
                ValueBinding vb = application
                        .createValueBinding(itemHiddenMode);
                component.setValueBinding(Properties.ITEM_HIDDEN_MODE, vb);

            } else {
                component.setItemHiddenMode(itemHiddenMode);
            }
        }
    }

    public void release() {
        doubleClickListeners = null;
        textPosition = null;
        borderType = null;
        selectionListeners = null;
        checkListeners = null;
        checkedValues = null;
        readOnly = null;
        verticalAlignment = null;
        showDropDownMark = null;
        itemHiddenMode = null;

        super.release();
    }

}
