package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.AbstractItemComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;

public class UIImageItemComponent extends AbstractItemComponent implements
        IVisibilityCapability, IToolTipCapability, IImageCapability,
        IStatesImageCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.UIImageItem";

    public UIImageItemComponent() {
        setRendererType(null);
    }

    public UIImageItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final void setToolTip(String text) {

        setItemDescription(text);

    }

    public final void setToolTip(ValueBinding text) {

        setValueBinding("itemDescription", text);

    }

    public final String getToolTip() {

        return getItemDescription();

    }

    public final void setHiddenMode(String hiddenMode) {

        setHiddenMode(((Integer) HiddenModeConverter.SINGLETON.getAsObject(
                null, null, hiddenMode)).intValue());

    }

    public final java.lang.Boolean getVisible() {
        return getVisible(null);
    }

    public final java.lang.Boolean getVisible(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBooleanProperty(Properties.VISIBLE, facesContext);
    }

    public final void setVisible(java.lang.Boolean visible) {
        engine.setProperty(Properties.VISIBLE, visible);
    }

    public final void setVisible(ValueBinding visible) {
        engine.setProperty(Properties.VISIBLE, visible);
    }

    public final int getHiddenMode() {
        return getHiddenMode(null);
    }

    public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.HIDDEN_MODE, 0, facesContext);
    }

    public final void setHiddenMode(int hiddenMode) {
        engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
    }

    public final void setHiddenMode(ValueBinding hiddenMode) {
        engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
    }

    public final java.lang.String getToolTipText() {
        return getToolTipText(null);
    }

    public final java.lang.String getToolTipText(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
    }

    public final void setToolTipText(java.lang.String toolTipText) {
        engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
    }

    public final void setToolTipText(ValueBinding toolTipText) {
        engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
    }

    public final java.lang.String getImageURL() {
        return getImageURL(null);
    }

    public final java.lang.String getImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
    }

    public final void setImageURL(java.lang.String imageURL) {
        engine.setProperty(Properties.IMAGE_URL, imageURL);
    }

    public final void setImageURL(ValueBinding imageURL) {
        engine.setProperty(Properties.IMAGE_URL, imageURL);
    }

    public final java.lang.String getDisabledImageURL() {
        return getDisabledImageURL(null);
    }

    public final java.lang.String getDisabledImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.DISABLED_IMAGE_URL,
                facesContext);
    }

    public final void setDisabledImageURL(java.lang.String disabledImageURL) {
        engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
    }

    public final void setDisabledImageURL(ValueBinding disabledImageURL) {
        engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
    }

    public final java.lang.String getHoverImageURL() {
        return getHoverImageURL(null);
    }

    public final java.lang.String getHoverImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.HOVER_IMAGE_URL,
                facesContext);
    }

    public final void setHoverImageURL(java.lang.String hoverImageURL) {
        engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
    }

    public final void setHoverImageURL(ValueBinding hoverImageURL) {
        engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
    }

    public final java.lang.String getSelectedImageURL() {
        return getSelectedImageURL(null);
    }

    public final java.lang.String getSelectedImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.SELECTED_IMAGE_URL,
                facesContext);
    }

    public final void setSelectedImageURL(java.lang.String selectedImageURL) {
        engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
    }

    public final void setSelectedImageURL(ValueBinding selectedImageURL) {
        engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
    }

    public void release() {
        super.release();
    }
}
