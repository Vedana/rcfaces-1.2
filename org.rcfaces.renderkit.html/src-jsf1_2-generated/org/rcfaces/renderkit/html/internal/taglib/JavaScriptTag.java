package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import javax.servlet.jsp.JspException;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.ITextCapability;
import javax.faces.component.UIViewRoot;
import org.rcfaces.renderkit.html.component.JavaScriptComponent;

public class JavaScriptTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptTag.class);

	private ValueExpression text;
	private ValueExpression src;
	private ValueExpression srcCharSet;
	private ValueExpression requiredFiles;
	private ValueExpression requiredClasses;
	public String getComponentType() {
		return JavaScriptComponent.COMPONENT_TYPE;
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

	public final void setRequiredFiles(ValueExpression requiredFiles) {
		this.requiredFiles = requiredFiles;
	}

	public final void setRequiredClasses(ValueExpression requiredClasses) {
		this.requiredClasses = requiredClasses;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (JavaScriptComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  src='"+src+"'");
			LOG.debug("  srcCharSet='"+srcCharSet+"'");
			LOG.debug("  requiredFiles='"+requiredFiles+"'");
			LOG.debug("  requiredClasses='"+requiredClasses+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof JavaScriptComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'JavaScriptComponent'.");
		}

		JavaScriptComponent component = (JavaScriptComponent) uiComponent;
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

		if (requiredFiles != null) {
			if (requiredFiles.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED_FILES, requiredFiles);

			} else {
				component.setRequiredFiles(requiredFiles.getExpressionString());
			}
		}

		if (requiredClasses != null) {
			if (requiredClasses.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED_CLASSES, requiredClasses);

			} else {
				component.setRequiredClasses(requiredClasses.getExpressionString());
			}
		}
	}

	public void release() {
		text = null;
		src = null;
		srcCharSet = null;
		requiredFiles = null;
		requiredClasses = null;

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
