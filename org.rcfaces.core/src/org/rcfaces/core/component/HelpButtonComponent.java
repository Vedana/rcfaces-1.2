package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ImageButtonComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IForCapability;

/**
 * <p>The helpButton Component is an <A href="/comps/imageButtonComponent.html">ImageButton</A>. It opens a new page showing the url specified in the "helpUrl" property of a component linked by the "for" property.</p>
 * <p>The helpButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
 * </ul>
 * </p>
 */
public class HelpButtonComponent extends ImageButtonComponent implements 
	IForCapability {

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

	public final java.lang.String getFor() {
		return getFor(null);
	}

	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
