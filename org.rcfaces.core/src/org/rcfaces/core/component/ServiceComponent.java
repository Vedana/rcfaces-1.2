package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.lang.Object;
import org.rcfaces.core.component.capability.IServiceEventCapability;
import javax.faces.el.ValueBinding;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;

public class ServiceComponent extends CameliaBaseComponent implements 
	IClientDataCapability,
	IServerDataCapability,
	IPropertyChangeEventCapability,
	IServiceEventCapability,
	IFilterCapability,
	IServerDataManager,
	IClientDataManager {

	public static final String COMPONENT_TYPE="org.rcfaces.core.service";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"serviceId","filterProperties","enableViewState","propertyChangeListener","serviceEventListener"}));
	}

	public ServiceComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ServiceComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setClientData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public final void setServerData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public final String getClientData(String name, FacesContext facesContext) {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public final Object getServerData(String name, FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return null;
		}
		
		return dataMapAccessor.getData(name, facesContext);
		
	}

	public final Map getClientDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "clientData", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public final Map getServerDataMap(FacesContext facesContext) {


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

	public final String[] listClientDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public final String[] listServerDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public final Map getClientDataMap() {


		return getClientDataMap(null);
		
	}

	public final int getClientDataCount() {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
		 
		 return dataMapAccessor.getDataCount();
		
	}

	public final String getClientData(String name) {


		 return getClientData(name, null);
		
	}

	public final String[] listClientDataKeys() {


			return listClientDataKeys(null);
		
	}

	public final String removeClientData(String name) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.removeData(name, null);
		
	}

	public final String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public final Object getServerData(String name) {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return dataMapAccessor.getData(name, null);
		
	}

	public final Object removeServerData(String name) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
		 	return null;
		}
            
		return dataMapAccessor.removeData(name, null);
		
	}

	public final Map getServerDataMap() {


		return getServerDataMap(null);
		
	}

	public final int getServerDataCount() {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
            
		return dataMapAccessor.getDataCount();
		
	}

	public final Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public final String[] listServerDataKeys() {


			return listServerDataKeys(null);
		
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

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	/**
	 * Returns the service id.
	 * @return service id
	 */
	public final String getServiceId() {
		return getServiceId(null);
	}

	/**
	 * Returns the service id.
	 * @return service id
	 */
	public final String getServiceId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SERVICE_ID, facesContext);
	}

	/**
	 * Sets the service id.
	 * @param serviceId service id
	 */
	public final void setServiceId(String serviceId) {
		engine.setProperty(Properties.SERVICE_ID, serviceId);
	}

	/**
	 * Sets the service id.
	 * @param serviceId service id
	 */
	public final void setServiceId(ValueBinding serviceId) {
		engine.setProperty(Properties.SERVICE_ID, serviceId);
	}

	/**
	 * Returns <code>true</code> if the attribute "serviceId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isServiceIdSetted() {
		return engine.isPropertySetted(Properties.SERVICE_ID);
	}

	public final boolean isEnableViewState() {
		return isEnableViewState(null);
	}

	public final boolean isEnableViewState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ENABLE_VIEW_STATE, true, facesContext);
	}

	public final void setEnableViewState(boolean enableViewState) {
		engine.setProperty(Properties.ENABLE_VIEW_STATE, enableViewState);
	}

	public final void setEnableViewState(ValueBinding enableViewState) {
		engine.setProperty(Properties.ENABLE_VIEW_STATE, enableViewState);
	}

	/**
	 * Returns <code>true</code> if the attribute "enableViewState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEnableViewStateSetted() {
		return engine.isPropertySetted(Properties.ENABLE_VIEW_STATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
