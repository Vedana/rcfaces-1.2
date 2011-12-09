package org.rcfaces.core.component;

import org.rcfaces.core.internal.capability.IAnchoredPositionSettings;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.internal.component.Properties;
import java.lang.String;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.component.capability.IOrientationCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IAnchoredPositionCapability;
import org.rcfaces.core.component.capability.IMarginCapability;

/**
 * <p>The ruler Component is the &lt;HR&gt; HTML equivalent.</p>
 * <p>It is used often when simple HTML is not desirable : for example if a part of a page is loaded via AJAX it might be easier to have only a jsf tree memory represantation.</p>
 * <p>The ruler Component has the following capability :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Visibility</li>
 * <li>Margin</li>
 * </ul>
 * </p>
 */
public class RulerComponent extends CameliaBaseComponent implements 
	IPositionCapability,
	IAnchoredPositionCapability,
	IMarginCapability,
	ISizeCapability,
	IVisibilityCapability,
	IHiddenModeCapability,
	ILookAndFeelCapability,
	IOrientationCapability,
	IForegroundBackgroundColorCapability,
	IAlignmentCapability,
	IAnchoredPositionSettings {

	private static final Log LOG = LogFactory.getLog(RulerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.ruler";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public RulerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public RulerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setMargins(String margins) {


				MarginTools.setMargins(this, margins);
			
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

	public java.lang.String getOrientation() {
		return getOrientation(null);
	}

	/**
	 * See {@link #getOrientation() getOrientation()} for more details
	 */
	public java.lang.String getOrientation(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ORIENTATION);
	}

	/**
	 * Returns <code>true</code> if the attribute "orientation" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOrientationSetted() {
		return getStateHelper().get(Properties.ORIENTATION)!=null;
	}

	public void setOrientation(java.lang.String orientation) {
		getStateHelper().put(Properties.ORIENTATION, orientation);
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

	public java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "alignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlignmentSetted() {
		return getStateHelper().get(Properties.ALIGNMENT)!=null;
	}

	public void setAlignment(java.lang.String alignment) {
		getStateHelper().put(Properties.ALIGNMENT, alignment);
	}

}
