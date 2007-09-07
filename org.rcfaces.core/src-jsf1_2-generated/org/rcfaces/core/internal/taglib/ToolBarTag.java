package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ToolBarTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolBarTag.class);

	private ValueExpression initListeners;
	private ValueExpression verticalAlignment;
	private ValueExpression borderType;
	private ValueExpression separatorImageURL;
	private ValueExpression separatorImageWidth;
	private ValueExpression separatorImageHeight;
	private ValueExpression separatorAlternateText;
	private ValueExpression controlImageURL;
	private ValueExpression controlImageWidth;
	private ValueExpression controlImageHeight;
	private ValueExpression controlAlternateText;
	private ValueExpression itemPadding;
	private ValueExpression locked;
	public String getComponentType() {
		return ToolBarComponent.COMPONENT_TYPE;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public final void setSeparatorImageURL(ValueExpression separatorImageURL) {
		this.separatorImageURL = separatorImageURL;
	}

	public final void setSeparatorImageWidth(ValueExpression separatorImageWidth) {
		this.separatorImageWidth = separatorImageWidth;
	}

	public final void setSeparatorImageHeight(ValueExpression separatorImageHeight) {
		this.separatorImageHeight = separatorImageHeight;
	}

	public final void setSeparatorAlternateText(ValueExpression separatorAlternateText) {
		this.separatorAlternateText = separatorAlternateText;
	}

	public final void setControlImageURL(ValueExpression controlImageURL) {
		this.controlImageURL = controlImageURL;
	}

	public final void setControlImageWidth(ValueExpression controlImageWidth) {
		this.controlImageWidth = controlImageWidth;
	}

	public final void setControlImageHeight(ValueExpression controlImageHeight) {
		this.controlImageHeight = controlImageHeight;
	}

	public final void setControlAlternateText(ValueExpression controlAlternateText) {
		this.controlAlternateText = controlAlternateText;
	}

	public final void setItemPadding(ValueExpression itemPadding) {
		this.itemPadding = itemPadding;
	}

	public final void setLocked(ValueExpression locked) {
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

		if (initListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

		if (separatorImageURL != null) {
			if (separatorImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.SEPARATOR_IMAGE_URL, separatorImageURL);

			} else {
				component.setSeparatorImageURL(separatorImageURL.getExpressionString());
			}
		}

		if (separatorImageWidth != null) {
			if (separatorImageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.SEPARATOR_IMAGE_WIDTH, separatorImageWidth);

			} else {
				component.setSeparatorImageWidth(getInt(separatorImageWidth.getExpressionString()));
			}
		}

		if (separatorImageHeight != null) {
			if (separatorImageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.SEPARATOR_IMAGE_HEIGHT, separatorImageHeight);

			} else {
				component.setSeparatorImageHeight(getInt(separatorImageHeight.getExpressionString()));
			}
		}

		if (separatorAlternateText != null) {
			if (separatorAlternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.SEPARATOR_ALTERNATE_TEXT, separatorAlternateText);

			} else {
				component.setSeparatorAlternateText(separatorAlternateText.getExpressionString());
			}
		}

		if (controlImageURL != null) {
			if (controlImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.CONTROL_IMAGE_URL, controlImageURL);

			} else {
				component.setControlImageURL(controlImageURL.getExpressionString());
			}
		}

		if (controlImageWidth != null) {
			if (controlImageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.CONTROL_IMAGE_WIDTH, controlImageWidth);

			} else {
				component.setControlImageWidth(getInt(controlImageWidth.getExpressionString()));
			}
		}

		if (controlImageHeight != null) {
			if (controlImageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.CONTROL_IMAGE_HEIGHT, controlImageHeight);

			} else {
				component.setControlImageHeight(getInt(controlImageHeight.getExpressionString()));
			}
		}

		if (controlAlternateText != null) {
			if (controlAlternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.CONTROL_ALTERNATE_TEXT, controlAlternateText);

			} else {
				component.setControlAlternateText(controlAlternateText.getExpressionString());
			}
		}

		if (itemPadding != null) {
			if (itemPadding.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_PADDING, itemPadding);

			} else {
				component.setItemPadding(getInt(itemPadding.getExpressionString()));
			}
		}

		if (locked != null) {
			if (locked.isLiteralText()==false) {
				component.setValueExpression(Properties.LOCKED, locked);

			} else {
				component.setLocked(getBool(locked.getExpressionString()));
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
