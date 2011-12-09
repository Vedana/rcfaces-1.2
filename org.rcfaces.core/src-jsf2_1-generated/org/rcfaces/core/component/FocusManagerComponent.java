package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

/**
 * <p>The focusManager Component is a non-visual component.</p>
 * <p>It allows to deal with the focus on the current page.</p>
 */
public class FocusManagerComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(FocusManagerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.focusManager";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public FocusManagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public FocusManagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	/**
	 * Returns a component's id. This component will get the focus.
	 * @return id
	 */
	public String getFocusId() {
		return getFocusId(null);
	}

	/**
	 * Returns a component's id. This component will get the focus.
	 * @return id
	 */
	public String getFocusId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOCUS_ID);
	}

	/**
	 * Sets a component's id. This component will get the focus.
	 * @param focusId id
	 */
	public void setFocusId(String focusId) {
		 getStateHelper().put(Properties.FOCUS_ID, focusId);
	}

	/**
	 * Sets a component's id. This component will get the focus.
	 * @param focusId id
	 */
	/**
	 * Returns <code>true</code> if the attribute "focusId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFocusIdSetted() {
		return getStateHelper().get(Properties.FOCUS_ID)!=null;
	}

	public boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SET_FOCUS_IF_MESSAGE, true);
	}

	public void setSetFocusIfMessage(boolean setFocusIfMessage) {
		 getStateHelper().put(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSetFocusIfMessageSetted() {
		return getStateHelper().get(Properties.SET_FOCUS_IF_MESSAGE)!=null;
	}

}
