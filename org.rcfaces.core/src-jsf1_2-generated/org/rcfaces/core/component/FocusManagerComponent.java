package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The focusManager Component is a non-visual component.</p>
 * <p>It allows to deal with the focus on the current page.</p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/FocuManagerComponent.html">focuManager</a> renderer is link to the <a href="/jsdocs/index.html?f_focuManager.html" target="_blank">f_focuManager</a> javascript class. f_focuManager extends f_object, fa_serializable</p>
 */
public class FocusManagerComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(FocusManagerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.focusManager";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"autoFocusFrom","focusId","autoFocus","setFocusIfMessage"}));
	}

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
		String s = engine.getStringProperty(Properties.FOCUS_ID, facesContext);
		return s;
	}

	/**
	 * Sets a component's id. This component will get the focus.
	 * @param focusId id
	 */
	public void setFocusId(String focusId) {
		engine.setProperty(Properties.FOCUS_ID, focusId);
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
		return engine.isPropertySetted(Properties.FOCUS_ID);
	}

	public boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SET_FOCUS_IF_MESSAGE, true, facesContext);
	}

	public void setSetFocusIfMessage(boolean setFocusIfMessage) {
		engine.setProperty(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSetFocusIfMessageSetted() {
		return engine.isPropertySetted(Properties.SET_FOCUS_IF_MESSAGE);
	}

	public boolean isAutoFocus() {
		return isAutoFocus(null);
	}

	public boolean isAutoFocus(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_FOCUS, false, facesContext);
	}

	public void setAutoFocus(boolean autoFocus) {
		engine.setProperty(Properties.AUTO_FOCUS, autoFocus);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoFocus" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isAutoFocusSetted() {
		return engine.isPropertySetted(Properties.AUTO_FOCUS);
	}

	public String getAutoFocusFrom() {
		return getAutoFocusFrom(null);
	}

	public String getAutoFocusFrom(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.AUTO_FOCUS_FROM, facesContext);
		return s;
	}

	public void setAutoFocusFrom(String autoFocusFrom) {
		engine.setProperty(Properties.AUTO_FOCUS_FROM, autoFocusFrom);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoFocusFrom" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isAutoFocusFromSetted() {
		return engine.isPropertySetted(Properties.AUTO_FOCUS_FROM);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
