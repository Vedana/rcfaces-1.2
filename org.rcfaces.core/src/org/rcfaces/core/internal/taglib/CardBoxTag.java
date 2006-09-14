package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CardBoxComponent;

public class CardBoxTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardBoxTag.class);

	private String selectionListeners;
	private String asyncRenderMode;
	private String preference;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardBoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  preference='"+preference+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CardBoxComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardBoxComponent'.");
		}

		CardBoxComponent component = (CardBoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
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
	}

	public void release() {
		selectionListeners = null;
		asyncRenderMode = null;
		preference = null;

		super.release();
	}

}
