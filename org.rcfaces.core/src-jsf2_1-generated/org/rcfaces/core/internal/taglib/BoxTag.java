package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.BoxComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class BoxTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(BoxTag.class);

	private ValueExpression backgroundImageHorizontalPosition;
	private ValueExpression backgroundImageHorizontalRepeat;
	private ValueExpression backgroundImageURL;
	private ValueExpression backgroundImageVerticalPosition;
	private ValueExpression backgroundImageVerticalRepeat;
	private ValueExpression border;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression initListeners;
	private ValueExpression loadListeners;
	private ValueExpression asyncRenderMode;
	private ValueExpression asyncDecodeMode;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression type;
	private ValueExpression overStyleClass;
	private ValueExpression horizontalScroll;
	private ValueExpression verticalScroll;
	public String getComponentType() {
		return BoxComponent.COMPONENT_TYPE;
	}

	public final void setBackgroundImageHorizontalPosition(ValueExpression backgroundImageHorizontalPosition) {
		this.backgroundImageHorizontalPosition = backgroundImageHorizontalPosition;
	}

	public final void setBackgroundImageHorizontalRepeat(ValueExpression backgroundImageHorizontalRepeat) {
		this.backgroundImageHorizontalRepeat = backgroundImageHorizontalRepeat;
	}

	public final void setBackgroundImageURL(ValueExpression backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
	}

	public final void setBackgroundImageVerticalPosition(ValueExpression backgroundImageVerticalPosition) {
		this.backgroundImageVerticalPosition = backgroundImageVerticalPosition;
	}

	public final void setBackgroundImageVerticalRepeat(ValueExpression backgroundImageVerticalRepeat) {
		this.backgroundImageVerticalRepeat = backgroundImageVerticalRepeat;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final void setAsyncRenderMode(ValueExpression asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public final void setAsyncDecodeMode(ValueExpression asyncDecodeMode) {
		this.asyncDecodeMode = asyncDecodeMode;
	}

	public final void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public final void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final void setType(ValueExpression type) {
		this.type = type;
	}

	public final void setOverStyleClass(ValueExpression overStyleClass) {
		this.overStyleClass = overStyleClass;
	}

	public void setHorizontalScroll(ValueExpression horizontalScroll) {
		this.horizontalScroll = horizontalScroll;
	}

	public void setVerticalScroll(ValueExpression verticalScroll) {
		this.verticalScroll = verticalScroll;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (BoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  asyncDecodeMode='"+asyncDecodeMode+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  type='"+type+"'");
			LOG.debug("  overStyleClass='"+overStyleClass+"'");
			LOG.debug("  horizontalScroll='"+horizontalScroll+"'");
			LOG.debug("  verticalScroll='"+verticalScroll+"'");
		}
		if ((uiComponent instanceof BoxComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'BoxComponent'.");
		}

		super.setProperties(uiComponent);

		BoxComponent component = (BoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (backgroundImageHorizontalPosition != null) {
			if (backgroundImageHorizontalPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);

			} else {
				component.setBackgroundImageHorizontalPosition(backgroundImageHorizontalPosition.getExpressionString());
			}
		}

		if (backgroundImageHorizontalRepeat != null) {
			if (backgroundImageHorizontalRepeat.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);

			} else {
				component.setBackgroundImageHorizontalRepeat(getBool(backgroundImageHorizontalRepeat.getExpressionString()));
			}
		}

		if (backgroundImageURL != null) {
			if (backgroundImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);

			} else {
				component.setBackgroundImageURL(backgroundImageURL.getExpressionString());
			}
		}

		if (backgroundImageVerticalPosition != null) {
			if (backgroundImageVerticalPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);

			} else {
				component.setBackgroundImageVerticalPosition(backgroundImageVerticalPosition.getExpressionString());
			}
		}

		if (backgroundImageVerticalRepeat != null) {
			if (backgroundImageVerticalRepeat.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);

			} else {
				component.setBackgroundImageVerticalRepeat(getBool(backgroundImageVerticalRepeat.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (loadListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (asyncRenderMode != null) {
			if (asyncRenderMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ASYNC_RENDER_MODE, asyncRenderMode);

			} else {
				component.setAsyncRenderMode(asyncRenderMode.getExpressionString());
			}
		}

		if (asyncDecodeMode != null) {
			if (asyncDecodeMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);

			} else {
				component.setAsyncDecodeMode(asyncDecodeMode.getExpressionString());
			}
		}

		if (scopeSaveValue != null) {
			if (scopeSaveValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue.getExpressionString()));
			}
		}

		if (scopeValue != null) {
			if (scopeValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);

			} else {
				component.setScopeValue(scopeValue.getExpressionString());
			}
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}

		if (type != null) {
			if (type.isLiteralText()==false) {
				component.setValueExpression(Properties.TYPE, type);

			} else {
				component.setType(type.getExpressionString());
			}
		}

		if (overStyleClass != null) {
			if (overStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.OVER_STYLE_CLASS, overStyleClass);

			} else {
				component.setOverStyleClass(overStyleClass.getExpressionString());
			}
		}

		if (horizontalScroll != null) {
			if (horizontalScroll.isLiteralText()==false) {
				component.setValueExpression(Properties.HORIZONTAL_SCROLL, horizontalScroll);

			} else {
				component.setHorizontalScroll(getBool(horizontalScroll.getExpressionString()));
			}
		}

		if (verticalScroll != null) {
			if (verticalScroll.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_SCROLL, verticalScroll);

			} else {
				component.setVerticalScroll(getBool(verticalScroll.getExpressionString()));
			}
		}
	}

	public void release() {
		backgroundImageHorizontalPosition = null;
		backgroundImageHorizontalRepeat = null;
		backgroundImageURL = null;
		backgroundImageVerticalPosition = null;
		backgroundImageVerticalRepeat = null;
		border = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		loadListeners = null;
		asyncRenderMode = null;
		asyncDecodeMode = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		type = null;
		overStyleClass = null;
		horizontalScroll = null;
		verticalScroll = null;

		super.release();
	}

}
