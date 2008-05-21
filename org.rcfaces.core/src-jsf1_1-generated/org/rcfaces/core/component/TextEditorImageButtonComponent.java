package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;
import javax.faces.el.ValueBinding;

public class TextEditorImageButtonComponent extends ImageButtonComponent implements 
	IForCapability,
	IRadioGroupCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEditorImageButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"groupName","for","type"}));
	}

	public TextEditorImageButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextEditorImageButtonComponent(String componentId) {
		this();
		setId(componentId);
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
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return engine.isPropertySetted(Properties.GROUP_NAME);
	}

	public void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	/**
	 * Returns a string value specifying the type of data (Java) to convert to or from.
	 * @return type of data
	 */
	public String getType() {
		return getType(null);
	}

	/**
	 * Returns a string value specifying the type of data (Java) to convert to or from.
	 * @return type of data
	 */
	public String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	public void setType(String type) {
		engine.setProperty(Properties.TYPE, type);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
