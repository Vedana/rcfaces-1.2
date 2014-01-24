package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.el.ValueExpression;
import org.rcfaces.renderkit.html.component.JavaScriptComponent;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.renderkit.html.component.Properties;
import org.rcfaces.core.component.capability.ITextCapability;

public class JavaScriptTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptTag.class);

	private ValueExpression text;
	private ValueExpression userAgent;
	private ValueExpression src;
	private ValueExpression srcCharSet;
	private ValueExpression requiredFiles;
	private ValueExpression requiredClasses;
	private ValueExpression requiredModules;
	private ValueExpression requiredSets;
	public String getComponentType() {
		return JavaScriptComponent.COMPONENT_TYPE;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setUserAgent(ValueExpression userAgent) {
		this.userAgent = userAgent;
	}

	public void setSrc(ValueExpression src) {
		this.src = src;
	}

	public void setSrcCharSet(ValueExpression srcCharSet) {
		this.srcCharSet = srcCharSet;
	}

	public void setRequiredFiles(ValueExpression requiredFiles) {
		this.requiredFiles = requiredFiles;
	}

	public void setRequiredClasses(ValueExpression requiredClasses) {
		this.requiredClasses = requiredClasses;
	}

	public void setRequiredModules(ValueExpression requiredModules) {
		this.requiredModules = requiredModules;
	}

	public void setRequiredSets(ValueExpression requiredSets) {
		this.requiredSets = requiredSets;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (JavaScriptComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  userAgent='"+userAgent+"'");
			LOG.debug("  src='"+src+"'");
			LOG.debug("  srcCharSet='"+srcCharSet+"'");
			LOG.debug("  requiredFiles='"+requiredFiles+"'");
			LOG.debug("  requiredClasses='"+requiredClasses+"'");
			LOG.debug("  requiredModules='"+requiredModules+"'");
			LOG.debug("  requiredSets='"+requiredSets+"'");
		}
		if ((uiComponent instanceof JavaScriptComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'JavaScriptComponent'.");
		}

		super.setProperties(uiComponent);

		JavaScriptComponent component = (JavaScriptComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (userAgent != null) {
			if (userAgent.isLiteralText()==false) {
				component.setValueExpression(Properties.USER_AGENT, userAgent);

			} else {
				component.setUserAgent(userAgent.getExpressionString());
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

		if (requiredModules != null) {
			if (requiredModules.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED_MODULES, requiredModules);

			} else {
				component.setRequiredModules(requiredModules.getExpressionString());
			}
		}

		if (requiredSets != null) {
			if (requiredSets.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED_SETS, requiredSets);

			} else {
				component.setRequiredSets(requiredSets.getExpressionString());
			}
		}
	}

	public void release() {
		text = null;
		userAgent = null;
		src = null;
		srcCharSet = null;
		requiredFiles = null;
		requiredClasses = null;
		requiredModules = null;
		requiredSets = null;

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
