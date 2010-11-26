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
import org.rcfaces.core.component.KeyLabelComponentXX;
import javax.faces.context.FacesContext;

public class KeyLabelTagxx extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(KeyLabelTagxx.class);

	private ValueExpression filterProperties;
	private ValueExpression selectedStyleClass;
	private ValueExpression parentsStyleClass;
	private ValueExpression showParents;
	public String getComponentType() {
		return KeyLabelComponentXX.COMPONENT_TYPE;
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
			if (KeyLabelComponentXX.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  selectedStyleClass='"+selectedStyleClass+"'");
			LOG.debug("  parentsStyleClass='"+parentsStyleClass+"'");
			LOG.debug("  showParents='"+showParents+"'");
		}
		if ((uiComponent instanceof KeyLabelComponentXX)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'KeyLabelComponent'.");
		}

		super.setProperties(uiComponent);

		KeyLabelComponentXX component = (KeyLabelComponentXX) uiComponent;
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
