package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.RulerComponent;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class RulerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(RulerTag.class);

	private String x;
	private String y;
	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String height;
	private String width;
	private String hiddenMode;
	private String visible;
	private String lookId;
	private String orientation;
	private String backgroundColor;
	private String foregroundColor;
	private String alignment;
	private String rendered;
	private String margins;
	public String getComponentType() {
		return RulerComponent.COMPONENT_TYPE;
	}

	public final String getX() {
		return x;
	}

	public final void setX(String x) {
		this.x = x;
	}

	public final String getY() {
		return y;
	}

	public final void setY(String y) {
		this.y = y;
	}

	public final String getMarginBottom() {
		return marginBottom;
	}

	public final void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public final String getMarginLeft() {
		return marginLeft;
	}

	public final void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public final String getMarginRight() {
		return marginRight;
	}

	public final void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public final String getMarginTop() {
		return marginTop;
	}

	public final void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public final String getHeight() {
		return height;
	}

	public final void setHeight(String height) {
		this.height = height;
	}

	public final String getWidth() {
		return width;
	}

	public final void setWidth(String width) {
		this.width = width;
	}

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
	}

	public final String getOrientation() {
		return orientation;
	}

	public final void setOrientation(String orientation) {
		this.orientation = orientation;
	}

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

	public final String getAlignment() {
		return alignment;
	}

	public final void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public final String getRendered() {
		return rendered;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	public final String getMargins() {
		return margins;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (RulerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  orientation='"+orientation+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  alignment='"+alignment+"'");
			LOG.debug("  rendered='"+rendered+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof RulerComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'RulerComponent'.");
		}

		RulerComponent component = (RulerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (x != null) {
			if (isValueReference(x)) {
				ValueBinding vb = application.createValueBinding(x);

				component.setX(vb);
			} else {
				component.setX(x);
			}
		}

		if (y != null) {
			if (isValueReference(y)) {
				ValueBinding vb = application.createValueBinding(y);

				component.setY(vb);
			} else {
				component.setY(y);
			}
		}

		if (marginBottom != null) {
			if (isValueReference(marginBottom)) {
				ValueBinding vb = application.createValueBinding(marginBottom);

				component.setMarginBottom(vb);
			} else {
				component.setMarginBottom(marginBottom);
			}
		}

		if (marginLeft != null) {
			if (isValueReference(marginLeft)) {
				ValueBinding vb = application.createValueBinding(marginLeft);

				component.setMarginLeft(vb);
			} else {
				component.setMarginLeft(marginLeft);
			}
		}

		if (marginRight != null) {
			if (isValueReference(marginRight)) {
				ValueBinding vb = application.createValueBinding(marginRight);

				component.setMarginRight(vb);
			} else {
				component.setMarginRight(marginRight);
			}
		}

		if (marginTop != null) {
			if (isValueReference(marginTop)) {
				ValueBinding vb = application.createValueBinding(marginTop);

				component.setMarginTop(vb);
			} else {
				component.setMarginTop(marginTop);
			}
		}

		if (height != null) {
			if (isValueReference(height)) {
				ValueBinding vb = application.createValueBinding(height);

				component.setHeight(vb);
			} else {
				component.setHeight(height);
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);

				component.setWidth(vb);
			} else {
				component.setWidth(width);
			}
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(visible));
			}
		}

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);

				component.setLookId(vb);
			} else {
				component.setLookId(lookId);
			}
		}

		if (orientation != null) {
			if (isValueReference(orientation)) {
				ValueBinding vb = application.createValueBinding(orientation);

				component.setOrientation(vb);
			} else {
				component.setOrientation(orientation);
			}
		}

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

		if (alignment != null) {
			if (isValueReference(alignment)) {
				ValueBinding vb = application.createValueBinding(alignment);

				component.setAlignment(vb);
			} else {
				component.setAlignment(alignment);
			}
		}

		if (rendered != null) {
			if (isValueReference(rendered)) {
				ValueBinding vb = application.createValueBinding(rendered);
				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(rendered));
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
		}
	}

	public void release() {
		x = null;
		y = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		height = null;
		width = null;
		hiddenMode = null;
		visible = null;
		lookId = null;
		orientation = null;
		backgroundColor = null;
		foregroundColor = null;
		alignment = null;
		rendered = null;
		margins = null;

		super.release();
	}

}
