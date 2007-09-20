package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolBarComponent;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ToolBarTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolBarTag.class);

	private String initListeners;
	private String verticalAlignment;
	private String borderType;
	private String separatorImageURL;
	private String separatorImageWidth;
	private String separatorImageHeight;
	private String separatorAlternateText;
	private String controlImageURL;
	private String controlImageWidth;
	private String controlImageHeight;
	private String controlAlternateText;
	private String itemPadding;
	private String locked;
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

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
	}

	public final void setSeparatorImageURL(String separatorImageURL) {
		this.separatorImageURL = separatorImageURL;
	}

	public final void setSeparatorImageWidth(String separatorImageWidth) {
		this.separatorImageWidth = separatorImageWidth;
	}

	public final void setSeparatorImageHeight(String separatorImageHeight) {
		this.separatorImageHeight = separatorImageHeight;
	}

	public final void setSeparatorAlternateText(String separatorAlternateText) {
		this.separatorAlternateText = separatorAlternateText;
	}

	public final void setControlImageURL(String controlImageURL) {
		this.controlImageURL = controlImageURL;
	}

	public final void setControlImageWidth(String controlImageWidth) {
		this.controlImageWidth = controlImageWidth;
	}

	public final void setControlImageHeight(String controlImageHeight) {
		this.controlImageHeight = controlImageHeight;
	}

	public final void setControlAlternateText(String controlAlternateText) {
		this.controlAlternateText = controlAlternateText;
	}

	public final void setItemPadding(String itemPadding) {
		this.itemPadding = itemPadding;
	}

	public final void setLocked(String locked) {
		this.locked = locked;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  separatorImageURL='"+separatorImageURL+"'");
			LOG.debug("  separatorImageWidth='"+separatorImageWidth+"'");
			LOG.debug("  separatorImageHeight='"+separatorImageHeight+"'");
			LOG.debug("  separatorAlternateText='"+separatorAlternateText+"'");
			LOG.debug("  controlImageURL='"+controlImageURL+"'");
			LOG.debug("  controlImageWidth='"+controlImageWidth+"'");
			LOG.debug("  controlImageHeight='"+controlImageHeight+"'");
			LOG.debug("  controlAlternateText='"+controlAlternateText+"'");
			LOG.debug("  itemPadding='"+itemPadding+"'");
			LOG.debug("  locked='"+locked+"'");
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
				component.setValueBinding(Properties.VERTICAL_ALIGNMENT, vb);

			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);
				component.setValueBinding(Properties.BORDER_TYPE, vb);

			} else {
				component.setBorderType(borderType);
			}
		}

		if (separatorImageURL != null) {
			if (isValueReference(separatorImageURL)) {
				ValueBinding vb = application.createValueBinding(separatorImageURL);
				component.setValueBinding(Properties.SEPARATOR_IMAGE_URL, vb);

			} else {
				component.setSeparatorImageURL(separatorImageURL);
			}
		}

		if (separatorImageWidth != null) {
			if (isValueReference(separatorImageWidth)) {
				ValueBinding vb = application.createValueBinding(separatorImageWidth);
				component.setValueBinding(Properties.SEPARATOR_IMAGE_WIDTH, vb);

			} else {
				component.setSeparatorImageWidth(getInt(separatorImageWidth));
			}
		}

		if (separatorImageHeight != null) {
			if (isValueReference(separatorImageHeight)) {
				ValueBinding vb = application.createValueBinding(separatorImageHeight);
				component.setValueBinding(Properties.SEPARATOR_IMAGE_HEIGHT, vb);

			} else {
				component.setSeparatorImageHeight(getInt(separatorImageHeight));
			}
		}

		if (separatorAlternateText != null) {
			if (isValueReference(separatorAlternateText)) {
				ValueBinding vb = application.createValueBinding(separatorAlternateText);
				component.setValueBinding(Properties.SEPARATOR_ALTERNATE_TEXT, vb);

			} else {
				component.setSeparatorAlternateText(separatorAlternateText);
			}
		}

		if (controlImageURL != null) {
			if (isValueReference(controlImageURL)) {
				ValueBinding vb = application.createValueBinding(controlImageURL);
				component.setValueBinding(Properties.CONTROL_IMAGE_URL, vb);

			} else {
				component.setControlImageURL(controlImageURL);
			}
		}

		if (controlImageWidth != null) {
			if (isValueReference(controlImageWidth)) {
				ValueBinding vb = application.createValueBinding(controlImageWidth);
				component.setValueBinding(Properties.CONTROL_IMAGE_WIDTH, vb);

			} else {
				component.setControlImageWidth(getInt(controlImageWidth));
			}
		}

		if (controlImageHeight != null) {
			if (isValueReference(controlImageHeight)) {
				ValueBinding vb = application.createValueBinding(controlImageHeight);
				component.setValueBinding(Properties.CONTROL_IMAGE_HEIGHT, vb);

			} else {
				component.setControlImageHeight(getInt(controlImageHeight));
			}
		}

		if (controlAlternateText != null) {
			if (isValueReference(controlAlternateText)) {
				ValueBinding vb = application.createValueBinding(controlAlternateText);
				component.setValueBinding(Properties.CONTROL_ALTERNATE_TEXT, vb);

			} else {
				component.setControlAlternateText(controlAlternateText);
			}
		}

		if (itemPadding != null) {
			if (isValueReference(itemPadding)) {
				ValueBinding vb = application.createValueBinding(itemPadding);
				component.setValueBinding(Properties.ITEM_PADDING, vb);

			} else {
				component.setItemPadding(getInt(itemPadding));
			}
		}

		if (locked != null) {
			if (isValueReference(locked)) {
				ValueBinding vb = application.createValueBinding(locked);
				component.setValueBinding(Properties.LOCKED, vb);

			} else {
				component.setLocked(getBool(locked));
			}
		}
	}

	public void release() {
		initListeners = null;
		verticalAlignment = null;
		borderType = null;
		separatorImageURL = null;
		separatorImageWidth = null;
		separatorImageHeight = null;
		separatorAlternateText = null;
		controlImageURL = null;
		controlImageWidth = null;
		controlImageHeight = null;
		controlAlternateText = null;
		itemPadding = null;
		locked = null;

		super.release();
	}

}
