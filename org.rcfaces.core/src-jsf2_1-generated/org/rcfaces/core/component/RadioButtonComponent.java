package org.rcfaces.core.component;

import org.rcfaces.core.component.RadioButtonComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HorizontalTextPositionConverter;
import java.util.Map;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.iterator.IRadioButtonIterator;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.RadioButtonTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.lang.Object;
import java.lang.String;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import java.util.Arrays;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * <p>The radioButton Component is based on the standard HTML tag &lt;INPUT TYPE="radio"&gt;. It can interoperate automatically with other radioButtons from the same group.</p>
 * <p>The radioButton Component has the following capabilities :
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
	ITextCapability,
	ITextDirectionCapability,
	IHorizontalTextPositionCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	IAlternateTextCapability,
	ISeverityStyleClassCapability,
	IFocusStyleClassCapability,
	ISelectedCapability,
	IRadioValueCapability,
	IRequiredCapability,
	IValidationParameters {

	private static final Log LOG = LogFactory.getLog(RadioButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.radioButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public RadioButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public RadioButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public void setValidationParameter(String name, ValueExpression value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public String removeValidationParameter(String name) {


		FacesContext facesContext=getFacesContext();

		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
 
 		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor!=null) {
			clientMapAccessor.removeData(name, facesContext);
		}
            
		return (String)dataMapAccessor.removeData(name, facesContext);
		
	}

	public String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	protected Converter getTextPositionConverter() {


				return HorizontalTextPositionConverter.SINGLETON;
			
	}

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
	}

	public RadioButtonComponent getSelectedFromSameGroup() {


			return RadioButtonTools.getSelectedRadioButtonFromSameGroup(this);
			
	}

	public IRadioButtonIterator listSameGroup() {


			return RadioButtonTools.listRadioButtonSameGroup(this);
			
	}

	public String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getClientValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (map.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map client=clientMapAccessor.getDataMap(facesContext);
		if (client==null || client.isEmpty()) {
		
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map fmap=new HashMap(map);
		if (map.keySet().removeAll(client.keySet())==false) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		if (fmap.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
			fmap=Collections.unmodifiableMap(fmap);
		}
		
		return fmap;
		
	}

	private Object setValidationParameterData(String name, Object value, boolean client) {


		FacesContext facesContext=getFacesContext();
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", true);
		if (client) {
			// On retire la limitation au niveau client si besoin !
			IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
			if (clientMapAccessor!=null) {
				clientMapAccessor.removeData(name, facesContext);
			}
		} else {
			IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", true);
			clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
		}
            
		return dataMapAccessor.setData(name, value, facesContext);
		
	}

	public boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
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

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ERROR_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return getStateHelper().get(Properties.ERROR_STYLE_CLASS)!=null;
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		getStateHelper().put(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FATAL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return getStateHelper().get(Properties.FATAL_STYLE_CLASS)!=null;
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		getStateHelper().put(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INFO_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return getStateHelper().get(Properties.INFO_STYLE_CLASS)!=null;
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		getStateHelper().put(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WARN_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return getStateHelper().get(Properties.WARN_STYLE_CLASS)!=null;
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		getStateHelper().put(Properties.WARN_STYLE_CLASS, warnStyleClass);
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

	public boolean isSelected() {
		return isSelected(null);
	}

	/**
	 * See {@link #isSelected() isSelected()} for more details
	 */
	public boolean isSelected(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SELECTED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "selected" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedSetted() {
		return getStateHelper().get(Properties.SELECTED)!=null;
	}

	public void setSelected(boolean selected) {
		getStateHelper().put(Properties.SELECTED, selected);
	}

	public java.lang.Object getRadioValue() {
		return getRadioValue(null);
	}

	/**
	 * See {@link #getRadioValue() getRadioValue()} for more details
	 */
	public java.lang.Object getRadioValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.RADIO_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "radioValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRadioValueSetted() {
		return getStateHelper().get(Properties.RADIO_VALUE)!=null;
	}

	public void setRadioValue(java.lang.Object radioValue) {
		getStateHelper().put(Properties.RADIO_VALUE, radioValue);
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

}
