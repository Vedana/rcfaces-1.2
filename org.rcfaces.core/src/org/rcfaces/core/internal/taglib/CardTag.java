package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.CardComponent;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CardTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardTag.class);

	private String textAlignment;
	private String verticalAlignment;
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
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CardComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardComponent'.");
		}

		CardComponent component = (CardComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (textAlignment != null) {
			if (isValueReference(textAlignment)) {
				ValueBinding vb = application.createValueBinding(textAlignment);

				component.setTextAlignment(vb);
			} else {
				component.setTextAlignment(textAlignment);
			}
		}

		if (verticalAlignment != null) {
			if (isValueReference(verticalAlignment)) {
				ValueBinding vb = application.createValueBinding(verticalAlignment);

				component.setVerticalAlignment(vb);
			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (loadListeners != null) {
			parseActionListener(application, component, LOAD_LISTENER_TYPE, loadListeners);
		}
	}

	public void release() {
		textAlignment = null;
		verticalAlignment = null;
		loadListeners = null;

		super.release();
	}

}
