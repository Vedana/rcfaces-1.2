package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.RadioButtonComponent;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ISelectedCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import org.rcfaces.core.component.iterator.IRadioButtonIterator;
import org.rcfaces.core.internal.tools.RadioButtonTools;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.internal.converter.HorizontalTextPositionConverter;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;

public class RadioButtonComponent extends AbstractInputComponent implements 
	ITextCapability,
	IHorizontalTextPositionCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	ISelectedCapability,
	IRadioValueCapability,
	IRequiredCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.radioButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","selected","text","required","radioValue","groupName","readOnly","textPosition"}));
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

	public final void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, null, textPosition)).intValue());
		
	}

	public final RadioButtonComponent getSelectedFromSameGroup() {


			return RadioButtonTools.getSelectedRadioButtonFromSameGroup(this);
			
	}

	public final IRadioButtonIterator listSameGroup() {


			return RadioButtonTools.listRadioButtonSameGroup(this);
			
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final int getTextPosition() {
		return getTextPosition(null);
	}

	public final int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_POSITION,0, facesContext);
	}

	public final void setTextPosition(int textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public final void setTextPosition(ValueBinding textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final boolean isSelected() {
		return isSelected(null);
	}

	public final boolean isSelected(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTED, false, facesContext);
	}

	public final void setSelected(boolean selected) {
		engine.setProperty(Properties.SELECTED, selected);
	}

	public final void setSelected(ValueBinding selected) {
		engine.setProperty(Properties.SELECTED, selected);
	}

	public final java.lang.Object getRadioValue() {
		return getRadioValue(null);
	}

	public final java.lang.Object getRadioValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.RADIO_VALUE, facesContext);
	}

	public final void setRadioValue(java.lang.Object radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public final void setRadioValue(ValueBinding radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
