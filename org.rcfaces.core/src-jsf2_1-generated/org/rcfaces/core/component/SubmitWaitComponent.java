package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ITextCapability;

public class SubmitWaitComponent extends CameliaBaseComponent implements 
	IImageCapability,
	ITextCapability,
	IStyleClassCapability,
	ISizeCapability,
	IWAIRoleCapability,
	ILookAndFeelCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(SubmitWaitComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitWait";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public SubmitWaitComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitWaitComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
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

	public String getBackgroundMode() {
		return getBackgroundMode(null);
	}

	public String getBackgroundMode(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_MODE);
	}

	public void setBackgroundMode(String backgroundMode) {
		 getStateHelper().put(Properties.BACKGROUND_MODE, backgroundMode);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBackgroundModeSetted() {
		return getStateHelper().get(Properties.BACKGROUND_MODE)!=null;
	}

}
