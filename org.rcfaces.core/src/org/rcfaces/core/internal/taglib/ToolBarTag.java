package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.internal.tools.ListenersTools;

public class ToolBarTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolBarTag.class);

	private String initListeners;
	private String verticalAlignment;
	private String separatorImageURL;
	private String separatorImageWidth;
	private String separatorImageHeight;
	private String controlImageURL;
	private String controlImageWidth;
	private String controlImageHeight;
	private String itemPadding;
	public String getComponentType() {
		return ToolBarComponent.COMPONENT_TYPE;
	}

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	public final String getVerticalAlignment() {
		return verticalAlignment;
	}

	public final void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final String getSeparatorImageURL() {
		return separatorImageURL;
	}

	public final void setSeparatorImageURL(String separatorImageURL) {
		this.separatorImageURL = separatorImageURL;
	}

	public final String getSeparatorImageWidth() {
		return separatorImageWidth;
	}

	public final void setSeparatorImageWidth(String separatorImageWidth) {
		this.separatorImageWidth = separatorImageWidth;
	}

	public final String getSeparatorImageHeight() {
		return separatorImageHeight;
	}

	public final void setSeparatorImageHeight(String separatorImageHeight) {
		this.separatorImageHeight = separatorImageHeight;
	}

	public final String getControlImageURL() {
		return controlImageURL;
	}

	public final void setControlImageURL(String controlImageURL) {
		this.controlImageURL = controlImageURL;
	}

	public final String getControlImageWidth() {
		return controlImageWidth;
	}

	public final void setControlImageWidth(String controlImageWidth) {
		this.controlImageWidth = controlImageWidth;
	}

	public final String getControlImageHeight() {
		return controlImageHeight;
	}

	public final void setControlImageHeight(String controlImageHeight) {
		this.controlImageHeight = controlImageHeight;
	}

	public final String getItemPadding() {
		return itemPadding;
	}

	public final void setItemPadding(String itemPadding) {
		this.itemPadding = itemPadding;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  separatorImageURL='"+separatorImageURL+"'");
			LOG.debug("  separatorImageWidth='"+separatorImageWidth+"'");
			LOG.debug("  separatorImageHeight='"+separatorImageHeight+"'");
			LOG.debug("  controlImageURL='"+controlImageURL+"'");
			LOG.debug("  controlImageWidth='"+controlImageWidth+"'");
			LOG.debug("  controlImageHeight='"+controlImageHeight+"'");
			LOG.debug("  itemPadding='"+itemPadding+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolBarComponent'.");
		}

		ToolBarComponent component = (ToolBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (initListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (verticalAlignment != null) {
			if (isValueReference(verticalAlignment)) {
				ValueBinding vb = application.createValueBinding(verticalAlignment);

				component.setVerticalAlignment(vb);
			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (separatorImageURL != null) {
			if (isValueReference(separatorImageURL)) {
				ValueBinding vb = application.createValueBinding(separatorImageURL);
				component.setSeparatorImageURL(vb);
			} else {
				component.setSeparatorImageURL(separatorImageURL);
			}
		}

		if (separatorImageWidth != null) {
			if (isValueReference(separatorImageWidth)) {
				ValueBinding vb = application.createValueBinding(separatorImageWidth);
				component.setSeparatorImageWidth(vb);
			} else {
				component.setSeparatorImageWidth(getInt(separatorImageWidth));
			}
		}

		if (separatorImageHeight != null) {
			if (isValueReference(separatorImageHeight)) {
				ValueBinding vb = application.createValueBinding(separatorImageHeight);
				component.setSeparatorImageHeight(vb);
			} else {
				component.setSeparatorImageHeight(getInt(separatorImageHeight));
			}
		}

		if (controlImageURL != null) {
			if (isValueReference(controlImageURL)) {
				ValueBinding vb = application.createValueBinding(controlImageURL);
				component.setControlImageURL(vb);
			} else {
				component.setControlImageURL(controlImageURL);
			}
		}

		if (controlImageWidth != null) {
			if (isValueReference(controlImageWidth)) {
				ValueBinding vb = application.createValueBinding(controlImageWidth);
				component.setControlImageWidth(vb);
			} else {
				component.setControlImageWidth(getInt(controlImageWidth));
			}
		}

		if (controlImageHeight != null) {
			if (isValueReference(controlImageHeight)) {
				ValueBinding vb = application.createValueBinding(controlImageHeight);
				component.setControlImageHeight(vb);
			} else {
				component.setControlImageHeight(getInt(controlImageHeight));
			}
		}

		if (itemPadding != null) {
			if (isValueReference(itemPadding)) {
				ValueBinding vb = application.createValueBinding(itemPadding);
				component.setItemPadding(vb);
			} else {
				component.setItemPadding(getInt(itemPadding));
			}
		}
	}

	public void release() {
		initListeners = null;
		verticalAlignment = null;
		separatorImageURL = null;
		separatorImageWidth = null;
		separatorImageHeight = null;
		controlImageURL = null;
		controlImageWidth = null;
		controlImageHeight = null;
		itemPadding = null;

		super.release();
	}

}
