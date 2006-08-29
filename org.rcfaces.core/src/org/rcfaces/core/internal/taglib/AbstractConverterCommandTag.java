package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.component.AbstractConverterCommandComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractConverterCommandTag extends AbstractCommandTag {

private static final Log LOG=LogFactory.getLog(AbstractConverterCommandTag.class);
	private String converter;
	public final String getConverter() {
		return converter;
	}

	public final void setConverter(String converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractConverterCommandComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractConverterCommandComponent'.");
		}

		AbstractConverterCommandComponent component = (AbstractConverterCommandComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setConverter(vb);
			} else {
				component.setConverter(converter);
			}
		}
	}

	public void release() {
		converter = null;

		super.release();
	}

}
