package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The resetButton Component is based on the standard HTML tag &lt;INPUT TYPE="reset"&gt;. It is a <a href="/comps/buttonComponent.html">button Component</a>.</p>
 * <p>The resetButton Component has the following capabilities :
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
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ResetButtonComponent.html">resetButton</a> renderer is linked to the <a href="/jsdocs/index.html?f_resetButton.html" target="_blank">f_resetButton</a> javascript class. f_resetButton extends f_button</p>
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
 * <td width="33%">f_resetButton</td>
 * <td width="50%">Defines styles for the wrapper element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ResetButtonComponent extends ButtonComponent {

	private static final Log LOG = LogFactory.getLog(ResetButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.resetButton";


	public ResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
