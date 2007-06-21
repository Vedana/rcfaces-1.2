package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.component.iterator.IRadioButtonIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HorizontalTextPositionConverter;
import org.rcfaces.core.internal.tools.RadioButtonTools;

/**
 * <p>
 * The radioButton Component is based on the standard HTML tag &lt;INPUT
 * TYPE="radio"&gt;. It can interoperate automatically with other radioButtons
 * from the same group.
 * </p>
 * <p>
 * The radioButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class RadioButtonComponent extends AbstractInputComponent implements
        ITextCapability, ITextDirectionCapability,
        IHorizontalTextPositionCapability, ISelectionEventCapability,
        IReadOnlyCapability, IAlternateTextCapability, ISelectedCapability,
        IRadioValueCapability, IRequiredCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.radioButton";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractInputComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "selectionListener", "alternateText", "selected", "text",
                "required", "radioValue", "groupName", "readOnly",
                "textPosition", "textDirection" }));
    }

    public RadioButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public RadioButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    protected Converter getTextPositionConverter() {

        return HorizontalTextPositionConverter.SINGLETON;

    }

    public void setTextPosition(String textPosition) {

        setTextPosition(((Integer) getTextPositionConverter().getAsObject(null,
                this, textPosition)).intValue());

    }

    public RadioButtonComponent getSelectedFromSameGroup() {

        return RadioButtonTools.getSelectedRadioButtonFromSameGroup(this);

    }

    public IRadioButtonIterator listSameGroup() {

        return RadioButtonTools.listRadioButtonSameGroup(this);

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

    public int getTextDirection() {
        return getTextDirection(null);
    }

    /**
     * See {@link #getTextDirection() getTextDirection()} for more details
     */
    public int getTextDirection(javax.faces.context.FacesContext facesContext) {
        return engine
                .getIntProperty(Properties.TEXT_DIRECTION, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "textDirection" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isTextDirectionSetted() {
        return engine.isPropertySetted(Properties.TEXT_DIRECTION);
    }

    public void setTextDirection(int textDirection) {
        engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
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

    public final void addSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        addFacesListener(listener);
    }

    public final void removeSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listSelectionListeners() {
        return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
    }

    public boolean isReadOnly() {
        return isReadOnly(null);
    }

    /**
     * See {@link #isReadOnly() isReadOnly()} for more details
     */
    public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.READ_ONLY, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "readOnly" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isReadOnlySetted() {
        return engine.isPropertySetted(Properties.READ_ONLY);
    }

    public void setReadOnly(boolean readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public java.lang.String getAlternateText() {
        return getAlternateText(null);
    }

    /**
     * See {@link #getAlternateText() getAlternateText()} for more details
     */
    public java.lang.String getAlternateText(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getStringProperty(Properties.ALTERNATE_TEXT, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "alternateText" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isAlternateTextSetted() {
        return engine.isPropertySetted(Properties.ALTERNATE_TEXT);
    }

    public void setAlternateText(java.lang.String alternateText) {
        engine.setProperty(Properties.ALTERNATE_TEXT, alternateText);
    }

    public boolean isSelected() {
        return isSelected(null);
    }

    /**
     * See {@link #isSelected() isSelected()} for more details
     */
    public boolean isSelected(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.SELECTED, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "selected" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isSelectedSetted() {
        return engine.isPropertySetted(Properties.SELECTED);
    }

    public void setSelected(boolean selected) {
        engine.setProperty(Properties.SELECTED, selected);
    }

    public java.lang.Object getRadioValue() {
        return getRadioValue(null);
    }

    /**
     * See {@link #getRadioValue() getRadioValue()} for more details
     */
    public java.lang.Object getRadioValue(
            javax.faces.context.FacesContext facesContext) {
        return engine.getProperty(Properties.RADIO_VALUE, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "radioValue" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isRadioValueSetted() {
        return engine.isPropertySetted(Properties.RADIO_VALUE);
    }

    public void setRadioValue(java.lang.Object radioValue) {
        engine.setProperty(Properties.RADIO_VALUE, radioValue);
    }

    public java.lang.String getGroupName() {
        return getGroupName(null);
    }

    /**
     * See {@link #getGroupName() getGroupName()} for more details
     */
    public java.lang.String getGroupName(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "groupName" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isGroupNameSetted() {
        return engine.isPropertySetted(Properties.GROUP_NAME);
    }

    public void setGroupName(java.lang.String groupName) {
        engine.setProperty(Properties.GROUP_NAME, groupName);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
