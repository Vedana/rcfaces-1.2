package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.html.component.NameSpaceComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class NameSpaceTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NameSpaceTag.class);

	private String uri;
	private String prefix;
	public String getComponentType() {
		return NameSpaceComponent.COMPONENT_TYPE;
	}

	public final void setUri(String uri) {
		this.uri = uri;
	}

	public final void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (NameSpaceComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  uri='"+uri+"'");
			LOG.debug("  prefix='"+prefix+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof NameSpaceComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NameSpaceComponent'.");
		}

		NameSpaceComponent component = (NameSpaceComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (uri != null) {
			if (isValueReference(uri)) {
				ValueBinding vb = application.createValueBinding(uri);
				component.setValueBinding(Properties.URI, vb);

			} else {
				component.setUri(uri);
			}
		}

		if (prefix != null) {
			if (isValueReference(prefix)) {
				ValueBinding vb = application.createValueBinding(prefix);
				component.setValueBinding(Properties.PREFIX, vb);

			} else {
				component.setPrefix(prefix);
			}
		}
	}

	public void release() {
		uri = null;
		prefix = null;

		super.release();
	}

}
