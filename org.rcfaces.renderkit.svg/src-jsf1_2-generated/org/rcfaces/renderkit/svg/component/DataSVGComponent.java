package org.rcfaces.renderkit.svg.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.renderkit.svg.component.SVGComponent;

public class DataSVGComponent extends SVGComponent {

	private static final Log LOG = LogFactory.getLog(DataSVGComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.dataSVG";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(SVGComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"dataModel","var"}));
	}

	public DataSVGComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DataSVGComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VAR, facesContext);
		return s;
	}

	public void setVar(String var) {
		engine.setProperty(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return engine.isPropertySetted(Properties.VAR);
	}

	public Object getDataModel() {
		return getDataModel(null);
	}

	public Object getDataModel(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.DATA_MODEL, facesContext);
	}

	public void setDataModel(Object dataModel) {
		engine.setValue(Properties.DATA_MODEL, dataModel);
	}

	/**
	 * Returns <code>true</code> if the attribute "dataModel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDataModelSetted() {
		return engine.isPropertySetted(Properties.DATA_MODEL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
