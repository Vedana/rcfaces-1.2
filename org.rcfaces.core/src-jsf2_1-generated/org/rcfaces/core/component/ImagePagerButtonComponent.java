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
import java.util.Collection;

/**
 * <p>The imagePagerButton Component is an <a href="/comps/imageButtonComponent.html">imageButton Component</a> that works like the keyword in the <a href="/comps/pagerComponent.html">pager Component</a> and is linked to the <a href="/comps/dataGridComponent.html">dataGrid Component</a>.
 * It shows informations about the result set (ex: number of available pages)
 * and can give direct access to a specific page ("a la Google").</p>
 * <p>The imagePagerButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
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
 */
public class ImagePagerButtonComponent extends ImageButtonComponent implements 
	IForCapability {

	private static final Log LOG = LogFactory.getLog(ImagePagerButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imagePagerButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ImageButtonComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.TYPE);
	}

	/**
	 * Sets a string value specifying the type of data (Java) to convert to or from.
	 * @param type type of data
	 */
	public void setType(String type) {
		 getStateHelper().put(Properties.TYPE, type);
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
		return getStateHelper().get(Properties.TYPE)!=null;
	}

	public boolean isHideIfDisabled() {
		return isHideIfDisabled(null);
	}

	public boolean isHideIfDisabled(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.HIDE_IF_DISABLED, false);
	}

	public void setHideIfDisabled(boolean hideIfDisabled) {
		 getStateHelper().put(Properties.HIDE_IF_DISABLED, hideIfDisabled);
	}

	/**
	 * Returns <code>true</code> if the attribute "hideIfDisabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHideIfDisabledSetted() {
		return getStateHelper().get(Properties.HIDE_IF_DISABLED)!=null;
	}

}
