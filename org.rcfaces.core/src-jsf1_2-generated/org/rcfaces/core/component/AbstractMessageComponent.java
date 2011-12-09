package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.rcfaces.core.component.capability.IUserEventCapability;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.component.capability.IForCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.CameliaMessageComponent;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.capability.IAnchoredPositionSettings;
import org.rcfaces.core.component.capability.IPositionCapability;
import java.lang.Object;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.rcfaces.core.component.capability.IPartialRenderingCapability;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import org.rcfaces.core.component.capability.IErrorEventCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ISizeCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IHelpCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.component.capability.IAnchoredPositionCapability;
import org.rcfaces.core.component.capability.IMarginCapability;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractMessageComponent extends CameliaMessageComponent implements 
	IMarginCapability,
	IUserEventCapability,
	IPositionCapability,
	IHelpCapability,
	IClientDataCapability,
	IPartialRenderingCapability,
	IStyleClassCapability,
	ILookAndFeelCapability,
	ISizeCapability,
	IForegroundBackgroundColorCapability,
	IVisibilityCapability,
	IErrorEventCapability,
	IWAIRoleCapability,
	IMouseEventCapability,
	IAnchoredPositionCapability,
	IUnlockedClientAttributesCapability,
	IInitEventCapability,
	IPropertyChangeEventCapability,
	IHiddenModeCapability,
	IServerDataCapability,
	IClientDataManager,
	IForCapability,
	IServerDataManager,
	IAnchoredPositionSettings {

	private static final Log LOG = LogFactory.getLog(AbstractMessageComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaMessageComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"bottomPosition","showSummary","visible","for","marginLeft","backgroundColor","errorListener","leftPosition","propertyChangeListener","helpURL","ariaLevel","topPosition","height","hiddenMode","mouseOverListener","foregroundColor","waiRole","mouseOutListener","lookId","rightPosition","helpMessage","userEventListener","marginTop","styleClass","marginRight","width","partialRendering","showDetail","ariaLabel","initListener","unlockedClientAttributeNames","marginBottom","toolTipText","y","margins","x"}));
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

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public Map getClientDataMap(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "clientData", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setServerData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public String[] listClientDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public void setClientData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
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

	public void setMargins(String margins) {


				MarginTools.setMargins(this, margins);
			
	}

	public String[] listServerDataKeys(FacesContext facesContext) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", false);
		if (dataMapAccessor==null) {
			return ComponentTools.STRING_EMPTY_ARRAY;
		}
		
		return dataMapAccessor.listDataKeys(facesContext);
		
	}

	public void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	public Boolean getVisibleState(FacesContext facesContext) {


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public java.lang.String getMarginBottom() {
		return getMarginBottom(null);
	}

	/**
	 * See {@link #getMarginBottom() getMarginBottom()} for more details
	 */
	public java.lang.String getMarginBottom(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_BOTTOM, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginBottom" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginBottomSetted() {
		return engine.isPropertySetted(Properties.MARGIN_BOTTOM);
	}

	public void setMarginBottom(java.lang.String marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	/**
	 * See {@link #getMarginLeft() getMarginLeft()} for more details
	 */
	public java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_LEFT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginLeft" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginLeftSetted() {
		return engine.isPropertySetted(Properties.MARGIN_LEFT);
	}

	public void setMarginLeft(java.lang.String marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	/**
	 * See {@link #getMarginRight() getMarginRight()} for more details
	 */
	public java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_RIGHT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginRight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginRightSetted() {
		return engine.isPropertySetted(Properties.MARGIN_RIGHT);
	}

	public void setMarginRight(java.lang.String marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	/**
	 * See {@link #getMarginTop() getMarginTop()} for more details
	 */
	public java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_TOP, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginTop" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginTopSetted() {
		return engine.isPropertySetted(Properties.MARGIN_TOP);
	}

	public void setMarginTop(java.lang.String marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
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

	public java.lang.String getX() {
		return getX(null);
	}

	/**
	 * See {@link #getX() getX()} for more details
	 */
	public java.lang.String getX(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.X, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "x" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isXSetted() {
		return engine.isPropertySetted(Properties.X);
	}

	public void setX(java.lang.String x) {
		engine.setProperty(Properties.X, x);
	}

	public java.lang.String getY() {
		return getY(null);
	}

	/**
	 * See {@link #getY() getY()} for more details
	 */
	public java.lang.String getY(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.Y, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "y" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isYSetted() {
		return engine.isPropertySetted(Properties.Y);
	}

	public void setY(java.lang.String y) {
		engine.setProperty(Properties.Y, y);
	}

	public java.lang.String getHelpMessage() {
		return getHelpMessage(null);
	}

	/**
	 * See {@link #getHelpMessage() getHelpMessage()} for more details
	 */
	public java.lang.String getHelpMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HELP_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "helpMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHelpMessageSetted() {
		return engine.isPropertySetted(Properties.HELP_MESSAGE);
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		engine.setProperty(Properties.HELP_MESSAGE, helpMessage);
	}

	public java.lang.String getHelpURL() {
		return getHelpURL(null);
	}

	/**
	 * See {@link #getHelpURL() getHelpURL()} for more details
	 */
	public java.lang.String getHelpURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HELP_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "helpURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHelpURLSetted() {
		return engine.isPropertySetted(Properties.HELP_URL);
	}

	public void setHelpURL(java.lang.String helpURL) {
		engine.setProperty(Properties.HELP_URL, helpURL);
	}

	public java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_TEXT);
	}

	public void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
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

	public String getClientData(String name) {


		 return getClientData(name, null);
		
	}

	public Map getClientDataMap() {


		return getClientDataMap(null);
		
	}

	public boolean isPartialRendering() {
		return isPartialRendering(null);
	}

	/**
	 * See {@link #isPartialRendering() isPartialRendering()} for more details
	 */
	public boolean isPartialRendering(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PARTIAL_RENDERING, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "partialRendering" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPartialRenderingSetted() {
		return engine.isPropertySetted(Properties.PARTIAL_RENDERING);
	}

	public void setPartialRendering(boolean partialRendering) {
		engine.setProperty(Properties.PARTIAL_RENDERING, partialRendering);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getLookId() {
		return getLookId(null);
	}

	/**
	 * See {@link #getLookId() getLookId()} for more details
	 */
	public java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LOOK_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "lookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLookIdSetted() {
		return engine.isPropertySetted(Properties.LOOK_ID);
	}

	public void setLookId(java.lang.String lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return engine.isPropertySetted(Properties.WIDTH);
	}

	public void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEIGHT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "height" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeightSetted() {
		return engine.isPropertySetted(Properties.HEIGHT);
	}

	public void setHeight(java.lang.String height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_COLOR);
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return engine.isPropertySetted(Properties.FOREGROUND_COLOR);
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return engine.isPropertySetted(Properties.VISIBLE);
	}

	public void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public Boolean getVisibleState() {


			return getVisibleState(null);
		
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

	public java.lang.String getAriaLabel() {
		return getAriaLabel(null);
	}

	/**
	 * See {@link #getAriaLabel() getAriaLabel()} for more details
	 */
	public java.lang.String getAriaLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ARIA_LABEL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLabelSetted() {
		return engine.isPropertySetted(Properties.ARIA_LABEL);
	}

	public void setAriaLabel(java.lang.String ariaLabel) {
		engine.setProperty(Properties.ARIA_LABEL, ariaLabel);
	}

	public int getAriaLevel() {
		return getAriaLevel(null);
	}

	/**
	 * See {@link #getAriaLevel() getAriaLevel()} for more details
	 */
	public int getAriaLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ARIA_LEVEL,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLevelSetted() {
		return engine.isPropertySetted(Properties.ARIA_LEVEL);
	}

	public void setAriaLevel(int ariaLevel) {
		engine.setProperty(Properties.ARIA_LEVEL, ariaLevel);
	}

	public java.lang.String getWaiRole() {
		return getWaiRole(null);
	}

	/**
	 * See {@link #getWaiRole() getWaiRole()} for more details
	 */
	public java.lang.String getWaiRole(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WAI_ROLE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "waiRole" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWaiRoleSetted() {
		return engine.isPropertySetted(Properties.WAI_ROLE);
	}

	public void setWaiRole(java.lang.String waiRole) {
		engine.setProperty(Properties.WAI_ROLE, waiRole);
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

	public int getBottomPosition() {
		return getBottomPosition(null);
	}

	/**
	 * See {@link #getBottomPosition() getBottomPosition()} for more details
	 */
	public int getBottomPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.BOTTOM_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "bottomPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBottomPositionSetted() {
		return engine.isPropertySetted(Properties.BOTTOM_POSITION);
	}

	public void setBottomPosition(int bottomPosition) {
		engine.setProperty(Properties.BOTTOM_POSITION, bottomPosition);
	}

	public int getLeftPosition() {
		return getLeftPosition(null);
	}

	/**
	 * See {@link #getLeftPosition() getLeftPosition()} for more details
	 */
	public int getLeftPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.LEFT_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "leftPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLeftPositionSetted() {
		return engine.isPropertySetted(Properties.LEFT_POSITION);
	}

	public void setLeftPosition(int leftPosition) {
		engine.setProperty(Properties.LEFT_POSITION, leftPosition);
	}

	public int getRightPosition() {
		return getRightPosition(null);
	}

	/**
	 * See {@link #getRightPosition() getRightPosition()} for more details
	 */
	public int getRightPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.RIGHT_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "rightPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRightPositionSetted() {
		return engine.isPropertySetted(Properties.RIGHT_POSITION);
	}

	public void setRightPosition(int rightPosition) {
		engine.setProperty(Properties.RIGHT_POSITION, rightPosition);
	}

	public int getTopPosition() {
		return getTopPosition(null);
	}

	/**
	 * See {@link #getTopPosition() getTopPosition()} for more details
	 */
	public int getTopPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TOP_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "topPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTopPositionSetted() {
		return engine.isPropertySetted(Properties.TOP_POSITION);
	}

	public void setTopPosition(int topPosition) {
		engine.setProperty(Properties.TOP_POSITION, topPosition);
	}

	public java.lang.String getUnlockedClientAttributeNames() {
		return getUnlockedClientAttributeNames(null);
	}

	/**
	 * See {@link #getUnlockedClientAttributeNames() getUnlockedClientAttributeNames()} for more details
	 */
	public java.lang.String getUnlockedClientAttributeNames(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "unlockedClientAttributeNames" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUnlockedClientAttributeNamesSetted() {
		return engine.isPropertySetted(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES);
	}

	public void setUnlockedClientAttributeNames(java.lang.String unlockedClientAttributeNames) {
		engine.setProperty(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);
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

	public final void addPropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removePropertyChangeListener(org.rcfaces.core.event.IPropertyChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listPropertyChangeListeners() {
		return getFacesListeners(org.rcfaces.core.event.IPropertyChangeListener.class);
	}

	public int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,IHiddenModeCapability.DEFAULT_HIDDEN_MODE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return engine.isPropertySetted(Properties.HIDDEN_MODE);
	}

	public void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public String[] listServerDataKeys() {


			return listServerDataKeys(null);
		
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
