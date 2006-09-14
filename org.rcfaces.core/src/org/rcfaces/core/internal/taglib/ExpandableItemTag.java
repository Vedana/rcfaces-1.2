package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ExpandableItemComponent;

public abstract class ExpandableItemTag extends UIImageItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ExpandableItemTag.class);

	private String backgroundColor;
	private String foregroundColor;
	private String text;
	private String expandedImageURL;
	public final String getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final String getForegroundColor() {
		return foregroundColor;
	}

	public final void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getExpandedImageURL() {
		return expandedImageURL;
	}

	public final void setExpandedImageURL(String expandedImageURL) {
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
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ExpandableItemComponent'.");
		}

		ExpandableItemComponent component = (ExpandableItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);

				component.setBackgroundColor(vb);
			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);

				component.setForegroundColor(vb);
			} else {
				component.setForegroundColor(foregroundColor);
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (expandedImageURL != null) {
			if (isValueReference(expandedImageURL)) {
				ValueBinding vb = application.createValueBinding(expandedImageURL);
				component.setExpandedImageURL(vb);
			} else {
				component.setExpandedImageURL(expandedImageURL);
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
