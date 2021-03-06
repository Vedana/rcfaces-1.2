package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IDialogPriorityCapability;
import org.rcfaces.core.internal.component.CameliaInputComponent;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
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

public class MessageDialogComponent extends CameliaInputComponent implements 
	IImageCapability,
	IStyleClassCapability,
	ITextCapability,
	ITextDirectionCapability,
	IVisibilityCapability,
	IDialogPriorityCapability,
	IImmediateCapability,
	IValidationEventCapability,
	ISizeCapability,
	IHiddenModeCapability,
	ILookAndFeelCapability,
	IWAIRoleCapability,
	ISelectionEventCapability,
	IUserEventCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(MessageDialogComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageDialog";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"lookId","text","userEventListener","visible","styleClass","width","ariaLabel","dialogPriority","textDirection","ariaLevel","selectionListener","immediate","title","height","hiddenMode","defaultValue","waiRole","imageURL","validationListener"}));
	}

	public MessageDialogComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageDialogComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
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

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return engine.isPropertySetted(Properties.IMAGE_URL);
	}

	public void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
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

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
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

	public int getDialogPriority() {
		return getDialogPriority(null);
	}

	/**
	 * See {@link #getDialogPriority() getDialogPriority()} for more details
	 */
	public int getDialogPriority(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DIALOG_PRIORITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dialogPriority" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDialogPrioritySetted() {
		return engine.isPropertySetted(Properties.DIALOG_PRIORITY);
	}

	public void setDialogPriority(int dialogPriority) {
		engine.setProperty(Properties.DIALOG_PRIORITY, dialogPriority);
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
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

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public String getTitle() {
		return getTitle(null);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public String getTitle(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.TITLE, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	/**
	 * Returns <code>true</code> if the attribute "title" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTitleSetted() {
		return engine.isPropertySetted(Properties.TITLE);
	}

	public String getDefaultValue() {
		return getDefaultValue(null);
	}

	public String getDefaultValue(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.DEFAULT_VALUE, facesContext);
		return s;
	}

	public void setDefaultValue(String defaultValue) {
		engine.setProperty(Properties.DEFAULT_VALUE, defaultValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultValueSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_VALUE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
