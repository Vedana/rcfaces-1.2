package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * An englobing tag that changes the style class surronuding a component when a
 * particular message is emitted for a component. ex : &lt;v:styledMessage
 * errorStyleClass="formErreur" for="ef1"&gt; &lt;v:textEntry id="ef1" ... /&gt;
 * &lt;/v:styledMessage&gt;
 */
public class StyledMessageComponent extends AbstractBasicComponent implements
        IBackgroundImageCapability, IBorderCapability, IMouseEventCapability,
        IInitEventCapability, IForCapability, ISeverityStyleClassCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.styledMessage";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractBasicComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "errorStyleClass", "fatalStyleClass", "mouseOverListener",
                "backgroundImageVerticalPosition", "warnStyleClass",
                "showIfMessage", "styleClass",
                "backgroundImageHorizontalPosition",
                "backgroundImageVerticalRepeat", "infoStyleClass",
                "backgroundImageHorizontalRepeat", "initListener",
                "backgroundImageURL", "border", "mouseOutListener",
                "setFocusIfMessage", "for" }));
    }

    public StyledMessageComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public StyledMessageComponent(String componentId) {
        this();
        setId(componentId);
    }

    public java.lang.String getBackgroundImageHorizontalPosition() {
        return getBackgroundImageHorizontalPosition(null);
    }

    /**
     * See
     * {@link #getBackgroundImageHorizontalPosition() getBackgroundImageHorizontalPosition()}
     * for more details
     */
    public java.lang.String getBackgroundImageHorizontalPosition(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(
                Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute
     * "backgroundImageHorizontalPosition" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBackgroundImageHorizontalPositionSetted() {
        return engine
                .isPropertySetted(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION);
    }

    public void setBackgroundImageHorizontalPosition(
            java.lang.String backgroundImageHorizontalPosition) {
        engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION,
                backgroundImageHorizontalPosition);
    }

    public boolean isBackgroundImageHorizontalRepeat() {
        return isBackgroundImageHorizontalRepeat(null);
    }

    /**
     * See
     * {@link #isBackgroundImageHorizontalRepeat() isBackgroundImageHorizontalRepeat()}
     * for more details
     */
    public boolean isBackgroundImageHorizontalRepeat(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(
                Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute
     * "backgroundImageHorizontalRepeat" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBackgroundImageHorizontalRepeatSetted() {
        return engine
                .isPropertySetted(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT);
    }

    public void setBackgroundImageHorizontalRepeat(
            boolean backgroundImageHorizontalRepeat) {
        engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT,
                backgroundImageHorizontalRepeat);
    }

    public java.lang.String getBackgroundImageURL() {
        return getBackgroundImageURL(null);
    }

    /**
     * See {@link #getBackgroundImageURL() getBackgroundImageURL()} for more
     * details
     */
    public java.lang.String getBackgroundImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.BACKGROUND_IMAGE_URL,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "backgroundImageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBackgroundImageURLSetted() {
        return engine.isPropertySetted(Properties.BACKGROUND_IMAGE_URL);
    }

    public void setBackgroundImageURL(java.lang.String backgroundImageURL) {
        engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
    }

    public java.lang.String getBackgroundImageVerticalPosition() {
        return getBackgroundImageVerticalPosition(null);
    }

    /**
     * See
     * {@link #getBackgroundImageVerticalPosition() getBackgroundImageVerticalPosition()}
     * for more details
     */
    public java.lang.String getBackgroundImageVerticalPosition(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(
                Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute
     * "backgroundImageVerticalPosition" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBackgroundImageVerticalPositionSetted() {
        return engine
                .isPropertySetted(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION);
    }

    public void setBackgroundImageVerticalPosition(
            java.lang.String backgroundImageVerticalPosition) {
        engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION,
                backgroundImageVerticalPosition);
    }

    public boolean isBackgroundImageVerticalRepeat() {
        return isBackgroundImageVerticalRepeat(null);
    }

    /**
     * See
     * {@link #isBackgroundImageVerticalRepeat() isBackgroundImageVerticalRepeat()}
     * for more details
     */
    public boolean isBackgroundImageVerticalRepeat(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(
                Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute
     * "backgroundImageVerticalRepeat" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isBackgroundImageVerticalRepeatSetted() {
        return engine
                .isPropertySetted(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT);
    }

    public void setBackgroundImageVerticalRepeat(
            boolean backgroundImageVerticalRepeat) {
        engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT,
                backgroundImageVerticalRepeat);
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

    public final void addMouseOutListener(
            org.rcfaces.core.event.IMouseOutListener listener) {
        addFacesListener(listener);
    }

    public final void removeMouseOutListener(
            org.rcfaces.core.event.IMouseOutListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listMouseOutListeners() {
        return getFacesListeners(org.rcfaces.core.event.IMouseOutListener.class);
    }

    public final void addMouseOverListener(
            org.rcfaces.core.event.IMouseOverListener listener) {
        addFacesListener(listener);
    }

    public final void removeMouseOverListener(
            org.rcfaces.core.event.IMouseOverListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listMouseOverListeners() {
        return getFacesListeners(org.rcfaces.core.event.IMouseOverListener.class);
    }

    public final void addInitListener(
            org.rcfaces.core.event.IInitListener listener) {
        addFacesListener(listener);
    }

    public final void removeInitListener(
            org.rcfaces.core.event.IInitListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listInitListeners() {
        return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
    }

    public java.lang.String getFor() {
        return getFor(null);
    }

    /**
     * See {@link #getFor() getFor()} for more details
     */
    public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.FOR, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "for" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isForSetted() {
        return engine.isPropertySetted(Properties.FOR);
    }

    public void setFor(java.lang.String forValue) {
        engine.setProperty(Properties.FOR, forValue);
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

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
