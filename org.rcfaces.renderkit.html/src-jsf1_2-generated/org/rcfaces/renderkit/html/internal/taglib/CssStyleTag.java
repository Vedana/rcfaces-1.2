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
	private ValueExpression userAgent;
	private ValueExpression src;
	private ValueExpression srcCharSet;
	private ValueExpression requiredModules;
	private ValueExpression requiredSets;
	private ValueExpression mergeStyles;
	public String getComponentType() {
		return CssStyleComponent.COMPONENT_TYPE;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setUserAgent(ValueExpression userAgent) {
		this.userAgent = userAgent;
	}

	public final void setSrc(ValueExpression src) {
		this.src = src;
	}

	public final void setSrcCharSet(ValueExpression srcCharSet) {
		this.srcCharSet = srcCharSet;
	}

	public final void setRequiredModules(ValueExpression requiredModules) {
		this.requiredModules = requiredModules;
	}

	public final void setRequiredSets(ValueExpression requiredSets) {
		this.requiredSets = requiredSets;
	}

	public final void setMergeStyles(ValueExpression mergeStyles) {
		this.mergeStyles = mergeStyles;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CssStyleComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  userAgent='"+userAgent+"'");
			LOG.debug("  src='"+src+"'");
			LOG.debug("  srcCharSet='"+srcCharSet+"'");
			LOG.debug("  requiredModules='"+requiredModules+"'");
			LOG.debug("  requiredSets='"+requiredSets+"'");
			LOG.debug("  mergeStyles='"+mergeStyles+"'");
		}
		if ((uiComponent instanceof CssStyleComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CssStyleComponent'.");
		}

		super.setProperties(uiComponent);

		CssStyleComponent component = (CssStyleComponent) uiComponent;
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

		if (mergeStyles != null) {
			if (mergeStyles.isLiteralText()==false) {
				component.setValueExpression(Properties.MERGE_STYLES, mergeStyles);

			} else {
				component.setMergeStyles(getBool(mergeStyles.getExpressionString()));
			}
		}
	}

	public void release() {
		text = null;
		userAgent = null;
		src = null;
		srcCharSet = null;
		requiredModules = null;
		requiredSets = null;
		mergeStyles = null;

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
