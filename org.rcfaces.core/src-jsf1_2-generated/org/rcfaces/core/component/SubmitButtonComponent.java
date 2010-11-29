package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The submitButton Component is based on the standard HTML tag &lt;INPUT TYPE="submit"&gt;. It is a <a href="/comps/buttonComponent.html">button Component</a>.</p>
 * <p>The submitButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/SubmitButtonComponent.html">submitButton</a> renderer is linked to the <a href="/jsdoc/index.html?f_submitButton.html">f_submitButton</a> javascript class. f_submitButton extends f_button</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee"  width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_imageSubmitButton</td>
 * <td width="50%">Defines styles for the wrapper A element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class SubmitButtonComponent extends ButtonComponent {

	private static final Log LOG = LogFactory.getLog(SubmitButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitButton";


	public SubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
