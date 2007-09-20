package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.core.component.AbstractConverterCommandComponent;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractConverterCommandTag extends AbstractCommandTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractConverterCommandTag.class);

	private String converter;
	public final String getConverter() {
		return converter;
	}

	public final void setConverter(String converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  converter='"+converter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractConverterCommandComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractConverterCommandComponent'.");
		}

		AbstractConverterCommandComponent component = (AbstractConverterCommandComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setValueBinding(Properties.CONVERTER, vb);

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
