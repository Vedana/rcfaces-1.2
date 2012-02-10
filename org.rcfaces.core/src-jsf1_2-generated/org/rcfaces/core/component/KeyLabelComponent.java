package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Arrays;
import java.util.Set;

public class KeyLabelComponent extends AbstractOutputComponent implements 
	IFilterCapability {

	private static final Log LOG = LogFactory.getLog(KeyLabelComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.keyLabel";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"parentsStyleClass","selectedStyleClass","filterProperties","showParents"}));
	}

	public KeyLabelComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public KeyLabelComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public String getSelectedStyleClass() {
		return getSelectedStyleClass(null);
	}

	public String getSelectedStyleClass(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.SELECTED_STYLE_CLASS, facesContext);


return s;
	}

	public void setSelectedStyleClass(String selectedStyleClass) {
		engine.setProperty(Properties.SELECTED_STYLE_CLASS, selectedStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectedStyleClassSetted() {
		return engine.isPropertySetted(Properties.SELECTED_STYLE_CLASS);
	}

	public String getParentsStyleClass() {
		return getParentsStyleClass(null);
	}

	public String getParentsStyleClass(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.PARENTS_STYLE_CLASS, facesContext);


return s;
	}

	public void setParentsStyleClass(String parentsStyleClass) {
		engine.setProperty(Properties.PARENTS_STYLE_CLASS, parentsStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "parentsStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isParentsStyleClassSetted() {
		return engine.isPropertySetted(Properties.PARENTS_STYLE_CLASS);
	}

	public boolean isShowParents() {
		return isShowParents(null);
	}

	public boolean isShowParents(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_PARENTS, false, facesContext);
	}

	public void setShowParents(boolean showParents) {
		engine.setProperty(Properties.SHOW_PARENTS, showParents);
	}

	/**
	 * Returns <code>true</code> if the attribute "showParents" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowParentsSetted() {
		return engine.isPropertySetted(Properties.SHOW_PARENTS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
