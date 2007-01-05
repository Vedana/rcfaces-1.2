package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.AbstractCommandComponent;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import java.util.Set;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

/**
 * A button Component highly customizable through its API.
 */
public class CustomButtonComponent extends AbstractCommandComponent implements 
	IBorderTypeCapability,
	IBorderCapability,
	ISelectionEventCapability,
	IDoubleClickEventCapability,
	IReadOnlyCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.customButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCommandComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","doubleClickListener","border","readOnly","borderType"}));
	}

	public CustomButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CustomButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setValue(Object value) {


				if (value instanceof javax.faces.el.ValueBinding) {
					setValue((javax.faces.el.ValueBinding)value);
					return;
				}
				engine.setValue(Properties.VALUE, value);
			
	}

	public final void setValue(ValueBinding value) {


				engine.setValueBinding(Properties.VALUE, value);
			
	}

	public final java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public final java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	public final void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	/**
	 * See {@link #setBorderType(String) setBorderType(String)} for more details
	 */
	public final void setBorderType(ValueBinding borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
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

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
