package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import java.lang.Object;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.HashMap;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.Collections;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.model.Time;
import org.rcfaces.core.internal.tools.TimeTools;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.internal.converter.TimeConverter;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public class TimeEntryComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.timeEntry";


	public TimeEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TimeEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public final Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public final String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	public final String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public final String removeValidationParameter(String name) {


		FacesContext facesContext=getFacesContext();

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
 
 		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor!=null) {
			clientMapAccessor.removeData(name, facesContext);
		}
            
		return (String)dataMapAccessor.removeData(name, facesContext);
		
	}

	public final void setValidationParameter(String name, ValueBinding value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public final Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public final int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public final void setValue(Object value) {


				if (value instanceof String) {
					value=TimeConverter.SINGLETON.getAsObject(null, this, (String)value);
				}
				
				super.setValue(value);
			
	}

	public final Time getTime() {


				return (Time)getValue();
			
	}

	public final void setTime(Time time) {


				setValue(time);
			
	}

	public final void setTime(ValueBinding valueBinding) {


			setValueBinding("value", valueBinding);
			
	}

	public final void setTime(String time) {


			Time timeValue=(Time)TimeConverter.SINGLETON.getAsObject(null, this, time);
			setTime(timeValue);
		
	}

	public final void setMinTime(String time) {


			Time timeValue=(Time)TimeConverter.SINGLETON.getAsObject(null, this, time);
			setMinTime(timeValue);
		
	}

	public final void setDefaultTime(String time) {


			Time timeValue=(Time)TimeConverter.SINGLETON.getAsObject(null, this, time);
			setDefaultTime(timeValue);
		
	}

	public final void setMaxTime(String time) {


			Time timeValue=(Time)TimeConverter.SINGLETON.getAsObject(null, this, time);
			setMaxTime(timeValue);
		
	}

	public final String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public final int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public final Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public final Map getClientValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (map.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
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

	private final Object setValidationParameterData(String name, Object value, boolean client) {


		FacesContext facesContext=getFacesContext();
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", true);
		if (client) {
			// On retire la limitation au niveau client si besoin !
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
			if (clientMapAccessor!=null) {
				clientMapAccessor.removeData(name, facesContext);
			}
		} else {
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", true);
			clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
		}
            
		return dataMapAccessor.setData(name, value, facesContext);
		
	}

	public final boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
	}

	public final boolean isAutoTab() {
		return isAutoTab(null);
	}

	public final boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_TAB, false, facesContext);
	}

	public final void setAutoTab(boolean autoTab) {
		engine.setProperty(Properties.AUTO_TAB, autoTab);
	}

	public final void setAutoTab(ValueBinding autoTab) {
		engine.setProperty(Properties.AUTO_TAB, autoTab);
	}

	public final void addValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removeValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValueChangeListeners() {
		return getFacesListeners(javax.faces.event.ValueChangeListener.class);
	}

	public final java.lang.String getFocusStyleClass() {
		return getFocusStyleClass(null);
	}

	public final java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS, facesContext);
	}

	public final void setFocusStyleClass(java.lang.String focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public final void setFocusStyleClass(ValueBinding focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
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

	public final boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	public final boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, true, facesContext);
	}

	public final void setAutoCompletion(boolean autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final void setAutoCompletion(ValueBinding autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final boolean isAutoCompletionSetted() {
		return engine.isPropertySetted(Properties.AUTO_COMPLETION);
	}

	public final Time getMinTime() {
		return getMinTime(null);
	}

	public final Time getMinTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MIN_TIME, facesContext);
	}

	public final void setMinTime(Time minTime) {
		engine.setProperty(Properties.MIN_TIME, minTime);
	}

	public final void setMinTime(ValueBinding minTime) {
		engine.setProperty(Properties.MIN_TIME, minTime);
	}

	public final boolean isMinTimeSetted() {
		return engine.isPropertySetted(Properties.MIN_TIME);
	}

	public final Time getDefaultTime() {
		return getDefaultTime(null);
	}

	public final Time getDefaultTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.DEFAULT_TIME, facesContext);
	}

	public final void setDefaultTime(Time defaultTime) {
		engine.setProperty(Properties.DEFAULT_TIME, defaultTime);
	}

	public final void setDefaultTime(ValueBinding defaultTime) {
		engine.setProperty(Properties.DEFAULT_TIME, defaultTime);
	}

	public final boolean isDefaultTimeSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_TIME);
	}

	public final Time getMaxTime() {
		return getMaxTime(null);
	}

	public final Time getMaxTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MAX_TIME, facesContext);
	}

	public final void setMaxTime(Time maxTime) {
		engine.setProperty(Properties.MAX_TIME, maxTime);
	}

	public final void setMaxTime(ValueBinding maxTime) {
		engine.setProperty(Properties.MAX_TIME, maxTime);
	}

	public final boolean isMaxTimeSetted() {
		return engine.isPropertySetted(Properties.MAX_TIME);
	}

	public final String getTimeFormat() {
		return getTimeFormat(null);
	}

	public final String getTimeFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TIME_FORMAT, facesContext);
	}

	public final void setTimeFormat(String timeFormat) {
		engine.setProperty(Properties.TIME_FORMAT, timeFormat);
	}

	public final void setTimeFormat(ValueBinding timeFormat) {
		engine.setProperty(Properties.TIME_FORMAT, timeFormat);
	}

	public final boolean isTimeFormatSetted() {
		return engine.isPropertySetted(Properties.TIME_FORMAT);
	}

	public void release() {
		super.release();
	}
}
