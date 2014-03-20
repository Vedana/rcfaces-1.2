package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The imageResetButton Component is a <a href="/comps/resetButtonComponent.html">resetButton Component</a> that can show an image.</p>
 * <p>The imageResetButton Component has the following capabilities :
 * <ul>
 * <li>IImageButtonFamilly </li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ImageResetButtonComponent.html">imageResetButton</a> renderer is linked to the <a href="/jsdocs/index.html?f_imageResetButton.html" target="_blank">f_imageResetButton</a> javascript class. f_imageResetButton extends f_imageButton</p>
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
 * <td width="33%">f_imageResetButton</td>
 * <td width="50%">Defines styles for the wrapper A element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageResetButton_image</td>
 * <td width="50%">Defines styles for the  IMG element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ImageResetButtonComponent extends ImageButtonComponent {

	private static final Log LOG = LogFactory.getLog(ImageResetButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageResetButton";


	public ImageResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
