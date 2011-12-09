package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HorizontalTextPositionConverter;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.ISelected3StatesCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * <p>The checkButton3States Component is a <a href="/comps/checkButtonComponent.html">CheckButton</a> with 3 states : Check, unchecked and undefined. It is often used to show the state of a group of checkButtons</p>
 * <p>This component is <b>experimental</b>.</p>
 * <p>The checkButton3States Component has the following capabilities :
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
public class CheckButton3StatesComponent extends AbstractInputComponent implements 
	ITextCapability,
	ITextDirectionCapability,
	IHorizontalTextPositionCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	IAlternateTextCapability,
	IFocusStyleClassCapability,
	ISelected3StatesCapability {

	private static final Log LOG = LogFactory.getLog(CheckButton3StatesComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.checkButton3States";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public CheckButton3StatesComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CheckButton3StatesComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Converter getTextPositionConverter() {


				return HorizontalTextPositionConverter.SINGLETON;
			
	}

	public boolean isSelected() {


			return SELECTED_STATE.equals(getSelectedState());
			
	}

	public boolean isUndeterminated() {


			return UNDETERMINATED_STATE.equals(getSelectedState());
			
	}

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		getStateHelper().put(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public int getTextPosition() {
		return getTextPosition(null);
	}

	/**
	 * See {@link #getTextPosition() getTextPosition()} for more details
	 */
	public int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_POSITION, IHorizontalTextPositionCapability.DEFAULT_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "textPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextPositionSetted() {
		return getStateHelper().get(Properties.TEXT_POSITION)!=null;
	}

	public void setTextPosition(int textPosition) {
		getStateHelper().put(Properties.TEXT_POSITION, textPosition);
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
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
	}

	public java.lang.String getAlternateText() {
		return getAlternateText(null);
	}

	/**
	 * See {@link #getAlternateText() getAlternateText()} for more details
	 */
	public java.lang.String getAlternateText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ALTERNATE_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return getStateHelper().get(Properties.ALTERNATE_TEXT)!=null;
	}

	public void setAlternateText(java.lang.String alternateText) {
		getStateHelper().put(Properties.ALTERNATE_TEXT, alternateText);
	}

	public java.lang.String getFocusStyleClass() {
		return getFocusStyleClass(null);
	}

	/**
	 * See {@link #getFocusStyleClass() getFocusStyleClass()} for more details
	 */
	public java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOCUS_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "focusStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFocusStyleClassSetted() {
		return getStateHelper().get(Properties.FOCUS_STYLE_CLASS)!=null;
	}

	public void setFocusStyleClass(java.lang.String focusStyleClass) {
		getStateHelper().put(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public java.lang.String getSelectedState() {
		return getSelectedState(null);
	}

	/**
	 * See {@link #getSelectedState() getSelectedState()} for more details
	 */
	public java.lang.String getSelectedState(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedStateSetted() {
		return getStateHelper().get(Properties.SELECTED_STATE)!=null;
	}

	public void setSelectedState(java.lang.String selectedState) {
		if (org.rcfaces.core.internal.listener.CameliaPhaseListener.isApplyingRequestValues()) {
			setSubmittedExternalValue(selectedState);
		} else {
			setValue(selectedState);
		}
	}

}
