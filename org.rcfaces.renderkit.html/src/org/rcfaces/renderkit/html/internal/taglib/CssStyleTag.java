package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import javax.servlet.jsp.JspException;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.ITextCapability;
import javax.faces.component.UIViewRoot;

public class CssStyleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CssStyleTag.class);

	private String text;
	private String src;
	private String srcCharSet;
	public String getComponentType() {
		return CssStyleComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getSrc() {
		return src;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	public final String getSrcCharSet() {
		return srcCharSet;
	}

	public final void setSrcCharSet(String srcCharSet) {
		this.srcCharSet = srcCharSet;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CssStyleComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  src='"+src+"'");
			LOG.debug("  srcCharSet='"+srcCharSet+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CssStyleComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CssStyleComponent'.");
		}

		CssStyleComponent component = (CssStyleComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (src != null) {
			if (isValueReference(src)) {
				ValueBinding vb = application.createValueBinding(src);
				component.setSrc(vb);
			} else {
				component.setSrc(src);
			}
		}

		if (srcCharSet != null) {
			if (isValueReference(srcCharSet)) {
				ValueBinding vb = application.createValueBinding(srcCharSet);
				component.setSrcCharSet(vb);
			} else {
				component.setSrcCharSet(srcCharSet);
			}
		}
	}

	public void release() {
		text = null;
		src = null;
		srcCharSet = null;

		super.release();
	}

	protected int getDoStartValue() {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		if (text == null && getBodyContent() != null) {
			String content = getBodyContent().getString();
			if (content != null && content.length() > 0) {
				content = content.trim();
				if (content.length() > 0) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("  [body of tag] text='"+content+"'");
					}
					((ITextCapability)getComponentInstance()).setText(content);
				}
			}
		}
		return super.doEndTag();
	}
}
