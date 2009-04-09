package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.FileItemComponent;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class FileItemTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FileItemTag.class);

	private String charSet;
	private String src;
	public final void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public final String getSrc() {
		return src;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  charSet='"+charSet+"'");
			LOG.debug("  src='"+src+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof FileItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FileItemComponent'.");
		}

		FileItemComponent component = (FileItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (charSet != null) {
			if (isValueReference(charSet)) {
				ValueBinding vb = application.createValueBinding(charSet);
				component.setValueBinding(Properties.CHAR_SET, vb);

			} else {
				component.setCharSet(charSet);
			}
		}

		if (src != null) {
			if (isValueReference(src)) {
				ValueBinding vb = application.createValueBinding(src);
				component.setValueBinding(Properties.SRC, vb);

			} else {
				component.setSrc(src);
			}
		}
	}

	public void release() {
		charSet = null;
		src = null;

		super.release();
	}

}
