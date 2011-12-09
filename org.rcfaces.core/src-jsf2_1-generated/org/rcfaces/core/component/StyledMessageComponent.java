package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * An englobing tag that changes the style class surronuding a component when a particular message is emitted for a component.
 * ex :
 * &lt;v:styledMessage errorStyleClass="formErreur" for="ef1"&gt;
 * &lt;v:textEntry
 * id="ef1"
 * ... /&gt;
 * &lt;/v:styledMessage&gt;
 */
public class StyledMessageComponent extends AbstractBasicComponent implements 
	IBackgroundImageCapability,
	IBorderCapability,
	IMouseEventCapability,
	IInitEventCapability,
	IForCapability,
	ISeverityStyleClassCapability {

	private static final Log LOG = LogFactory.getLog(StyledMessageComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.styledMessage";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

	public StyledMessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public StyledMessageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getBackgroundImageHorizontalPosition() {
		return getBackgroundImageHorizontalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageHorizontalPosition() getBackgroundImageHorizontalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageHorizontalPosition(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalPositionSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION)!=null;
	}

	public void setBackgroundImageHorizontalPosition(java.lang.String backgroundImageHorizontalPosition) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public boolean isBackgroundImageHorizontalRepeat() {
		return isBackgroundImageHorizontalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageHorizontalRepeat() isBackgroundImageHorizontalRepeat()} for more details
	 */
	public boolean isBackgroundImageHorizontalRepeat(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageHorizontalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageHorizontalRepeatSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT)!=null;
	}

	public void setBackgroundImageHorizontalRepeat(boolean backgroundImageHorizontalRepeat) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public java.lang.String getBackgroundImageURL() {
		return getBackgroundImageURL(null);
	}

	/**
	 * See {@link #getBackgroundImageURL() getBackgroundImageURL()} for more details
	 */
	public java.lang.String getBackgroundImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageURLSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_URL)!=null;
	}

	public void setBackgroundImageURL(java.lang.String backgroundImageURL) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public java.lang.String getBackgroundImageVerticalPosition() {
		return getBackgroundImageVerticalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageVerticalPosition() getBackgroundImageVerticalPosition()} for more details
	 */
	public java.lang.String getBackgroundImageVerticalPosition(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalPositionSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION)!=null;
	}

	public void setBackgroundImageVerticalPosition(java.lang.String backgroundImageVerticalPosition) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public boolean isBackgroundImageVerticalRepeat() {
		return isBackgroundImageVerticalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageVerticalRepeat() isBackgroundImageVerticalRepeat()} for more details
	 */
	public boolean isBackgroundImageVerticalRepeat(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundImageVerticalRepeat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundImageVerticalRepeatSetted() {
		return getStateHelper().get(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT)!=null;
	}

	public void setBackgroundImageVerticalRepeat(boolean backgroundImageVerticalRepeat) {
		getStateHelper().put(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
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

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ERROR_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return getStateHelper().get(Properties.ERROR_STYLE_CLASS)!=null;
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		getStateHelper().put(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FATAL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return getStateHelper().get(Properties.FATAL_STYLE_CLASS)!=null;
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		getStateHelper().put(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INFO_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return getStateHelper().get(Properties.INFO_STYLE_CLASS)!=null;
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		getStateHelper().put(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WARN_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return getStateHelper().get(Properties.WARN_STYLE_CLASS)!=null;
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		getStateHelper().put(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public boolean isShowIfMessage() {
		return isShowIfMessage(null);
	}

	public boolean isShowIfMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_IF_MESSAGE, false);
	}

	public void setShowIfMessage(boolean showIfMessage) {
		 getStateHelper().put(Properties.SHOW_IF_MESSAGE, showIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowIfMessageSetted() {
		return getStateHelper().get(Properties.SHOW_IF_MESSAGE)!=null;
	}

	public boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SET_FOCUS_IF_MESSAGE, false);
	}

	public void setSetFocusIfMessage(boolean setFocusIfMessage) {
		 getStateHelper().put(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSetFocusIfMessageSetted() {
		return getStateHelper().get(Properties.SET_FOCUS_IF_MESSAGE)!=null;
	}

}
