package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.component.CardComponent;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CardTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardTag.class);

	private String textAlignment;
	private String verticalAlignment;
	private String scopeSaveValue;
	private String scopeValue;
	private String scopeVar;
	private String asyncDecodeMode;
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

	public final String getScopeSaveValue() {
		return scopeSaveValue;
	}

	public final void setScopeSaveValue(String scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
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

	public final String getAsyncDecodeMode() {
		return asyncDecodeMode;
	}

	public final void setAsyncDecodeMode(String asyncDecodeMode) {
		this.asyncDecodeMode = asyncDecodeMode;
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
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  asyncDecodeMode='"+asyncDecodeMode+"'");
		}
		if ((uiComponent instanceof CardComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardComponent'.");
		}

		super.setProperties(uiComponent);

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

		if (scopeSaveValue != null) {
			if (isValueReference(scopeSaveValue)) {
				ValueBinding vb = application.createValueBinding(scopeSaveValue);
				component.setValueBinding(Properties.SCOPE_SAVE_VALUE, vb);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue));
			}
		}

		if (scopeValue != null) {
			if (isValueReference(scopeValue)) {
				ValueBinding vb = application.createValueBinding(scopeValue);
				component.setValueBinding(Properties.SCOPE_VALUE, vb);

			} else {
				component.setScopeValue(scopeValue);
			}
		}

		if (scopeVar != null) {
			if (isValueReference(scopeVar)) {
				ValueBinding vb = application.createValueBinding(scopeVar);
				component.setValueBinding(Properties.SCOPE_VAR, vb);

			} else {
				component.setScopeVar(scopeVar);
			}
		}

		if (asyncDecodeMode != null) {
			if (isValueReference(asyncDecodeMode)) {
				ValueBinding vb = application.createValueBinding(asyncDecodeMode);
				component.setValueBinding(Properties.ASYNC_DECODE_MODE, vb);

			} else {
				component.setAsyncDecodeMode(asyncDecodeMode);
			}
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}
	}

	public void release() {
		textAlignment = null;
		verticalAlignment = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		asyncDecodeMode = null;
		loadListeners = null;

		super.release();
	}

}
