package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Collection;

public class KeyLabelComponent extends AbstractOutputComponent implements 
	IFilterCapability {

	private static final Log LOG = LogFactory.getLog(KeyLabelComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.keyLabel";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractOutputComponent.BEHAVIOR_EVENT_NAMES;

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
		return (org.rcfaces.core.model.IFilterProperties)getStateHelper().eval(Properties.FILTER_PROPERTIES);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return getStateHelper().get(Properties.FILTER_PROPERTIES)!=null;
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		getStateHelper().put(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public String getSelectedStyleClass() {
		return getSelectedStyleClass(null);
	}

	public String getSelectedStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SELECTED_STYLE_CLASS);
	}

	public void setSelectedStyleClass(String selectedStyleClass) {
		 getStateHelper().put(Properties.SELECTED_STYLE_CLASS, selectedStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectedStyleClassSetted() {
		return getStateHelper().get(Properties.SELECTED_STYLE_CLASS)!=null;
	}

	public String getParentsStyleClass() {
		return getParentsStyleClass(null);
	}

	public String getParentsStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PARENTS_STYLE_CLASS);
	}

	public void setParentsStyleClass(String parentsStyleClass) {
		 getStateHelper().put(Properties.PARENTS_STYLE_CLASS, parentsStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "parentsStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isParentsStyleClassSetted() {
		return getStateHelper().get(Properties.PARENTS_STYLE_CLASS)!=null;
	}

	public boolean isShowParents() {
		return isShowParents(null);
	}

	public boolean isShowParents(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_PARENTS, false);
	}

	public void setShowParents(boolean showParents) {
		 getStateHelper().put(Properties.SHOW_PARENTS, showParents);
	}

	/**
	 * Returns <code>true</code> if the attribute "showParents" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowParentsSetted() {
		return getStateHelper().get(Properties.SHOW_PARENTS)!=null;
	}

}
