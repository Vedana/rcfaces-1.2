package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.CriteriaComponent;
import javax.faces.context.FacesContext;

public class CriteriaTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CriteriaTag.class);

	private ValueExpression selectionCardinality;
	private ValueExpression selectedValues;
	private ValueExpression criteriaValue;
	private ValueExpression criteriaConverter;
	private ValueExpression labelConverter;
	public String getComponentType() {
		return CriteriaComponent.COMPONENT_TYPE;
	}

	public final void setSelectionCardinality(ValueExpression selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public final void setSelectedValues(ValueExpression selectedValues) {
		this.selectedValues = selectedValues;
	}

	public final void setCriteriaValue(ValueExpression criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public final void setCriteriaConverter(ValueExpression criteriaConverter) {
		this.criteriaConverter = criteriaConverter;
	}

	public final void setLabelConverter(ValueExpression labelConverter) {
		this.labelConverter = labelConverter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CriteriaComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  criteriaValue='"+criteriaValue+"'");
			LOG.debug("  criteriaConverter='"+criteriaConverter+"'");
			LOG.debug("  labelConverter='"+labelConverter+"'");
		}
		if ((uiComponent instanceof CriteriaComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CriteriaComponent'.");
		}

		super.setProperties(uiComponent);

		CriteriaComponent component = (CriteriaComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionCardinality != null) {
			if (selectionCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTION_CARDINALITY, selectionCardinality);

			} else {
				component.setSelectionCardinality(selectionCardinality.getExpressionString());
			}
		}

		if (selectedValues != null) {
			if (selectedValues.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_VALUES, selectedValues);

			} else {
				component.setSelectedValues(selectedValues.getExpressionString());
			}
		}

		if (criteriaValue != null) {
			if (criteriaValue.isLiteralText()==false) {
				component.setValueExpression(Properties.CRITERIA_VALUE, criteriaValue);

			} else {
				component.setCriteriaValue(criteriaValue.getExpressionString());
			}
		}

		if (criteriaConverter != null) {
			if (criteriaConverter.isLiteralText()==false) {
				component.setValueExpression(Properties.CRITERIA_CONVERTER, criteriaConverter);

			} else {
				component.setCriteriaConverter(criteriaConverter.getExpressionString());
			}
		}

		if (labelConverter != null) {
			if (labelConverter.isLiteralText()==false) {
				component.setValueExpression(Properties.LABEL_CONVERTER, labelConverter);

			} else {
				component.setLabelConverter(labelConverter.getExpressionString());
			}
		}
	}

	public void release() {
		selectionCardinality = null;
		selectedValues = null;
		criteriaValue = null;
		criteriaConverter = null;
		labelConverter = null;

		super.release();
	}

}
