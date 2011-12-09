package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

/**
 * <p>The ImageSubmitButton Component is a <a href="/comps/submitButtonComponent.html">submitButton Component</a> that can show an image.</p>
 * <p>The ImageSubmitButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ImageSubmitButtonComponent extends ImageButtonComponent {

	private static final Log LOG = LogFactory.getLog(ImageSubmitButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageSubmitButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ImageButtonComponent.BEHAVIOR_EVENT_NAMES;

	public ImageSubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageSubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

}
