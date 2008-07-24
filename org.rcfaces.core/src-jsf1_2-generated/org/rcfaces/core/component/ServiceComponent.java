package org.rcfaces.core.component;

import java.util.TimeZone;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import java.util.Collections;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.component.capability.IComponentTimeZoneCapability;
import org.rcfaces.core.component.capability.IServiceEventCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.converter.LocaleConverter;
import java.util.Locale;
import java.util.Set;
import org.rcfaces.core.internal.tools.ComponentTools;
import java.lang.Object;
import java.lang.String;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.IServerDataManager;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Arrays;

/**
 * <p>The service Component is a non-visual component.</p>
 * <p>It allows to (synchronously or asynchronously) call AJAX services from the client.</p>
 * <p>The service Component has the following capability :
 * <ul>
 * <li>Property changed Event handling</li>
 * </ul>
 * </p>
 */
public class ServiceComponent extends CameliaBaseComponent implements 
	IClientDataCapability,
	IServerDataCapability,
	IPropertyChangeEventCapability,
	IServiceEventCapability,
	IFilterCapability,
	IComponentLocaleCapability,
	IComponentTimeZoneCapability,
	IClientDataManager,
	IServerDataManager {

	public static final String COMPONENT_TYPE="org.rcfaces.core.service";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"enableViewState","filterProperties","serviceId","componentTimeZone","componentLocale","serviceEventListener","propertyChangeListener"}));
	}

	public ServiceComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ServiceComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setClientData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setServerData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setComponentTimeZone(String timeZone) {


		setComponentTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public String getClientData(String name, FacesContext facesContext) {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public Object getServerData(String name, FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return null;
		}
		
		return dataMapAccessor.getData(name, facesContext);
		
	}

	public Map getClientDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "clientData", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getServerDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "serverData", false);
 		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
			if (map.isEmpty()) {
				return Collections.EMPTY_MAP;
			}
			map=Collections.unmodifiableMap(map);
		}
		return map;
		
	}

	public String[] listClientDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public String[] listServerDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public int getClientDataCount() {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
		 
		 return dataMapAccessor.getDataCount();
		
	}

	public String[] listClientDataKeys() {


			return listClientDataKeys(null);
		
	}

	public String removeClientData(String name) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.removeData(name, null);
		
	}

	public String getClientData(String name) {


		 return getClientData(name, null);
		
	}

	public Map getClientDataMap() {


		return getClientDataMap(null);
		
	}

	public String[] listServerDataKeys() {


			return listServerDataKeys(null);
		
	}

	public Map getServerDataMap() {


		return getServerDataMap(null);
		
	}

	public int getServerDataCount() {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
            
		return dataMapAccessor.getDataCount();
		
	}

	public Object getServerData(String name) {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return dataMapAccessor.getData(name, null);
		
	}

	public Object removeServerData(String name) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
		 	return null;
		}
            
		return dataMapAccessor.removeData(name, null);
		
	}

	public final void addPropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removePropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listPropertyChangeListeners() {
		return getFacesListeners(org.rcfaces.core.event.IPropertyChangeListener.class);
	}

	public final void addServiceEventListener(org.rcfaces.core.event.IServiceEventListener listener) {
		addFacesListener(listener);
	}

	public final void removeServiceEventListener(org.rcfaces.core.event.IServiceEventListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listServiceEventListeners() {
		return getFacesListeners(org.rcfaces.core.event.IServiceEventListener.class);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public java.util.Locale getComponentLocale() {
		return getComponentLocale(null);
	}

	/**
	 * See {@link #getComponentLocale() getComponentLocale()} for more details
	 */
	public java.util.Locale getComponentLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.COMPONENT_LOCALE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentLocaleSetted() {
		return engine.isPropertySetted(Properties.COMPONENT_LOCALE);
	}

	public void setComponentLocale(java.util.Locale componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	public java.util.TimeZone getComponentTimeZone() {
		return getComponentTimeZone(null);
	}

	/**
	 * See {@link #getComponentTimeZone() getComponentTimeZone()} for more details
	 */
	public java.util.TimeZone getComponentTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.COMPONENT_TIME_ZONE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentTimeZoneSetted() {
		return engine.isPropertySetted(Properties.COMPONENT_TIME_ZONE);
	}

	public void setComponentTimeZone(java.util.TimeZone componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
	}

	/**
	 * Returns the service id.
	 * @return service id
	 */
	public String getServiceId() {
		return getServiceId(null);
	}

	/**
	 * Returns the service id.
	 * @return service id
	 */
	public String getServiceId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SERVICE_ID, facesContext);
	}

	/**
	 * Sets the service id.
	 * @param serviceId service id
	 */
	public void setServiceId(String serviceId) {
		engine.setProperty(Properties.SERVICE_ID, serviceId);
	}

	/**
	 * Sets the service id.
	 * @param serviceId service id
	 */
	/**
	 * Returns <code>true</code> if the attribute "serviceId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isServiceIdSetted() {
		return engine.isPropertySetted(Properties.SERVICE_ID);
	}

	public boolean isEnableViewState() {
		return isEnableViewState(null);
	}

	public boolean isEnableViewState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ENABLE_VIEW_STATE, true, facesContext);
	}

	public void setEnableViewState(boolean enableViewState) {
		engine.setProperty(Properties.ENABLE_VIEW_STATE, enableViewState);
	}

	/**
	 * Returns <code>true</code> if the attribute "enableViewState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isEnableViewStateSetted() {
		return engine.isPropertySetted(Properties.ENABLE_VIEW_STATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
