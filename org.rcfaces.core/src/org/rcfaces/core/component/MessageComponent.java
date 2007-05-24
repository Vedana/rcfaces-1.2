package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

/**
 * <p>
 * The message Component is a placeholder for error messages (only one is
 * shown).
 * </p>
 * <p>
 * The message Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class MessageComponent extends AbstractMessageComponent implements
        IImageSizeCapability, ITextCapability, ISeverityStyleClassCapability,
        ISeverityImagesCapability, IImageAccessorsCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.message";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractMessageComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "errorStyleClass", "imageHeight", "fatalStyleClass",
                "imageURL", "errorImageURL", "warnStyleClass",
                "showActiveComponentMessage", "showIfMessage", "warnImageURL",
                "styleClass", "text", "imageWidth", "infoStyleClass",
                "infoImageURL", "setFocusIfMessage", "fatalImageURL" }));
    }

    public MessageComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public MessageComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final IContentAccessors getImageAccessors(FacesContext facesContext) {

        return ImageAccessorTools.createImageAccessors(facesContext, this,
                engine);

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

    public java.lang.String getText() {
        return getText(null);
    }

    /**
     * See {@link #getText() getText()} for more details
     */
    public java.lang.String getText(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.TEXT, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "text" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isTextSetted() {
        return engine.isPropertySetted(Properties.TEXT);
    }

    public void setText(java.lang.String text) {
        engine.setProperty(Properties.TEXT, text);
    }

    public java.lang.String getErrorStyleClass() {
        return getErrorStyleClass(null);
    }

    /**
     * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
     */
    public java.lang.String getErrorStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ERROR_STYLE_CLASS,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "errorStyleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isErrorStyleClassSetted() {
        return engine.isPropertySetted(Properties.ERROR_STYLE_CLASS);
    }

    public void setErrorStyleClass(java.lang.String errorStyleClass) {
        engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
    }

    public java.lang.String getFatalStyleClass() {
        return getFatalStyleClass(null);
    }

    /**
     * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
     */
    public java.lang.String getFatalStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.FATAL_STYLE_CLASS,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isFatalStyleClassSetted() {
        return engine.isPropertySetted(Properties.FATAL_STYLE_CLASS);
    }

    public void setFatalStyleClass(java.lang.String fatalStyleClass) {
        engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
    }

    public java.lang.String getInfoStyleClass() {
        return getInfoStyleClass(null);
    }

    /**
     * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
     */
    public java.lang.String getInfoStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.INFO_STYLE_CLASS,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "infoStyleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isInfoStyleClassSetted() {
        return engine.isPropertySetted(Properties.INFO_STYLE_CLASS);
    }

    public void setInfoStyleClass(java.lang.String infoStyleClass) {
        engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
    }

    public java.lang.String getWarnStyleClass() {
        return getWarnStyleClass(null);
    }

    /**
     * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
     */
    public java.lang.String getWarnStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.WARN_STYLE_CLASS,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "warnStyleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isWarnStyleClassSetted() {
        return engine.isPropertySetted(Properties.WARN_STYLE_CLASS);
    }

    public void setWarnStyleClass(java.lang.String warnStyleClass) {
        engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
    }

    public java.lang.String getErrorImageURL() {
        return getErrorImageURL(null);
    }

    /**
     * See {@link #getErrorImageURL() getErrorImageURL()} for more details
     */
    public java.lang.String getErrorImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ERROR_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "errorImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isErrorImageURLSetted() {
        return engine.isPropertySetted(Properties.ERROR_IMAGE_URL);
    }

    public void setErrorImageURL(java.lang.String errorImageURL) {
        engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
    }

    public java.lang.String getFatalImageURL() {
        return getFatalImageURL(null);
    }

    /**
     * See {@link #getFatalImageURL() getFatalImageURL()} for more details
     */
    public java.lang.String getFatalImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.FATAL_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "fatalImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isFatalImageURLSetted() {
        return engine.isPropertySetted(Properties.FATAL_IMAGE_URL);
    }

    public void setFatalImageURL(java.lang.String fatalImageURL) {
        engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
    }

    public java.lang.String getInfoImageURL() {
        return getInfoImageURL(null);
    }

    /**
     * See {@link #getInfoImageURL() getInfoImageURL()} for more details
     */
    public java.lang.String getInfoImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getStringProperty(Properties.INFO_IMAGE_URL, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "infoImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isInfoImageURLSetted() {
        return engine.isPropertySetted(Properties.INFO_IMAGE_URL);
    }

    public void setInfoImageURL(java.lang.String infoImageURL) {
        engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
    }

    public java.lang.String getWarnImageURL() {
        return getWarnImageURL(null);
    }

    /**
     * See {@link #getWarnImageURL() getWarnImageURL()} for more details
     */
    public java.lang.String getWarnImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getStringProperty(Properties.WARN_IMAGE_URL, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "warnImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isWarnImageURLSetted() {
        return engine.isPropertySetted(Properties.WARN_IMAGE_URL);
    }

    public void setWarnImageURL(java.lang.String warnImageURL) {
        engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
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

    public final IContentAccessors getImageAccessors() {

        return getImageAccessors(null);

    }

    public final boolean isSetFocusIfMessage() {
        return isSetFocusIfMessage(null);
    }

    public final boolean isSetFocusIfMessage(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.SET_FOCUS_IF_MESSAGE, false,
                facesContext);
    }

    public final void setSetFocusIfMessage(boolean setFocusIfMessage) {
        engine.setProperty(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
    }

    /**
     * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isSetFocusIfMessageSetted() {
        return engine.isPropertySetted(Properties.SET_FOCUS_IF_MESSAGE);
    }

    public final boolean isShowIfMessage() {
        return isShowIfMessage(null);
    }

    public final boolean isShowIfMessage(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.SHOW_IF_MESSAGE, false,
                facesContext);
    }

    public final void setShowIfMessage(boolean showIfMessage) {
        engine.setProperty(Properties.SHOW_IF_MESSAGE, showIfMessage);
    }

    /**
     * Returns <code>true</code> if the attribute "showIfMessage" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isShowIfMessageSetted() {
        return engine.isPropertySetted(Properties.SHOW_IF_MESSAGE);
    }

    public final boolean isShowActiveComponentMessage() {
        return isShowActiveComponentMessage(null);
    }

    public final boolean isShowActiveComponentMessage(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE,
                false, facesContext);
    }

    public final void setShowActiveComponentMessage(
            boolean showActiveComponentMessage) {
        engine.setProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE,
                showActiveComponentMessage);
    }

    /**
     * Returns <code>true</code> if the attribute "showActiveComponentMessage"
     * is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isShowActiveComponentMessageSetted() {
        return engine
                .isPropertySetted(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
