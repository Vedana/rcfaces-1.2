package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.rcfaces.core.component.capability.IUserEventCapability;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.manager.IClientDataManager;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IValueLockedCapability;
import java.util.Collection;
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
import org.rcfaces.core.internal.component.CameliaOutputComponent;
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
public abstract class AbstractOutputComponent extends CameliaOutputComponent implements 
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
	IValueLockedCapability,
	IClientDataManager,
	IServerDataManager,
	IAnchoredPositionSettings {

	private static final Log LOG = LogFactory.getLog(AbstractOutputComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaOutputComponent.BEHAVIOR_EVENT_NAMES;


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


			if (isPropertySetted(Properties.VISIBLE)==false) {
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
		return (String)getStateHelper().eval(Properties.MARGIN_BOTTOM);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginBottom" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginBottomSetted() {
		return getStateHelper().get(Properties.MARGIN_BOTTOM)!=null;
	}

	public void setMarginBottom(java.lang.String marginBottom) {
		getStateHelper().put(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	/**
	 * See {@link #getMarginLeft() getMarginLeft()} for more details
	 */
	public java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_LEFT);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginLeft" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginLeftSetted() {
		return getStateHelper().get(Properties.MARGIN_LEFT)!=null;
	}

	public void setMarginLeft(java.lang.String marginLeft) {
		getStateHelper().put(Properties.MARGIN_LEFT, marginLeft);
	}

	public java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	/**
	 * See {@link #getMarginRight() getMarginRight()} for more details
	 */
	public java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_RIGHT);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginRight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginRightSetted() {
		return getStateHelper().get(Properties.MARGIN_RIGHT)!=null;
	}

	public void setMarginRight(java.lang.String marginRight) {
		getStateHelper().put(Properties.MARGIN_RIGHT, marginRight);
	}

	public java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	/**
	 * See {@link #getMarginTop() getMarginTop()} for more details
	 */
	public java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MARGIN_TOP);
	}

	/**
	 * Returns <code>true</code> if the attribute "marginTop" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMarginTopSetted() {
		return getStateHelper().get(Properties.MARGIN_TOP)!=null;
	}

	public void setMarginTop(java.lang.String marginTop) {
		getStateHelper().put(Properties.MARGIN_TOP, marginTop);
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
		return (String)getStateHelper().eval(Properties.X);
	}

	/**
	 * Returns <code>true</code> if the attribute "x" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isXSetted() {
		return getStateHelper().get(Properties.X)!=null;
	}

	public void setX(java.lang.String x) {
		getStateHelper().put(Properties.X, x);
	}

	public java.lang.String getY() {
		return getY(null);
	}

	/**
	 * See {@link #getY() getY()} for more details
	 */
	public java.lang.String getY(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.Y);
	}

	/**
	 * Returns <code>true</code> if the attribute "y" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isYSetted() {
		return getStateHelper().get(Properties.Y)!=null;
	}

	public void setY(java.lang.String y) {
		getStateHelper().put(Properties.Y, y);
	}

	public java.lang.String getHelpMessage() {
		return getHelpMessage(null);
	}

	/**
	 * See {@link #getHelpMessage() getHelpMessage()} for more details
	 */
	public java.lang.String getHelpMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HELP_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "helpMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHelpMessageSetted() {
		return getStateHelper().get(Properties.HELP_MESSAGE)!=null;
	}

	public void setHelpMessage(java.lang.String helpMessage) {
		getStateHelper().put(Properties.HELP_MESSAGE, helpMessage);
	}

	public java.lang.String getHelpURL() {
		return getHelpURL(null);
	}

	/**
	 * See {@link #getHelpURL() getHelpURL()} for more details
	 */
	public java.lang.String getHelpURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HELP_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "helpURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHelpURLSetted() {
		return getStateHelper().get(Properties.HELP_URL)!=null;
	}

	public void setHelpURL(java.lang.String helpURL) {
		getStateHelper().put(Properties.HELP_URL, helpURL);
	}

	public java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TOOL_TIP_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return getStateHelper().get(Properties.TOOL_TIP_TEXT)!=null;
	}

	public void setToolTipText(java.lang.String toolTipText) {
		getStateHelper().put(Properties.TOOL_TIP_TEXT, toolTipText);
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
		return (Boolean)getStateHelper().eval(Properties.PARTIAL_RENDERING, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "partialRendering" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPartialRenderingSetted() {
		return getStateHelper().get(Properties.PARTIAL_RENDERING)!=null;
	}

	public void setPartialRendering(boolean partialRendering) {
		getStateHelper().put(Properties.PARTIAL_RENDERING, partialRendering);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getLookId() {
		return getLookId(null);
	}

	/**
	 * See {@link #getLookId() getLookId()} for more details
	 */
	public java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.LOOK_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "lookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLookIdSetted() {
		return getStateHelper().get(Properties.LOOK_ID)!=null;
	}

	public void setLookId(java.lang.String lookId) {
		getStateHelper().put(Properties.LOOK_ID, lookId);
	}

	public java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WIDTH);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return getStateHelper().get(Properties.WIDTH)!=null;
	}

	public void setWidth(java.lang.String width) {
		getStateHelper().put(Properties.WIDTH, width);
	}

	public java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HEIGHT);
	}

	/**
	 * Returns <code>true</code> if the attribute "height" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeightSetted() {
		return getStateHelper().get(Properties.HEIGHT)!=null;
	}

	public void setHeight(java.lang.String height) {
		getStateHelper().put(Properties.HEIGHT, height);
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return getStateHelper().get(Properties.BACKGROUND_COLOR)!=null;
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		getStateHelper().put(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOREGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return getStateHelper().get(Properties.FOREGROUND_COLOR)!=null;
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		getStateHelper().put(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.VISIBLE, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return getStateHelper().get(Properties.VISIBLE)!=null;
	}

	public void setVisible(boolean visible) {
		getStateHelper().put(Properties.VISIBLE, visible);
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
		return (String)getStateHelper().eval(Properties.ARIA_LABEL);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLabelSetted() {
		return getStateHelper().get(Properties.ARIA_LABEL)!=null;
	}

	public void setAriaLabel(java.lang.String ariaLabel) {
		getStateHelper().put(Properties.ARIA_LABEL, ariaLabel);
	}

	public int getAriaLevel() {
		return getAriaLevel(null);
	}

	/**
	 * See {@link #getAriaLevel() getAriaLevel()} for more details
	 */
	public int getAriaLevel(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ARIA_LEVEL, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "ariaLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAriaLevelSetted() {
		return getStateHelper().get(Properties.ARIA_LEVEL)!=null;
	}

	public void setAriaLevel(int ariaLevel) {
		getStateHelper().put(Properties.ARIA_LEVEL, ariaLevel);
	}

	public java.lang.String getWaiRole() {
		return getWaiRole(null);
	}

	/**
	 * See {@link #getWaiRole() getWaiRole()} for more details
	 */
	public java.lang.String getWaiRole(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WAI_ROLE);
	}

	/**
	 * Returns <code>true</code> if the attribute "waiRole" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWaiRoleSetted() {
		return getStateHelper().get(Properties.WAI_ROLE)!=null;
	}

	public void setWaiRole(java.lang.String waiRole) {
		getStateHelper().put(Properties.WAI_ROLE, waiRole);
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
		return (Integer)getStateHelper().eval(Properties.BOTTOM_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "bottomPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBottomPositionSetted() {
		return getStateHelper().get(Properties.BOTTOM_POSITION)!=null;
	}

	public void setBottomPosition(int bottomPosition) {
		getStateHelper().put(Properties.BOTTOM_POSITION, bottomPosition);
	}

	public int getLeftPosition() {
		return getLeftPosition(null);
	}

	/**
	 * See {@link #getLeftPosition() getLeftPosition()} for more details
	 */
	public int getLeftPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.LEFT_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "leftPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLeftPositionSetted() {
		return getStateHelper().get(Properties.LEFT_POSITION)!=null;
	}

	public void setLeftPosition(int leftPosition) {
		getStateHelper().put(Properties.LEFT_POSITION, leftPosition);
	}

	public int getRightPosition() {
		return getRightPosition(null);
	}

	/**
	 * See {@link #getRightPosition() getRightPosition()} for more details
	 */
	public int getRightPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.RIGHT_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "rightPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRightPositionSetted() {
		return getStateHelper().get(Properties.RIGHT_POSITION)!=null;
	}

	public void setRightPosition(int rightPosition) {
		getStateHelper().put(Properties.RIGHT_POSITION, rightPosition);
	}

	public int getTopPosition() {
		return getTopPosition(null);
	}

	/**
	 * See {@link #getTopPosition() getTopPosition()} for more details
	 */
	public int getTopPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TOP_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "topPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTopPositionSetted() {
		return getStateHelper().get(Properties.TOP_POSITION)!=null;
	}

	public void setTopPosition(int topPosition) {
		getStateHelper().put(Properties.TOP_POSITION, topPosition);
	}

	public java.lang.String getUnlockedClientAttributeNames() {
		return getUnlockedClientAttributeNames(null);
	}

	/**
	 * See {@link #getUnlockedClientAttributeNames() getUnlockedClientAttributeNames()} for more details
	 */
	public java.lang.String getUnlockedClientAttributeNames(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES);
	}

	/**
	 * Returns <code>true</code> if the attribute "unlockedClientAttributeNames" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUnlockedClientAttributeNamesSetted() {
		return getStateHelper().get(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES)!=null;
	}

	public void setUnlockedClientAttributeNames(java.lang.String unlockedClientAttributeNames) {
		getStateHelper().put(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);
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
		return (Integer)getStateHelper().eval(Properties.HIDDEN_MODE, IHiddenModeCapability.DEFAULT_HIDDEN_MODE);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return getStateHelper().get(Properties.HIDDEN_MODE)!=null;
	}

	public void setHiddenMode(int hiddenMode) {
		getStateHelper().put(Properties.HIDDEN_MODE, hiddenMode);
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

	public boolean isValueLocked() {
		return isValueLocked(null);
	}

	/**
	 * See {@link #isValueLocked() isValueLocked()} for more details
	 */
	public boolean isValueLocked(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.VALUE_LOCKED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueLocked" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isValueLockedSetted() {
		return getStateHelper().get(Properties.VALUE_LOCKED)!=null;
	}

	public void setValueLocked(boolean valueLocked) {
		getStateHelper().put(Properties.VALUE_LOCKED, valueLocked);
	}

}
