package org.rcfaces.core.component;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The passwordEntry Component is based on the standard HTML tag &lt;INPUT TYPE="password"&gt; and is a <a href="/comps/textEntryComponent.html">textEntry Component</a>.</p>
 * <p>The passwordEntry Component has the following capabilities :
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
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/PasswordEntrykComponent.html">passwordEntry</a> renderer is linked to the <a href="/jsdocs/index.html?f_passwordEntry.html" target="_blank">f_passwordEntry</a> javascript class. f_passwordEntry extends f_textEntry</p>
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
 * <td width="33%">f_passwordEntry</td>
 * <td width="50%">Defines styles for the INPUT element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class PasswordEntryComponent extends TextEntryComponent {

	private static final Log LOG = LogFactory.getLog(PasswordEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.passwordEntry";


	public PasswordEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PasswordEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
