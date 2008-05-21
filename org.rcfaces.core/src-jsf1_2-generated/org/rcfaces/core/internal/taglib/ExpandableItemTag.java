package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ExpandableItemComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class ExpandableItemTag extends UIImageItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ExpandableItemTag.class);

	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression text;
	private ValueExpression expandedImageURL;
	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setExpandedImageURL(ValueExpression expandedImageURL) {
		this.expandedImageURL = expandedImageURL;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  expandedImageURL='"+expandedImageURL+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ExpandableItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ExpandableItemComponent'.");
		}

		ExpandableItemComponent component = (ExpandableItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (backgroundColor != null) {
			if (backgroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_COLOR, backgroundColor);

			} else {
				component.setBackgroundColor(backgroundColor.getExpressionString());
			}
		}

		if (foregroundColor != null) {
			if (foregroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.FOREGROUND_COLOR, foregroundColor);

			} else {
				component.setForegroundColor(foregroundColor.getExpressionString());
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (expandedImageURL != null) {
			if (expandedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.EXPANDED_IMAGE_URL, expandedImageURL);

			} else {
				component.setExpandedImageURL(expandedImageURL.getExpressionString());
			}
		}
	}

	public void release() {
		backgroundColor = null;
		foregroundColor = null;
		text = null;
		expandedImageURL = null;

		super.release();
	}

}
