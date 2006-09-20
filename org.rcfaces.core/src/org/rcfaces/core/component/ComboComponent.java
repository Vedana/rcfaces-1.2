package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IRequiredCapability;

public class ComboComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IRequiredCapability,
	IFilterCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.combo";


	public ComboComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboComponent(String componentId) {
		this();
		setId(componentId);
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

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public void release() {
		super.release();
	}
}
