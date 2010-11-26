package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;

/**
 * <p>The lineBreak Component is the &lt;BR&gt; HTML equivalent.</p>
 * <p>It is used often when simple HTML is not desirable : for example if a part of a page is loaded via AJAX it might be easier to have only a jsf tree memory represantation.</p>
 * <p>The lineBreak Component has the following capability :
 * <ul>
 * <li>IStyleClassCapability</li>
 * <li>IVisibilityCapability</li>
 * <li>IHiddenModeCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/LineBreakComponent.html">lineBreak</a> renderer is linked to the <a href="/jsdoc/index.html?f_lineBreak.html">f_lineBreak</a> javascript class. f_lineBreak extends f_component</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee" " width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_lineBreak</td>
 * <td width="50%">Defines styles for the wrapper element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class LineBreakComponent extends CameliaBaseComponent implements 
	IStyleClassCapability,
	IVisibilityCapability,
	IHiddenModeCapability {

	private static final Log LOG = LogFactory.getLog(LineBreakComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.lineBreak";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"visible","hiddenMode","styleClass","rendered"}));
	}

	public LineBreakComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LineBreakComponent(String componentId) {
		this();
		setId(componentId);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
