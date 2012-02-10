package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.AbstractBasicComponent;

public class CriteriaListComponent extends AbstractBasicComponent implements 
	IForCapability {

	private static final Log LOG = LogFactory.getLog(CriteriaListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.criteriaList";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"criteriaFormat","noCriteriaMessage","for"}));
	}

	public CriteriaListComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CriteriaListComponent(String componentId) {
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

	public String getCriteriaFormat() {
		return getCriteriaFormat(null);
	}

	public String getCriteriaFormat(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.CRITERIA_FORMAT, facesContext);


return s;
	}

	public void setCriteriaFormat(String criteriaFormat) {
		engine.setProperty(Properties.CRITERIA_FORMAT, criteriaFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "criteriaFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCriteriaFormatSetted() {
		return engine.isPropertySetted(Properties.CRITERIA_FORMAT);
	}

	public String getNoCriteriaMessage() {
		return getNoCriteriaMessage(null);
	}

	public String getNoCriteriaMessage(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.NO_CRITERIA_MESSAGE, facesContext);


return s;
	}

	public void setNoCriteriaMessage(String noCriteriaMessage) {
		engine.setProperty(Properties.NO_CRITERIA_MESSAGE, noCriteriaMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "noCriteriaMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNoCriteriaMessageSetted() {
		return engine.isPropertySetted(Properties.NO_CRITERIA_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
