package org.rcfaces.renderkit.svg.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.lang.Object;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import java.util.HashSet;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;

public abstract class NodeComponent extends CameliaItemComponent implements 
	IAlternateTextCapability,
	IAccessKeyCapability,
	ITabIndexCapability,
	IServerDataCapability,
	IClientDataCapability,
	IServerDataManager,
	IClientDataManager {

	private static final Log LOG = LogFactory.getLog(NodeComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"targetId","alternateText","itemDescription","itemLabel","accessKey","itemDisabled","rendered","itemValue","tabIndex","selectable"}));
	}


	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setTargetId(String src) {


			setItemValue(src);
			
	}

	public String getTargetId() {


			return (String)getItemValue();
			
	}

	public String getClientData(String name, FacesContext facesContext) {


		 IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		 if (dataMapAccessor==null) {
		 	return null;
		 }
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public void setClientData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public Object getServerData(String name, FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return null;
		}
		
		return dataMapAccessor.getData(name, facesContext);
		
	}

	public void setServerData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
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

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return engine.getIntegerProperty(Properties.TAB_INDEX, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "tabIndex" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTabIndexSetted() {
		return engine.isPropertySetted(Properties.TAB_INDEX);
	}

	public void setTabIndex(java.lang.Integer tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
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

	public boolean isRendered() {
		return isRendered(null);
	}

	public boolean isRendered(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDERED, true, facesContext);
	}

	public void setRendered(boolean rendered) {
		engine.setProperty(Properties.RENDERED, rendered);
	}

	/**
	 * Returns <code>true</code> if the attribute "rendered" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRenderedSetted() {
		return engine.isPropertySetted(Properties.RENDERED);
	}

	public boolean isSelectable() {
		return isSelectable(null);
	}

	public boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTABLE, false, facesContext);
	}

	public void setSelectable(boolean selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectableSetted() {
		return engine.isPropertySetted(Properties.SELECTABLE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueBinding(String name, ValueBinding binding) {
		if (Properties.TARGET_ID.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueBinding(name, binding);
	}
}
