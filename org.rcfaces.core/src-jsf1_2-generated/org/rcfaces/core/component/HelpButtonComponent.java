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
 * <p>The helpButton Component is an <A href="/comps/imageButtonComponent.html">ImageButton</A>. It opens a new page showing the url specified in the "helpUrl" property of a component linked by the "for" property.</p>
 * <p>The helpButton Component has the following capabilities :
 * <ul>
 * <li>IForCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/HelpButtonComponent.html">helpButton</a> renderer is link to the <a href="/jsdocs/index.html?f_helpButton.html" target="_blank">f_helpButton</a> javascript class. f_helpButton extends f_imageButton</p>
 * 
 * 
 * <p>Table of component style classes : </p>
 * 
 * <table border="1" cellpadding="3" cellspacing="0"  width="100%">
 * <tbody >
 * <tr  style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">
 * Style Name
 * </td>
 * <td bgcolor="#eeeeee"  width="50%">
 * Description
 * </td>
 * </tr>
 * <tr  style="text-align:left">
 * <td  width="33%">
 * f_helpButton
 * </td>The server reported an error while performing the "cvs commit" command.
 * org.rcfaces.core: cvs commit: sticky tag `1.3' for file `metadatas/camelia-doc.xml' is not a branch
 * org.rcfaces.core: cvs [commit aborted]: correct above errors first!
 * <td width="50%">
 * Defines styles for the wrapper DIV element
 * </td>
 * </tr>
 * <tr style="text-align:left">
 * <td  width="33%">
 * f_helpButton_ctext
 * </td>
 * <td width="50%">
 * Defines styles for the button Text
 * </td>
 * </tr>
 * <tr style="text-align:left">
 * <td width="33%">
 * f_helpButton_cimage
 * </td>
 * <td width="50%">
 * Defines styles for the button Image
 * </td>
 * </tr>
 * </tbody>
 * </table>
 */
public class HelpButtonComponent extends ImageButtonComponent implements 
	IForCapability {

	private static final Log LOG = LogFactory.getLog(HelpButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.helpButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"for"}));
	}

	public HelpButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HelpButtonComponent(String componentId) {
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
