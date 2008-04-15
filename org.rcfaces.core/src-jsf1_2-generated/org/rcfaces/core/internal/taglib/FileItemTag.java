package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.FileItemComponent;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class FileItemTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FileItemTag.class);

	private ValueExpression src;
	public final void setSrc(ValueExpression src) {
		this.src = src;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
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

		if (src != null) {
			if (src.isLiteralText()==false) {
				component.setValueExpression(Properties.SRC, src);

			} else {
				component.setSrc(src.getExpressionString());
			}
		}
	}

	public void release() {
		src = null;

		super.release();
	}

}
