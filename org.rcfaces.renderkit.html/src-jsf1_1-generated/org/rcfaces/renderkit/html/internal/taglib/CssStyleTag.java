package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import javax.servlet.jsp.JspException;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.ITextCapability;
import javax.faces.component.UIViewRoot;

public class CssStyleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CssStyleTag.class);

	private String text;
	private String userAgent;
	private String src;
	private String srcCharSet;
	private String requiredModules;
	private String requiredSets;
	private String mergeStyles;
	public String getComponentType() {
		return CssStyleComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getUserAgent() {
		return userAgent;
	}

	public final void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	public final void setSrcCharSet(String srcCharSet) {
		this.srcCharSet = srcCharSet;
	}

	public final void setRequiredModules(String requiredModules) {
		this.requiredModules = requiredModules;
	}

	public final void setRequiredSets(String requiredSets) {
		this.requiredSets = requiredSets;
	}

	public final void setMergeStyles(String mergeStyles) {
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
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
			}
		}

		if (userAgent != null) {
			if (isValueReference(userAgent)) {
				ValueBinding vb = application.createValueBinding(userAgent);
				component.setValueBinding(Properties.USER_AGENT, vb);

			} else {
				component.setUserAgent(userAgent);
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

		if (srcCharSet != null) {
			if (isValueReference(srcCharSet)) {
				ValueBinding vb = application.createValueBinding(srcCharSet);
				component.setValueBinding(Properties.SRC_CHAR_SET, vb);

			} else {
				component.setSrcCharSet(srcCharSet);
			}
		}

		if (requiredModules != null) {
			if (isValueReference(requiredModules)) {
				ValueBinding vb = application.createValueBinding(requiredModules);
				component.setValueBinding(Properties.REQUIRED_MODULES, vb);

			} else {
				component.setRequiredModules(requiredModules);
			}
		}

		if (requiredSets != null) {
			if (isValueReference(requiredSets)) {
				ValueBinding vb = application.createValueBinding(requiredSets);
				component.setValueBinding(Properties.REQUIRED_SETS, vb);

			} else {
				component.setRequiredSets(requiredSets);
			}
		}

		if (mergeStyles != null) {
			if (isValueReference(mergeStyles)) {
				ValueBinding vb = application.createValueBinding(mergeStyles);
				component.setValueBinding(Properties.MERGE_STYLES, vb);

			} else {
				component.setMergeStyles(getBool(mergeStyles));
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
