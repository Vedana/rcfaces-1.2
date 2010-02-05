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

	private ValueExpression charSet;
	private ValueExpression src;
	public final void setCharSet(ValueExpression charSet) {
		this.charSet = charSet;
	}

	public final void setSrc(ValueExpression src) {
		this.src = src;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  charSet='"+charSet+"'");
			LOG.debug("  src='"+src+"'");
		}
		if ((uiComponent instanceof FileItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FileItemComponent'.");
		}

		super.setProperties(uiComponent);

		FileItemComponent component = (FileItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (charSet != null) {
			if (charSet.isLiteralText()==false) {
				component.setValueExpression(Properties.CHAR_SET, charSet);

			} else {
				component.setCharSet(charSet.getExpressionString());
			}
		}

		if (src != null) {
			if (src.isLiteralText()==false) {
				component.setValueExpression(Properties.SRC, src);

			} else {
				component.setSrc(src.getExpressionString());
			}
		}
	}

	public void release() {
		charSet = null;
		src = null;

		super.release();
	}

}
