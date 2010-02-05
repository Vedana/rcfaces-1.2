package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CardBoxTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardBoxTag.class);

	private String selectionListeners;
	private String asyncRenderMode;
	private String preferences;
	private String scopeSaveValue;
	private String scopeValue;
	private String scopeVar;
	private String asyncDecodeMode;
	public String getComponentType() {
		return CardBoxComponent.COMPONENT_TYPE;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getAsyncRenderMode() {
		return asyncRenderMode;
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public final String getPreferences() {
		return preferences;
	}

	public final void setPreferences(String preferences) {
		this.preferences = preferences;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardBoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  preferences='"+preferences+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  asyncDecodeMode='"+asyncDecodeMode+"'");
		}
		if ((uiComponent instanceof CardBoxComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardBoxComponent'.");
		}

		super.setProperties(uiComponent);

		CardBoxComponent component = (CardBoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (asyncRenderMode != null) {
			if (isValueReference(asyncRenderMode)) {
				ValueBinding vb = application.createValueBinding(asyncRenderMode);
				component.setValueBinding(Properties.ASYNC_RENDER_MODE, vb);

			} else {
				component.setAsyncRenderMode(asyncRenderMode);
			}
		}

		if (preferences != null) {
				ValueBinding vb = application.createValueBinding(preferences);
				component.setValueBinding(Properties.PREFERENCES, vb);
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
	}

	public void release() {
		selectionListeners = null;
		asyncRenderMode = null;
		preferences = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		asyncDecodeMode = null;

		super.release();
	}

}
