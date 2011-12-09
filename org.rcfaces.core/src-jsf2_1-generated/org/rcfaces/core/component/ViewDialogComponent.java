package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.IDialogPriorityCapability;
import org.rcfaces.core.component.capability.IClosableCapability;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.internal.component.CameliaOutputComponent;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ISizeCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.ITextCapability;

public class ViewDialogComponent extends CameliaOutputComponent implements 
	IImageCapability,
	IStyleClassCapability,
	ITextCapability,
	ITextDirectionCapability,
	IVisibilityCapability,
	IDialogPriorityCapability,
	ISizeCapability,
	IHiddenModeCapability,
	ILookAndFeelCapability,
	IWAIRoleCapability,
	ICloseEventCapability,
	IClosableCapability,
	IImmediateCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(ViewDialogComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.viewDialog";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaOutputComponent.BEHAVIOR_EVENT_NAMES;

	public ViewDialogComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ViewDialogComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
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

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return getStateHelper().get(Properties.IMAGE_URL)!=null;
	}

	public void setImageURL(java.lang.String imageURL) {
		getStateHelper().put(Properties.IMAGE_URL, imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
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

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		getStateHelper().put(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
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

	public int getDialogPriority() {
		return getDialogPriority(null);
	}

	/**
	 * See {@link #getDialogPriority() getDialogPriority()} for more details
	 */
	public int getDialogPriority(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.DIALOG_PRIORITY, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "dialogPriority" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDialogPrioritySetted() {
		return getStateHelper().get(Properties.DIALOG_PRIORITY)!=null;
	}

	public void setDialogPriority(int dialogPriority) {
		getStateHelper().put(Properties.DIALOG_PRIORITY, dialogPriority);
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

	public final void addCloseListener(org.rcfaces.core.event.ICloseListener listener) {
		addFacesListener(listener);
	}

	public final void removeCloseListener(org.rcfaces.core.event.ICloseListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCloseListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICloseListener.class);
	}

	public boolean isClosable() {
		return isClosable(null);
	}

	/**
	 * See {@link #isClosable() isClosable()} for more details
	 */
	public boolean isClosable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.CLOSABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "closable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClosableSetted() {
		return getStateHelper().get(Properties.CLOSABLE)!=null;
	}

	public void setClosable(boolean closable) {
		getStateHelper().put(Properties.CLOSABLE, closable);
	}

	public boolean isImmediate() {
		return isImmediate(null);
	}

	/**
	 * See {@link #isImmediate() isImmediate()} for more details
	 */
	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.IMMEDIATE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImmediateSetted() {
		return getStateHelper().get(Properties.IMMEDIATE)!=null;
	}

	public void setImmediate(boolean immediate) {
		getStateHelper().put(Properties.IMMEDIATE, immediate);
	}

	public String getViewURL() {
		return getViewURL(null);
	}

	public String getViewURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VIEW_URL);
	}

	public void setViewURL(String viewURL) {
		 getStateHelper().put(Properties.VIEW_URL, viewURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "viewURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isViewURLSetted() {
		return getStateHelper().get(Properties.VIEW_URL)!=null;
	}

	public String getShellDecoratorName() {
		return getShellDecoratorName(null);
	}

	public String getShellDecoratorName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SHELL_DECORATOR_NAME);
	}

	public void setShellDecoratorName(String shellDecoratorName) {
		 getStateHelper().put(Properties.SHELL_DECORATOR_NAME, shellDecoratorName);
	}

	/**
	 * Returns <code>true</code> if the attribute "shellDecoratorName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShellDecoratorNameSetted() {
		return getStateHelper().get(Properties.SHELL_DECORATOR_NAME)!=null;
	}

}
