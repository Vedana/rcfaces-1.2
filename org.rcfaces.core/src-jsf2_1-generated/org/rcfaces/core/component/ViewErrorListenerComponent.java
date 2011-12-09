package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.lang.Object;
import java.util.Map;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.capability.IErrorEventCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IClientDataCapability;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.internal.tools.ComponentTools;

public class ViewErrorListenerComponent extends CameliaBaseComponent implements 
	IClientDataCapability,
	IServerDataCapability,
	IPropertyChangeEventCapability,
	IErrorEventCapability {

	private static final Log LOG = LogFactory.getLog(ViewErrorListenerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.viewErrorListener";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public ViewErrorListenerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ViewErrorListenerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getClientData(String name, FacesContext facesContext) {


		 IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public Object getServerData(String name, FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return null;
		}
		
		return dataMapAccessor.getData(name, facesContext);
		
	}

	public Map getClientDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "clientData", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getServerDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "serverData", false);
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


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public String[] listServerDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public int getClientDataCount() {


		 IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
		 
		 return dataMapAccessor.getDataCount();
		
	}

	public String[] listClientDataKeys() {


			return listClientDataKeys(null);
		
	}

	public String removeClientData(String name) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.removeData(name, null);
		
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
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

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public Map getServerDataMap() {


		return getServerDataMap(null);
		
	}

	public int getServerDataCount() {


		 IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return 0;
		 }
            
		return dataMapAccessor.getDataCount();
		
	}

	public Object getServerData(String name) {


		 IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return dataMapAccessor.getData(name, null);
		
	}

	public Object removeServerData(String name) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
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

	public final void addErrorListener(org.rcfaces.core.event.IErrorListener listener) {
		addFacesListener(listener);
	}

	public final void removeErrorListener(org.rcfaces.core.event.IErrorListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listErrorListeners() {
		return getFacesListeners(org.rcfaces.core.event.IErrorListener.class);
	}

}
