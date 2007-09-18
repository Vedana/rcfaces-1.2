package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import org.rcfaces.renderkit.html.component.JavaScriptComponent;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.capability.ITextCapability;

public class JavaScriptTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptTag.class);

	private String text;
	private String src;
	private String srcCharSet;
	private String requiredFiles;
	private String requiredClasses;
	public String getComponentType() {
		return JavaScriptComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	public final void setSrcCharSet(String srcCharSet) {
		this.srcCharSet = srcCharSet;
	}

	public final void setRequiredFiles(String requiredFiles) {
		this.requiredFiles = requiredFiles;
	}

	public final void setRequiredClasses(String requiredClasses) {
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
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
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

		if (requiredFiles != null) {
			if (isValueReference(requiredFiles)) {
				ValueBinding vb = application.createValueBinding(requiredFiles);
				component.setValueBinding(Properties.REQUIRED_FILES, vb);

			} else {
				component.setRequiredFiles(requiredFiles);
			}
		}

		if (requiredClasses != null) {
			if (isValueReference(requiredClasses)) {
				ValueBinding vb = application.createValueBinding(requiredClasses);
				component.setValueBinding(Properties.REQUIRED_CLASSES, vb);

			} else {
				component.setRequiredClasses(requiredClasses);
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
