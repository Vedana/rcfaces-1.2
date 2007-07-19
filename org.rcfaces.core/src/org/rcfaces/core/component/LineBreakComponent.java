package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;

/**
 * <p>
 * The lineBreak Component is the &lt;BR&gt; HTML equivalent.
 * </p>
 * <p>
 * It is used often when simple HTML is not desirable : for example if a part of
 * a page is loaded via AJAX it might be easier to have only a jsf tree memory
 * represantation.
 * </p>
 * <p>
 * The lineBreak Component has the following capability :
 * <ul>
 * <li>Visibility</li>
 * </ul>
 * </p>
 */
public class LineBreakComponent extends CameliaBaseComponent implements
        IStyleClassCapability, IVisibilityCapability, IHiddenModeCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.lineBreak";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            CameliaBaseComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] { "styleClass",
                "visible", "hiddenMode", "rendered" }));
    }

    public LineBreakComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public LineBreakComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void setHiddenMode(String hiddenMode) {

        setHiddenMode(((Integer) HiddenModeConverter.SINGLETON.getAsObject(
                null, this, hiddenMode)).intValue());

    }

    public Boolean getVisibleState(FacesContext facesContext) {

        if (engine.isPropertySetted(Properties.VISIBLE) == false) {
            return null;
        }

        return Boolean.valueOf(isVisible(facesContext));

    }

    public java.lang.String getStyleClass() {
        return getStyleClass(null);
    }

    /**
     * See {@link #getStyleClass() getStyleClass()} for more details
     */
    public java.lang.String getStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "styleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isStyleClassSetted() {
        return engine.isPropertySetted(Properties.STYLE_CLASS);
    }

    public void setStyleClass(java.lang.String styleClass) {
        engine.setProperty(Properties.STYLE_CLASS, styleClass);
    }

    public boolean isVisible() {
        return isVisible(null);
    }

    /**
     * See {@link #isVisible() isVisible()} for more details
     */
    public boolean isVisible(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "visible" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isVisibleSetted() {
        return engine.isPropertySetted(Properties.VISIBLE);
    }

    public void setVisible(boolean visible) {
        engine.setProperty(Properties.VISIBLE, visible);
    }

    public Boolean getVisibleState() {

        return getVisibleState(null);

    }

    public int getHiddenMode() {
        return getHiddenMode(null);
    }

    /**
     * See {@link #getHiddenMode() getHiddenMode()} for more details
     */
    public int getHiddenMode(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.HIDDEN_MODE, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "hiddenMode" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isHiddenModeSetted() {
        return engine.isPropertySetted(Properties.HIDDEN_MODE);
    }

    public void setHiddenMode(int hiddenMode) {
        engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
