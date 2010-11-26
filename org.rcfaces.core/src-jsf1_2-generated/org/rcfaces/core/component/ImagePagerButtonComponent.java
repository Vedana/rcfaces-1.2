package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.ImageButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;

/**
 * <p>The imagePagerButton Component is an <a href="/comps/imageButtonComponent.html">imageButton Component</a> that works like the keyword in the <a href="/comps/pagerComponent.html">pager Component</a> and is linked to the <a href="/comps/dataGridComponent.html">dataGrid Component</a>.
 * It shows informations about the result set (ex: number of available pages)
 * and can give direct access to a specific page ("a la Google").</p>
 * <p>The imagePagerButton Component has the following capabilities :
 * <ul>
 * <li>IForCapability</li>
 * </ul>
 * </p>
 * <p>The authorized types are :
 * <ul>
 * <li>first</li>
 * <li>prev</li>
 * <li>next</li>
 * <li>last</li>
 * <li>&lt;number&gt; where number is a page number</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ImagePagerButtonComponent.html">imagePagerButton</a> renderer is linked to the <a href="/jsdoc/index.html?f_imagePagerButton.html">f_imagePagerButton</a> javascript class. f_imagePagerButton extends f_imageButton, fa_pager</p>
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
 * <td bgcolor="#ffffff" width="33%">f_imagePagerButton</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_imagePagerButton_text</td>
 * <td width="50%">Defines styles for the SPAN element of the button</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_imagePagerButton_image</td>
 * <td width="50%">Defines styles for the A and IMG elements of the button</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ImagePagerButtonComponent extends ImageButtonComponent implements 
	IForCapability {

	private static final Log LOG = LogFactory.getLog(ImagePagerButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imagePagerButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"for","hideIfDisabled","type"}));
	}

	public ImagePagerButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImagePagerButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * Returns a string value specifying the type of data (Java) to convert to or from.
	 * @return type of data
	 */
	public String getType() {
		return getType(null);
	}

	/**
	 * Returns a string value specifying the type of data (Java) to convert to or from.
	 * @return type of data
	 */
	public String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	public void setType(String type) {
		engine.setProperty(Properties.TYPE, type);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	public boolean isHideIfDisabled() {
		return isHideIfDisabled(null);
	}

	public boolean isHideIfDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HIDE_IF_DISABLED, false, facesContext);
	}

	public void setHideIfDisabled(boolean hideIfDisabled) {
		engine.setProperty(Properties.HIDE_IF_DISABLED, hideIfDisabled);
	}

	/**
	 * Returns <code>true</code> if the attribute "hideIfDisabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHideIfDisabledSetted() {
		return engine.isPropertySetted(Properties.HIDE_IF_DISABLED);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
