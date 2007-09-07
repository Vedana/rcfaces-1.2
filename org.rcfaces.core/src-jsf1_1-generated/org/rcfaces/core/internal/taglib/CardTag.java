package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CardComponent;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CardTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardTag.class);

	private String textAlignment;
	private String verticalAlignment;
	private String scopeValue;
	private String scopeVar;
	private String loadListeners;
	public String getComponentType() {
		return CardComponent.COMPONENT_TYPE;
	}

	public final String getTextAlignment() {
		return textAlignment;
	}

	public final void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final String getVerticalAlignment() {
		return verticalAlignment;
	}

	public final void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final String getScopeValue() {
		return scopeValue;
	}

	public final void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final String getScopeVar() {
		return scopeVar;
	}

	public final void setScopeVar(String scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CardComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardComponent'.");
		}

		CardComponent component = (CardComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (textAlignment != null) {
			if (isValueReference(textAlignment)) {
				ValueBinding vb = application.createValueBinding(textAlignment);
				component.setValueBinding(Properties.TEXT_ALIGNMENT, vb);

			} else {
				component.setTextAlignment(textAlignment);
			}
		}

		if (verticalAlignment != null) {
			if (isValueReference(verticalAlignment)) {
				ValueBinding vb = application.createValueBinding(verticalAlignment);
				component.setValueBinding(Properties.VERTICAL_ALIGNMENT, vb);

			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (scopeValue != null) {
				ValueBinding vb = application.createValueBinding(scopeValue);
				component.setValueBinding(Properties.SCOPE_VALUE, vb);
		}

		if (scopeVar != null) {
			if (isValueReference(scopeVar)) {
				ValueBinding vb = application.createValueBinding(scopeVar);
				component.setValueBinding(Properties.SCOPE_VAR, vb);

			} else {
				component.setScopeVar(scopeVar);
			}
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}
	}

	public void release() {
		textAlignment = null;
		verticalAlignment = null;
		scopeValue = null;
		scopeVar = null;
		loadListeners = null;

		super.release();
	}

}
