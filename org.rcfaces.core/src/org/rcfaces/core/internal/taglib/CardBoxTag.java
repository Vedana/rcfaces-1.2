package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CardBoxTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardBoxTag.class);

	private String selectionListeners;
	private String asyncRenderMode;
	private String preference;
	private String scopeValue;
	private String scopeVar;
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

	public final String getPreference() {
		return preference;
	}

	public final void setPreference(String preference) {
		this.preference = preference;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardBoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  preference='"+preference+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CardBoxComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardBoxComponent'.");
		}

		CardBoxComponent component = (CardBoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (asyncRenderMode != null) {
			if (isValueReference(asyncRenderMode)) {
				ValueBinding vb = application.createValueBinding(asyncRenderMode);

				component.setAsyncRenderMode(vb);
			} else {
				component.setAsyncRenderMode(asyncRenderMode);
			}
		}

		if (preference != null) {
				ValueBinding vb = application.createValueBinding(preference);

				component.setPreference(vb);
		}

		if (scopeValue != null) {
				ValueBinding vb = application.createValueBinding(scopeValue);

				component.setScopeValue(vb);
		}

		if (scopeVar != null) {
			if (isValueReference(scopeVar)) {
				ValueBinding vb = application.createValueBinding(scopeVar);

				component.setScopeVar(vb);
			} else {
				component.setScopeVar(scopeVar);
			}
		}
	}

	public void release() {
		selectionListeners = null;
		asyncRenderMode = null;
		preference = null;
		scopeValue = null;
		scopeVar = null;

		super.release();
	}

}
