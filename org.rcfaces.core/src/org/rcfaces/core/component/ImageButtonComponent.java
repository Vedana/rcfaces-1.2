package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

/**
 * <p>
 * The imageButton Component is a <a href="/comps/buttonComponent.html">button
 * Component</a> that can show an image.
 * </p>
 * <p>
 * The imageButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ImageButtonComponent extends ButtonComponent implements
        IImageButtonFamilly {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.imageButton";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            ButtonComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "selectionListener", "hoverImageURL", "imageHeight",
                "imageURL", "disabledImageURL", "disabled", "text",
                "imageWidth", "selectedImageURL", "border", "borderType",
                "readOnly", "textPosition" }));
    }

    public ImageButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ImageButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    protected Converter getTextPositionConverter() {

        return TextPositionConverter.SINGLETON;

    }

    public void setTextPosition(String textPosition) {

        setTextPosition(((Integer) getTextPositionConverter().getAsObject(null,
                this, textPosition)).intValue());

    }

    public java.lang.String getImageURL() {
        return getImageURL(null);
    }

    /**
     * See {@link #getImageURL() getImageURL()} for more details
     */
    public java.lang.String getImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "imageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isImageURLSetted() {
        return engine.isPropertySetted(Properties.IMAGE_URL);
    }

    public void setImageURL(java.lang.String imageURL) {
        engine.setProperty(Properties.IMAGE_URL, imageURL);
    }

    public java.lang.String getDisabledImageURL() {
        return getDisabledImageURL(null);
    }

    /**
     * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
     */
    public java.lang.String getDisabledImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.DISABLED_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "disabledImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isDisabledImageURLSetted() {
        return engine.isPropertySetted(Properties.DISABLED_IMAGE_URL);
    }

    public void setDisabledImageURL(java.lang.String disabledImageURL) {
        engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
    }

    public java.lang.String getHoverImageURL() {
        return getHoverImageURL(null);
    }

    /**
     * See {@link #getHoverImageURL() getHoverImageURL()} for more details
     */
    public java.lang.String getHoverImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.HOVER_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "hoverImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isHoverImageURLSetted() {
        return engine.isPropertySetted(Properties.HOVER_IMAGE_URL);
    }

    public void setHoverImageURL(java.lang.String hoverImageURL) {
        engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
    }

    public java.lang.String getSelectedImageURL() {
        return getSelectedImageURL(null);
    }

    /**
     * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
     */
    public java.lang.String getSelectedImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.SELECTED_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "selectedImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isSelectedImageURLSetted() {
        return engine.isPropertySetted(Properties.SELECTED_IMAGE_URL);
    }

    public void setSelectedImageURL(java.lang.String selectedImageURL) {
        engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
    }

    public boolean isBorder() {
        return isBorder(null);
    }

    /**
     * See {@link #isBorder() isBorder()} for more details
     */
    public boolean isBorder(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.BORDER, true, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "border" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBorderSetted() {
        return engine.isPropertySetted(Properties.BORDER);
    }

    public void setBorder(boolean border) {
        engine.setProperty(Properties.BORDER, border);
    }

    public java.lang.String getBorderType() {
        return getBorderType(null);
    }

    /**
     * See {@link #getBorderType() getBorderType()} for more details
     */
    public java.lang.String getBorderType(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "borderType" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBorderTypeSetted() {
        return engine.isPropertySetted(Properties.BORDER_TYPE);
    }

    public void setBorderType(java.lang.String borderType) {
        engine.setProperty(Properties.BORDER_TYPE, borderType);
    }

    public int getTextPosition() {
        return getTextPosition(null);
    }

    /**
     * See {@link #getTextPosition() getTextPosition()} for more details
     */
    public int getTextPosition(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.TEXT_POSITION, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "textPosition" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isTextPositionSetted() {
        return engine.isPropertySetted(Properties.TEXT_POSITION);
    }

    public void setTextPosition(int textPosition) {
        engine.setProperty(Properties.TEXT_POSITION, textPosition);
    }

    public int getImageHeight() {
        return getImageHeight(null);
    }

    /**
     * See {@link #getImageHeight() getImageHeight()} for more details
     */
    public int getImageHeight(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.IMAGE_HEIGHT, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "imageHeight" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isImageHeightSetted() {
        return engine.isPropertySetted(Properties.IMAGE_HEIGHT);
    }

    public void setImageHeight(int imageHeight) {
        engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
    }

    public int getImageWidth() {
        return getImageWidth(null);
    }

    /**
     * See {@link #getImageWidth() getImageWidth()} for more details
     */
    public int getImageWidth(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.IMAGE_WIDTH, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "imageWidth" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isImageWidthSetted() {
        return engine.isPropertySetted(Properties.IMAGE_WIDTH);
    }

    public void setImageWidth(int imageWidth) {
        engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
    }

    public IContentAccessors getImageAccessors() {

        return getImageAccessors(null);

    }

    public IContentAccessors getImageAccessors(FacesContext facesContext) {

        return ImageAccessorTools.createImageAccessors(facesContext, this,
                engine);

    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
