package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.SelectionTools;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.capability.ISelectionComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.CollectionTools;
import org.rcfaces.core.internal.tools.ComponentTools;

public class CriteriaComponent extends CameliaBaseComponent implements 
	ISelectionComponent {

	private static final Log LOG = LogFactory.getLog(CriteriaComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.criteria";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"converter","value"}));
	}

	public CriteriaComponent() {
		setRendererType(null);
	}

	public CriteriaComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setConverter(String converterId) {

			
				setConverter(converterId, null);
			
	}

	public void setConverter(String converterId, FacesContext facesContext) {


				Converter converter=ComponentTools.createConverter(facesContext, converterId);
	
				setConverter(converter);
			
	}

	public void select(Object rowValue) {


				SelectionTools.select(null, this, rowValue);
			
	}

	public void selectAll() {


				SelectionTools.selectAll(null, this);
			
	}

	public void deselect(Object rowValue) {


				SelectionTools.deselect(null, this, rowValue);
			
	}

	public void deselectAll() {


				SelectionTools.deselectAll(null, this);
			
	}

	public int getSelectedValuesCount() {


				return SelectionTools.getCount(getSelectedValues());
			
	}

	public Object getFirstSelectedValue() {


				return SelectionTools.getFirst(getSelectedValues(), getValue());
			
	}

	public Object[] listSelectedValues() {


				return SelectionTools.listValues(getSelectedValues(), getValue());
			
	}

	public Object getSelectedValues() {

			
				return getSelectedValues(null);
			
	}

	public Object getSelectedValues(FacesContext facesContext) {

			
				if (engine.isPropertySetted(Properties.SELECTED_VALUES)) {
					return engine.getValue(Properties.SELECTED_VALUES, facesContext);
				}

				return SelectionTools.getAdaptedValues(getValue(), false);
			
	}

	public void setSelectedValues(Object selectedValues) {


				if (engine.isPropertySetted(Properties.SELECTED_VALUES)==false) {
					if (SelectionTools.setAdaptedValues(getValue(), selectedValues)) {
						return;
					}
				}
								
				engine.setValue(Properties.SELECTED_VALUES, selectedValues);
			
	}

	public Object getValue() {
		return getValue(null);
	}

	public Object getValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.VALUE, facesContext);
	}

	public void setValue(Object value) {
		engine.setValue(Properties.VALUE, value);
	}

	/**
	 * Returns <code>true</code> if the attribute "value" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueSetted() {
		return engine.isPropertySetted(Properties.VALUE);
	}

	public javax.faces.convert.Converter getConverter() {
		return getConverter(null);
	}

	public javax.faces.convert.Converter getConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.CONVERTER, facesContext);
	}

	public void setConverter(javax.faces.convert.Converter converter) {
		engine.setProperty(Properties.CONVERTER, converter);
	}

	/**
	 * Returns <code>true</code> if the attribute "converter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isConverterSetted() {
		return engine.isPropertySetted(Properties.CONVERTER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
