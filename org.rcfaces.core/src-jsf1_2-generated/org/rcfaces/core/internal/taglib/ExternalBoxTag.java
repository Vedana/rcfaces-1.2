package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ExternalBoxComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ExternalBoxTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ExternalBoxTag.class);

	private ValueExpression loadListeners;
	private ValueExpression overStyleClass;
	private ValueExpression scrolling;
	private ValueExpression contentURL;
	public String getComponentType() {
		return ExternalBoxComponent.COMPONENT_TYPE;
	}

	public final void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final void setOverStyleClass(ValueExpression overStyleClass) {
		this.overStyleClass = overStyleClass;
	}

	public final void setScrolling(ValueExpression scrolling) {
		this.scrolling = scrolling;
	}

	public final void setContentURL(ValueExpression contentURL) {
		this.contentURL = contentURL;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ExternalBoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  overStyleClass='"+overStyleClass+"'");
			LOG.debug("  scrolling='"+scrolling+"'");
			LOG.debug("  contentURL='"+contentURL+"'");
		}
		if ((uiComponent instanceof ExternalBoxComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ExternalBoxComponent'.");
		}

		super.setProperties(uiComponent);

		ExternalBoxComponent component = (ExternalBoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (loadListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (overStyleClass != null) {
			if (overStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.OVER_STYLE_CLASS, overStyleClass);

			} else {
				component.setOverStyleClass(overStyleClass.getExpressionString());
			}
		}

		if (scrolling != null) {
			if (scrolling.isLiteralText()==false) {
				component.setValueExpression(Properties.SCROLLING, scrolling);

			} else {
				component.setScrolling(scrolling.getExpressionString());
			}
		}

		if (contentURL != null) {
			if (contentURL.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, contentURL);

			} else {
				component.setContentURL(contentURL.getExpressionString());
			}
		}
	}

	public void release() {
		loadListeners = null;
		overStyleClass = null;
		scrolling = null;
		contentURL = null;

		super.release();
	}

}
