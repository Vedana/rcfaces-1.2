package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;
import java.util.Collection;

public class TextEditorImageButtonComponent extends ImageButtonComponent implements 
	IForCapability,
	IRadioGroupCapability {

	private static final Log LOG = LogFactory.getLog(TextEditorImageButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEditorImageButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ImageButtonComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.GROUP_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return getStateHelper().get(Properties.GROUP_NAME)!=null;
	}

	public void setGroupName(java.lang.String groupName) {
		getStateHelper().put(Properties.GROUP_NAME, groupName);
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
		return (String)getStateHelper().eval(Properties.TYPE);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	public void setType(String type) {
		 getStateHelper().put(Properties.TYPE, type);
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
		return getStateHelper().get(Properties.TYPE)!=null;
	}

}
