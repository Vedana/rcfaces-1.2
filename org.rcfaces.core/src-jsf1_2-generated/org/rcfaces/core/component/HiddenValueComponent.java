package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IValueLockedCapability;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.lang.Object;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.component.CameliaInputComponent;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;

/**
 * <p>The hiddenValue Component is a non-visual component. It is equivalent to and Input hidden type HTML tag.</p>
 * <p>It allows to access and store value on the client and on the server while keeping it concealed. The clientData Component can also be used.</p>
 * <p>The hiddenValue Component has the following capability :
 * <ul>
 * <li>Property changed Event handling</li>
 * </ul>
 * </p>
 */
public class HiddenValueComponent extends CameliaInputComponent implements 
	IClientDataCapability,
	IServerDataCapability,
	IPropertyChangeEventCapability,
	IImmediateCapability,
	IValueLockedCapability,
	IValidationEventCapability,
	IServerDataManager,
	IClientDataManager {

	public static final String COMPONENT_TYPE="org.rcfaces.core.hiddenValue";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"validationListener","immediate","propertyChangeListener","valueLocked"}));
	}

	public HiddenValueComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HiddenValueComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setClientData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public void setServerData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
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

	public Map getClientDataMap() {


		return getClientDataMap(null);
		
	}

	public int getClientDataCount() {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
		 
		 return dataMapAccessor.getDataCount();
		
	}

	public String getClientData(String name) {


		 return getClientData(name, null);
		
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

	public String[] listServerDataKeys() {


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

	public boolean isValueLocked() {
		return isValueLocked(null);
	}

	/**
	 * See {@link #isValueLocked() isValueLocked()} for more details
	 */
	public boolean isValueLocked(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VALUE_LOCKED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueLocked" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isValueLockedSetted() {
		return engine.isPropertySetted(Properties.VALUE_LOCKED);
	}

	public void setValueLocked(boolean valueLocked) {
		engine.setProperty(Properties.VALUE_LOCKED, valueLocked);
	}

	public final void addValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		addFacesListener(listener);
	}

	public final void removeValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValidationListeners() {
		return getFacesListeners(org.rcfaces.core.event.IValidationListener.class);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
