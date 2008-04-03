package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.HiddenValueComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class HiddenValueTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HiddenValueTag.class);

	private ValueExpression propertyChangeListeners;
	private ValueExpression immediate;
	private ValueExpression valueLocked;
	private ValueExpression validationListeners;
	private ValueExpression value;
	private ValueExpression converter;
	public String getComponentType() {
		return HiddenValueComponent.COMPONENT_TYPE;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setValueLocked(ValueExpression valueLocked) {
		this.valueLocked = valueLocked;
	}

	public final void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HiddenValueComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  valueLocked='"+valueLocked+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof HiddenValueComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HiddenValueComponent'.");
		}

		HiddenValueComponent component = (HiddenValueComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (valueLocked != null) {
			if (valueLocked.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_LOCKED, valueLocked);

			} else {
				component.setValueLocked(getBool(valueLocked.getExpressionString()));
			}
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}

		if (converter != null) {
			if (converter.isLiteralText()==false) {
				component.setValueExpression(Properties.CONVERTER, converter);

			} else {
				component.setConverter(converter.getExpressionString());
			}
		}
	}

	public void release() {
		propertyChangeListeners = null;
		immediate = null;
		valueLocked = null;
		validationListeners = null;
		value = null;
		converter = null;

		super.release();
	}

}
