package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ILayoutPositionCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.internal.converter.AlignmentNormalizer;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ISizeCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.component.capability.IOrientationCapability;
import org.rcfaces.core.component.capability.IMarginCapability;

/**
 * <p>The ruler Component is the &lt;HR&gt; HTML equivalent.</p>
 * <p>It is used often when simple HTML is not desirable : for example if a part of a page is loaded via AJAX it might be easier to have only a jsf tree memory represantation.</p>
 * <p>The ruler Component has the following capability :
 * <ul>
 * <li>IPositionCapability</li>
 * <li>IMarginCapability</li>
 * <li>ISizeCapability</li>
 * <li>IVisibilityCapability</li>
 * <li>IHiddenModeCapability</li>
 * <li>ILookAndFeelCapability</li>
 * <li>IOrientationCapability</li>
 * <li>IForegroundBackgroundColorCapability</li>
 * <li>IAlignmentCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/RulerComponent.html">ruler</a> renderer is linked to the <a href="/jsdocs/index.html?f_ruler.html" target="_blank">f_ruler</a> javascript class. f_ruler extends f_component</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_ruler</td>
 * <td width="50%">Defines styles for the HR element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class RulerComponent extends CameliaBaseComponent implements 
	IPositionCapability,
	ILayoutPositionCapability,
	IMarginCapability,
	ISizeCapability,
	IVisibilityCapability,
	IHiddenModeCapability,
	ILookAndFeelCapability,
	IOrientationCapability,
	IForegroundBackgroundColorCapability,
	IAlignmentCapability {

	private static final Log LOG = LogFactory.getLog(RulerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.ruler";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"alignment","orientation","lookId","visible","backgroundColor","marginLeft","marginTop","marginRight","width","rendered","verticalCenter","marginBottom","height","hiddenMode","bottom","left","right","foregroundColor","y","horizontalCenter","margins","x","top"}));
	}

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


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
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

	public java.lang.Number getBottom() {
		return getBottom(null);
	}

	/**
	 * See {@link #getBottom() getBottom()} for more details
	 */
	public java.lang.Number getBottom(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.BOTTOM, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "bottom" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBottomSetted() {
		return engine.isPropertySetted(Properties.BOTTOM);
	}

	public void setBottom(java.lang.Number bottom) {
		engine.setProperty(Properties.BOTTOM, bottom);
	}

	public java.lang.Number getHorizontalCenter() {
		return getHorizontalCenter(null);
	}

	/**
	 * See {@link #getHorizontalCenter() getHorizontalCenter()} for more details
	 */
	public java.lang.Number getHorizontalCenter(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.HORIZONTAL_CENTER, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalCenter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalCenterSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_CENTER);
	}

	public void setHorizontalCenter(java.lang.Number horizontalCenter) {
		engine.setProperty(Properties.HORIZONTAL_CENTER, horizontalCenter);
	}

	public java.lang.Number getLeft() {
		return getLeft(null);
	}

	/**
	 * See {@link #getLeft() getLeft()} for more details
	 */
	public java.lang.Number getLeft(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.LEFT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "left" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLeftSetted() {
		return engine.isPropertySetted(Properties.LEFT);
	}

	public void setLeft(java.lang.Number left) {
		engine.setProperty(Properties.LEFT, left);
	}

	public java.lang.Number getRight() {
		return getRight(null);
	}

	/**
	 * See {@link #getRight() getRight()} for more details
	 */
	public java.lang.Number getRight(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.RIGHT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "right" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRightSetted() {
		return engine.isPropertySetted(Properties.RIGHT);
	}

	public void setRight(java.lang.Number right) {
		engine.setProperty(Properties.RIGHT, right);
	}

	public java.lang.Number getTop() {
		return getTop(null);
	}

	/**
	 * See {@link #getTop() getTop()} for more details
	 */
	public java.lang.Number getTop(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.TOP, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "top" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTopSetted() {
		return engine.isPropertySetted(Properties.TOP);
	}

	public void setTop(java.lang.Number top) {
		engine.setProperty(Properties.TOP, top);
	}

	public java.lang.Number getVerticalCenter() {
		return getVerticalCenter(null);
	}

	/**
	 * See {@link #getVerticalCenter() getVerticalCenter()} for more details
	 */
	public java.lang.Number getVerticalCenter(javax.faces.context.FacesContext facesContext) {
		return (java.lang.Number)engine.getProperty(Properties.VERTICAL_CENTER, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalCenter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalCenterSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_CENTER);
	}

	public void setVerticalCenter(java.lang.Number verticalCenter) {
		engine.setProperty(Properties.VERTICAL_CENTER, verticalCenter);
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

	public java.lang.String getOrientation() {
		return getOrientation(null);
	}

	/**
	 * See {@link #getOrientation() getOrientation()} for more details
	 */
	public java.lang.String getOrientation(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ORIENTATION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "orientation" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOrientationSetted() {
		return engine.isPropertySetted(Properties.ORIENTATION);
	}

	public void setOrientation(java.lang.String orientation) {
		engine.setProperty(Properties.ORIENTATION, orientation);
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

	public java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "alignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlignmentSetted() {
		return engine.isPropertySetted(Properties.ALIGNMENT);
	}

	public void setAlignment(String alignment) {


			engine.setProperty(Properties.ALIGNMENT, AlignmentNormalizer.normalize(alignment));
    	
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
