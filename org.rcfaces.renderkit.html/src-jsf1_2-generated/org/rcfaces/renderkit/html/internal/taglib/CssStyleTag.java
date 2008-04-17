package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.capability.ITextCapability;

public class CssStyleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CssStyleTag.class);

	private ValueExpression text;
	private ValueExpression src;
	private ValueExpression srcCharSet;
	public String getComponentType() {
		return CssStyleComponent.COMPONENT_TYPE;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setSrc(ValueExpression src) {
		this.src = src;
	}

	public final void setSrcCharSet(ValueExpression srcCharSet) {
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

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (src != null) {
			if (src.isLiteralText()==false) {
				component.setValueExpression(Properties.SRC, src);

			} else {
				component.setSrc(src.getExpressionString());
			}
		}

		if (srcCharSet != null) {
			if (srcCharSet.isLiteralText()==false) {
				component.setValueExpression(Properties.SRC_CHAR_SET, srcCharSet);

			} else {
				component.setSrcCharSet(srcCharSet.getExpressionString());
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
