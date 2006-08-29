package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.CardComponent;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CardTag extends AbstractOutputTag {

private static final Log LOG=LogFactory.getLog(CardTag.class);
	private String loadListeners;
	public String getComponentType() {
		return CardComponent.COMPONENT_TYPE;
	}

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CardComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardComponent'.");
		}

		CardComponent component = (CardComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (loadListeners != null) {
			parseActionListener(application, component, LOAD_LISTENER_TYPE, loadListeners);
		}
	}

	public void release() {
		loadListeners = null;

		super.release();
	}

}
