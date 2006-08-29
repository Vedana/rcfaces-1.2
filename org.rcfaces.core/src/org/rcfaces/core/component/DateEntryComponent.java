package org.rcfaces.core.component;

import java.util.Map;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAutoTabCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.AbstractCalendarComponent;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.DateConverter;
import org.rcfaces.core.internal.manager.IValidationParameters;

import java.util.Date;
import java.util.Collections;

public class DateEntryComponent extends AbstractCalendarComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateEntry";


	public DateEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateEntryComponent(String componentId) {
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

	public final void setDefaultDate(String date) {


			setDefaultDate((Date)DateConverter.SINGLETON.getAsObject(null, this, date));
		
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
			return map;
		}
		
		Map client=clientMapAccessor.getDataMap(facesContext);
		if (client==null || client.isEmpty()) {
			return map;
		}
		
		Map fmap=new HashMap(map);
		if (map.keySet().removeAll(client.keySet())==false) {
			return map;
		}
		
		if (fmap.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		if (Constants.LOCK_READ_ONLY_COLLECTIONS) {
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

	public final boolean isShowCalendarOnFocus() {
		return isShowCalendarOnFocus(null);
	}

	public final boolean isShowCalendarOnFocus(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_CALENDAR_ON_FOCUS, false, facesContext);
	}

	public final void setShowCalendarOnFocus(boolean showCalendarOnFocus) {
		engine.setProperty(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);
	}

	public final void setShowCalendarOnFocus(ValueBinding showCalendarOnFocus) {
		engine.setProperty(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);
	}

	public final boolean isShowCalendarOnFocusSetted() {
		return engine.isPropertySetted(Properties.SHOW_CALENDAR_ON_FOCUS);
	}

	public final boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	public final boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, false, facesContext);
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

	public final String getDateFormat() {
		return getDateFormat(null);
	}

	public final String getDateFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DATE_FORMAT, facesContext);
	}

	public final void setDateFormat(String dateFormat) {
		engine.setProperty(Properties.DATE_FORMAT, dateFormat);
	}

	public final void setDateFormat(ValueBinding dateFormat) {
		engine.setProperty(Properties.DATE_FORMAT, dateFormat);
	}

	public final boolean isDateFormatSetted() {
		return engine.isPropertySetted(Properties.DATE_FORMAT);
	}

	public final java.util.Date getDefaultDate() {
		return getDefaultDate(null);
	}

	public final java.util.Date getDefaultDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.DEFAULT_DATE, facesContext);
	}

	public final void setDefaultDate(java.util.Date defaultDate) {
		engine.setProperty(Properties.DEFAULT_DATE, defaultDate);
	}

	public final void setDefaultDate(ValueBinding defaultDate) {
		engine.setProperty(Properties.DEFAULT_DATE, defaultDate);
	}

	public final boolean isDefaultDateSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DATE);
	}

	public void release() {
		super.release();
	}
}
