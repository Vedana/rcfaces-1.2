package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IToolTipIdCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ToolTipTools;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractCommandComponent;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.iterator.IToolTipIterator;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.internal.capability.IToolTipComponent;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * <p>The button Component is equivalent to the standard HTML tag &lt;BUTTON&gt;.</p>
 * <p>The button Component has the following capabilities :
 * <ul>
 * <li>ITextCapability</li>
 * <li>ITextDirectionCapability</li>
 * <li>ISelectionEventCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>IAlternateTextCapability</li>
 * <li>IFocusStyleClassCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ButtonComponent.html">button</a> renderer is linked to the <a href="/jsdocs/index.html?f_button.html" target="_blank">f_button</a> javascript class. f_box extends f_component, fa_asyncRender, fa_subMenu</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_button</td>
 * <td width="50%">Defines styles for the wrapper Input element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ButtonComponent extends AbstractCommandComponent implements 
	ITextCapability,
	ITextDirectionCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	IAlternateTextCapability,
	IFocusStyleClassCapability,
	IToolTipIdCapability,
	IToolTipComponent {

	private static final Log LOG = LogFactory.getLog(ButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.button";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCommandComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusStyleClass","selectionListener","text","readOnly","alternateText","toolTipId","textDirection"}));
	}

	public ButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IToolTipIterator listToolTips() {


			return ToolTipTools.listToolTips(this);
		
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
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
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
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

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
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
	public java.lang.String getAlternateText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALTERNATE_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return engine.isPropertySetted(Properties.ALTERNATE_TEXT);
	}

	public void setAlternateText(java.lang.String alternateText) {
		engine.setProperty(Properties.ALTERNATE_TEXT, alternateText);
	}

	public java.lang.String getFocusStyleClass() {
		return getFocusStyleClass(null);
	}

	/**
	 * See {@link #getFocusStyleClass() getFocusStyleClass()} for more details
	 */
	public java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "focusStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFocusStyleClassSetted() {
		return engine.isPropertySetted(Properties.FOCUS_STYLE_CLASS);
	}

	public void setFocusStyleClass(java.lang.String focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public java.lang.String getToolTipId() {
		return getToolTipId(null);
	}

	/**
	 * See {@link #getToolTipId() getToolTipId()} for more details
	 */
	public java.lang.String getToolTipId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipIdSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_ID);
	}

	public void setToolTipId(java.lang.String toolTipId) {
		engine.setProperty(Properties.TOOL_TIP_ID, toolTipId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
