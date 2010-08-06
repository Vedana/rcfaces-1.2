package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.KeyLabelComponent;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class KeyLabelTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(KeyLabelTag.class);

	private ValueExpression filterProperties;
	private ValueExpression selectedStyleClass;
	private ValueExpression parentsStyleClass;
	private ValueExpression showParents;
	public String getComponentType() {
		return KeyLabelComponent.COMPONENT_TYPE;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setSelectedStyleClass(ValueExpression selectedStyleClass) {
		this.selectedStyleClass = selectedStyleClass;
	}

	public final void setParentsStyleClass(ValueExpression parentsStyleClass) {
		this.parentsStyleClass = parentsStyleClass;
	}

	public final void setShowParents(ValueExpression showParents) {
		this.showParents = showParents;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (KeyLabelComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  selectedStyleClass='"+selectedStyleClass+"'");
			LOG.debug("  parentsStyleClass='"+parentsStyleClass+"'");
			LOG.debug("  showParents='"+showParents+"'");
		}
		if ((uiComponent instanceof KeyLabelComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'KeyLabelComponent'.");
		}

		super.setProperties(uiComponent);

		KeyLabelComponent component = (KeyLabelComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (filterProperties != null) {
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (selectedStyleClass != null) {
			if (selectedStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_STYLE_CLASS, selectedStyleClass);

			} else {
				component.setSelectedStyleClass(selectedStyleClass.getExpressionString());
			}
		}

		if (parentsStyleClass != null) {
			if (parentsStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.PARENTS_STYLE_CLASS, parentsStyleClass);

			} else {
				component.setParentsStyleClass(parentsStyleClass.getExpressionString());
			}
		}

		if (showParents != null) {
			if (showParents.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_PARENTS, showParents);

			} else {
				component.setShowParents(getBool(showParents.getExpressionString()));
			}
		}
	}

	public void release() {
		filterProperties = null;
		selectedStyleClass = null;
		parentsStyleClass = null;
		showParents = null;

		super.release();
	}

}
