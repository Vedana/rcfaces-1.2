package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import java.lang.Object;
import org.rcfaces.core.component.capability.IHelpCapability;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Collections;
import java.util.Arrays;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import java.lang.String;
import javax.faces.context.FacesContext;
import java.util.Map;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.component.CameliaMessagesComponent;
import org.rcfaces.core.component.capability.IServerDataCapability;

public abstract class AbstractMessagesComponent extends CameliaMessagesComponent implements 
	ISizeCapability,
	IVisibilityCapability,
	IMouseEventCapability,
	IHelpCapability,
	IClientDataCapability,
	IPositionCapability,
	ILookAndFeelCapability,
	IMarginCapability,
	IForegroundBackgroundColorCapability,
	IStyleClassCapability,
	IUserEventCapability,
	IServerDataCapability,
	IPropertyChangeEventCapability,
	IInitEventCapability,
	IServerDataManager,
	IClientDataManager {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaMessagesComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"width","marginRight","hiddenMode","helpMessage","foregroundColor","styleClass","height","globalOnly","margins","initListener","propertyChangeListener","mouseOutListener","toolTipText","mouseOverListener","userEventListener","showDetail","marginBottom","helpURL","showSummary","y","visible","lookId","marginLeft","marginTop","backgroundColor","x"}));
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

	public final String[] listServerDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public final void setServerData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public final String[] listClientDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final Boolean getVisibleState(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.VISIBLE)==false) {
					return null;
				}
				
				return Boolean.valueOf(isVisible(facesContext));
			
	}

	public final void setClientData(String name, ValueBinding value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public final Map getClientDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "clientData", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public final void setMargins(String margins) {


				MarginTools.setMargins(this, margins);
			
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

	public final java.lang.String getHeight() {
		return getHeight(null);
	}

	public final java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEIGHT, facesContext);
	}

	public final void setHeight(java.lang.String height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public final void setHeight(ValueBinding height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public final java.lang.String getWidth() {
		return getWidth(null);
	}

	public final java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	public final void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final void setWidth(ValueBinding width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, false, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


				return getVisibleState(null);
			
	}

	public final void addMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOutListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOutListener.class);
	}

	public final void addMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOverListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOverListener.class);
	}

	public final java.lang.String getHelpMessage() {
		return getHelpMessage(null);
	}

	public final java.lang.String getHelpMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HELP_MESSAGE, facesContext);
	}

	public final void setHelpMessage(java.lang.String helpMessage) {
		engine.setProperty(Properties.HELP_MESSAGE, helpMessage);
	}

	public final void setHelpMessage(ValueBinding helpMessage) {
		engine.setProperty(Properties.HELP_MESSAGE, helpMessage);
	}

	public final java.lang.String getHelpURL() {
		return getHelpURL(null);
	}

	public final java.lang.String getHelpURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HELP_URL, facesContext);
	}

	public final void setHelpURL(java.lang.String helpURL) {
		engine.setProperty(Properties.HELP_URL, helpURL);
	}

	public final void setHelpURL(ValueBinding helpURL) {
		engine.setProperty(Properties.HELP_URL, helpURL);
	}

	public final java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	public final java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	public final void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final void setToolTipText(ValueBinding toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
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

	public final java.lang.String getX() {
		return getX(null);
	}

	public final java.lang.String getX(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.X, facesContext);
	}

	public final void setX(java.lang.String x) {
		engine.setProperty(Properties.X, x);
	}

	public final void setX(ValueBinding x) {
		engine.setProperty(Properties.X, x);
	}

	public final java.lang.String getY() {
		return getY(null);
	}

	public final java.lang.String getY(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.Y, facesContext);
	}

	public final void setY(java.lang.String y) {
		engine.setProperty(Properties.Y, y);
	}

	public final void setY(ValueBinding y) {
		engine.setProperty(Properties.Y, y);
	}

	public final java.lang.String getLookId() {
		return getLookId(null);
	}

	public final java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LOOK_ID, facesContext);
	}

	public final void setLookId(java.lang.String lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public final void setLookId(ValueBinding lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public final java.lang.String getMarginBottom() {
		return getMarginBottom(null);
	}

	public final java.lang.String getMarginBottom(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_BOTTOM, facesContext);
	}

	public final void setMarginBottom(java.lang.String marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public final void setMarginBottom(ValueBinding marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public final java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	public final java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_LEFT, facesContext);
	}

	public final void setMarginLeft(java.lang.String marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public final void setMarginLeft(ValueBinding marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public final java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	public final java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_RIGHT, facesContext);
	}

	public final void setMarginRight(java.lang.String marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public final void setMarginRight(ValueBinding marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public final java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	public final java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_TOP, facesContext);
	}

	public final void setMarginTop(java.lang.String marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	public final void setMarginTop(ValueBinding marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final void setForegroundColor(ValueBinding foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final void addUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		addFacesListener(listener);
	}

	public final void removeUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listUserEventListeners() {
		return getFacesListeners(org.rcfaces.core.event.IUserEventListener.class);
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

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
