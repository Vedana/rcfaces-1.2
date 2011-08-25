package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.RulerComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class RulerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(RulerTag.class);

	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression bottom;
	private ValueExpression left;
	private ValueExpression right;
	private ValueExpression top;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression visible;
	private ValueExpression hiddenMode;
	private ValueExpression lookId;
	private ValueExpression orientation;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression alignment;
	private ValueExpression rendered;
	private ValueExpression margins;
	public String getComponentType() {
		return RulerComponent.COMPONENT_TYPE;
	}

	public void setX(ValueExpression x) {
		this.x = x;
	}

	public void setY(ValueExpression y) {
		this.y = y;
	}

	public void setBottom(ValueExpression bottom) {
		this.bottom = bottom;
	}

	public void setLeft(ValueExpression left) {
		this.left = left;
	}

	public void setRight(ValueExpression right) {
		this.right = right;
	}

	public void setTop(ValueExpression top) {
		this.top = top;
	}

	public void setMarginBottom(ValueExpression marginBottom) {
		this.marginBottom = marginBottom;
	}

	public void setMarginLeft(ValueExpression marginLeft) {
		this.marginLeft = marginLeft;
	}

	public void setMarginRight(ValueExpression marginRight) {
		this.marginRight = marginRight;
	}

	public void setMarginTop(ValueExpression marginTop) {
		this.marginTop = marginTop;
	}

	public void setWidth(ValueExpression width) {
		this.width = width;
	}

	public void setHeight(ValueExpression height) {
		this.height = height;
	}

	public void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public void setOrientation(ValueExpression orientation) {
		this.orientation = orientation;
	}

	public void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setAlignment(ValueExpression alignment) {
		this.alignment = alignment;
	}

	public void setRendered(ValueExpression rendered) {
		this.rendered = rendered;
	}

	public void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (RulerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  bottom='"+bottom+"'");
			LOG.debug("  left='"+left+"'");
			LOG.debug("  right='"+right+"'");
			LOG.debug("  top='"+top+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  orientation='"+orientation+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  alignment='"+alignment+"'");
			LOG.debug("  rendered='"+rendered+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		if ((uiComponent instanceof RulerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'RulerComponent'.");
		}

		super.setProperties(uiComponent);

		RulerComponent component = (RulerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (x != null) {
			if (x.isLiteralText()==false) {
				component.setValueExpression(Properties.X, x);

			} else {
				component.setX(x.getExpressionString());
			}
		}

		if (y != null) {
			if (y.isLiteralText()==false) {
				component.setValueExpression(Properties.Y, y);

			} else {
				component.setY(y.getExpressionString());
			}
		}

		if (bottom != null) {
			if (bottom.isLiteralText()==false) {
				component.setValueExpression(Properties.BOTTOM, bottom);

			} else {
				component.setBottom(getInt(bottom.getExpressionString()));
			}
		}

		if (left != null) {
			if (left.isLiteralText()==false) {
				component.setValueExpression(Properties.LEFT, left);

			} else {
				component.setLeft(getInt(left.getExpressionString()));
			}
		}

		if (right != null) {
			if (right.isLiteralText()==false) {
				component.setValueExpression(Properties.RIGHT, right);

			} else {
				component.setRight(getInt(right.getExpressionString()));
			}
		}

		if (top != null) {
			if (top.isLiteralText()==false) {
				component.setValueExpression(Properties.TOP, top);

			} else {
				component.setTop(getInt(top.getExpressionString()));
			}
		}

		if (marginBottom != null) {
			if (marginBottom.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_BOTTOM, marginBottom);

			} else {
				component.setMarginBottom(marginBottom.getExpressionString());
			}
		}

		if (marginLeft != null) {
			if (marginLeft.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_LEFT, marginLeft);

			} else {
				component.setMarginLeft(marginLeft.getExpressionString());
			}
		}

		if (marginRight != null) {
			if (marginRight.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_RIGHT, marginRight);

			} else {
				component.setMarginRight(marginRight.getExpressionString());
			}
		}

		if (marginTop != null) {
			if (marginTop.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_TOP, marginTop);

			} else {
				component.setMarginTop(marginTop.getExpressionString());
			}
		}

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (height != null) {
			if (height.isLiteralText()==false) {
				component.setValueExpression(Properties.HEIGHT, height);

			} else {
				component.setHeight(height.getExpressionString());
			}
		}

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
			}
		}

		if (orientation != null) {
			if (orientation.isLiteralText()==false) {
				component.setValueExpression(Properties.ORIENTATION, orientation);

			} else {
				component.setOrientation(orientation.getExpressionString());
			}
		}

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

		if (alignment != null) {
			if (alignment.isLiteralText()==false) {
				component.setValueExpression(Properties.ALIGNMENT, alignment);

			} else {
				component.setAlignment(alignment.getExpressionString());
			}
		}

		if (rendered != null) {
			if (rendered.isLiteralText()==false) {
				component.setValueExpression(Properties.RENDERED, rendered);

			} else {
				component.setRendered(getBool(rendered.getExpressionString()));
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}
	}

	public void release() {
		x = null;
		y = null;
		bottom = null;
		left = null;
		right = null;
		top = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		width = null;
		height = null;
		visible = null;
		hiddenMode = null;
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
