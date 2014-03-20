package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IHeadingZoneCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IHeadingLevelCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICaptionCapability;
import java.util.Arrays;
import java.util.Set;

public class HeadingZoneComponent extends CameliaBaseComponent implements 
	IHeadingZoneCapability,
	IHeadingLevelCapability,
	ICaptionCapability {

	private static final Log LOG = LogFactory.getLog(HeadingZoneComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.headingZone";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"headingLevel","caption","headingZone"}));
	}

	public HeadingZoneComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HeadingZoneComponent(String componentId) {
		this();
		setId(componentId);
	}

	public boolean isHeadingZone() {
		return isHeadingZone(null);
	}

	/**
	 * See {@link #isHeadingZone() isHeadingZone()} for more details
	 */
	public boolean isHeadingZone(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADING_ZONE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingZoneSetted() {
		return engine.isPropertySetted(Properties.HEADING_ZONE);
	}

	public void setHeadingZone(boolean headingZone) {
		engine.setProperty(Properties.HEADING_ZONE, headingZone);
	}

	public java.lang.String getHeadingLevel() {
		return getHeadingLevel(null);
	}

	/**
	 * See {@link #getHeadingLevel() getHeadingLevel()} for more details
	 */
	public java.lang.String getHeadingLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEADING_LEVEL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingLevelSetted() {
		return engine.isPropertySetted(Properties.HEADING_LEVEL);
	}

	public void setHeadingLevel(java.lang.String headingLevel) {
		engine.setProperty(Properties.HEADING_LEVEL, headingLevel);
	}

	public java.lang.String getCaption() {
		return getCaption(null);
	}

	/**
	 * See {@link #getCaption() getCaption()} for more details
	 */
	public java.lang.String getCaption(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CAPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "caption" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCaptionSetted() {
		return engine.isPropertySetted(Properties.CAPTION);
	}

	public void setCaption(java.lang.String caption) {
		engine.setProperty(Properties.CAPTION, caption);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
