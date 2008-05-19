package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ComboComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression required;
	private ValueExpression focusStyleClass;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression filterProperties;
	public String getComponentType() {
		return ComboComponent.COMPONENT_TYPE;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public final void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComboComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboComponent'.");
		}

		ComboComponent component = (ComboComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
			}
		}

		if (errorStyleClass != null) {
			if (errorStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ERROR_STYLE_CLASS, errorStyleClass);

			} else {
				component.setErrorStyleClass(errorStyleClass.getExpressionString());
			}
		}

		if (fatalStyleClass != null) {
			if (fatalStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FATAL_STYLE_CLASS, fatalStyleClass);

			} else {
				component.setFatalStyleClass(fatalStyleClass.getExpressionString());
			}
		}

		if (infoStyleClass != null) {
			if (infoStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.INFO_STYLE_CLASS, infoStyleClass);

			} else {
				component.setInfoStyleClass(infoStyleClass.getExpressionString());
			}
		}

		if (warnStyleClass != null) {
			if (warnStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.WARN_STYLE_CLASS, warnStyleClass);

			} else {
				component.setWarnStyleClass(warnStyleClass.getExpressionString());
			}
		}

		if (filterProperties != null) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);
		}
	}

	public void release() {
		selectionListeners = null;
		required = null;
		focusStyleClass = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		filterProperties = null;

		super.release();
	}

}
