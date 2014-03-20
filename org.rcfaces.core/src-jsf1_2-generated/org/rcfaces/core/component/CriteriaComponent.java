package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CriteriaTools;
import org.rcfaces.core.internal.component.Properties;
import java.lang.Object;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.internal.capability.ICriteriaContainer;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
import org.rcfaces.core.item.CriteriaItem;
import javax.faces.convert.Converter;
import org.rcfaces.core.model.ISelectedCriteria;
import org.rcfaces.core.internal.converter.SelectionCardinalityConverter;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.capability.ISelectionComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.CollectionTools;
import org.rcfaces.core.internal.tools.ComponentTools;

public class CriteriaComponent extends CameliaBaseComponent implements 
	ISelectionCardinalityCapability,
	ISelectedValuesCapability,
	ISelectionComponent,
	ICriteriaConfiguration {

	private static final Log LOG = LogFactory.getLog(CriteriaComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.criteria";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"criteriaValue","criteriaConverter","selectionCardinality","labelConverter","criteriaTitle","selectedValues"}));
	}

	public CriteriaComponent() {
		setRendererType(null);
	}

	public CriteriaComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setCriteriaConverter(String converterId) {


				setCriteriaConverter(converterId, null);
			
	}

	public void setCriteriaConverter(String converterId, FacesContext facesContext) {

			
				Converter converter=ComponentTools.createConverter(facesContext, converterId);

				setCriteriaConverter(converter);
			
	}

	public void setLabelConverter(String converterId) {


				setLabelConverter(converterId, null);
			
	}

	public void setLabelConverter(String converterId, FacesContext facesContext) {

			
				Converter converter=ComponentTools.createConverter(facesContext, converterId);

				setLabelConverter(converter);
			
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


				return SelectionTools.getFirst(getSelectedValues(), null);
			
	}

	public Object[] listSelectedValues() {


				return SelectionTools.listValues(getSelectedValues(), null);
			
	}

	public void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)SelectionCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public CriteriaItem[] listAvailableCriteriaItems() {


				return CriteriaTools.listAvailableCriteriaItems(this, null);
			
	}

	public CriteriaItem[] listAvailableCriteriaItems(ISelectedCriteria[] configs) {


				return CriteriaTools.listAvailableCriteriaItems(this, configs);
			
	}

	public ICriteriaContainer getCriteriaContainer() {


				return CriteriaTools.getCriteriaContainer(this);
			
	}

	public int getCriteriaCardinality() {


				return getSelectionCardinality();
			
	}

	public int getSelectionCardinality() {
		return getSelectionCardinality(null);
	}

	/**
	 * See {@link #getSelectionCardinality() getSelectionCardinality()} for more details
	 */
	public int getSelectionCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SELECTION_CARDINALITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectionCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectionCardinalitySetted() {
		return engine.isPropertySetted(Properties.SELECTION_CARDINALITY);
	}

	public void setSelectionCardinality(int selectionCardinality) {
		engine.setProperty(Properties.SELECTION_CARDINALITY, selectionCardinality);
	}

	public java.lang.Object getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * See {@link #getSelectedValues() getSelectedValues()} for more details
	 */
	public java.lang.Object getSelectedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.SELECTED_VALUES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedValuesSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUES);
	}

	public void setSelectedValues(java.lang.Object selectedValues) {
		engine.setProperty(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getSelectedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.SELECTED_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public Object getCriteriaValue() {
		return getCriteriaValue(null);
	}

	public Object getCriteriaValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.CRITERIA_VALUE, facesContext);
	}

	public void setCriteriaValue(Object criteriaValue) {
		engine.setValue(Properties.CRITERIA_VALUE, criteriaValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "criteriaValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCriteriaValueSetted() {
		return engine.isPropertySetted(Properties.CRITERIA_VALUE);
	}

	public String getCriteriaTitle() {
		return getCriteriaTitle(null);
	}

	public String getCriteriaTitle(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.CRITERIA_TITLE, facesContext);
		return s;
	}

	public void setCriteriaTitle(String criteriaTitle) {
		engine.setProperty(Properties.CRITERIA_TITLE, criteriaTitle);
	}

	/**
	 * Returns <code>true</code> if the attribute "criteriaTitle" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCriteriaTitleSetted() {
		return engine.isPropertySetted(Properties.CRITERIA_TITLE);
	}

	public javax.faces.convert.Converter getCriteriaConverter() {
		return getCriteriaConverter(null);
	}

	public javax.faces.convert.Converter getCriteriaConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.CRITERIA_CONVERTER, facesContext);
	}

	public void setCriteriaConverter(javax.faces.convert.Converter criteriaConverter) {
		engine.setProperty(Properties.CRITERIA_CONVERTER, criteriaConverter);
	}

	/**
	 * Returns <code>true</code> if the attribute "criteriaConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCriteriaConverterSetted() {
		return engine.isPropertySetted(Properties.CRITERIA_CONVERTER);
	}

	public javax.faces.convert.Converter getLabelConverter() {
		return getLabelConverter(null);
	}

	public javax.faces.convert.Converter getLabelConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.LABEL_CONVERTER, facesContext);
	}

	public void setLabelConverter(javax.faces.convert.Converter labelConverter) {
		engine.setProperty(Properties.LABEL_CONVERTER, labelConverter);
	}

	/**
	 * Returns <code>true</code> if the attribute "labelConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLabelConverterSetted() {
		return engine.isPropertySetted(Properties.LABEL_CONVERTER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
