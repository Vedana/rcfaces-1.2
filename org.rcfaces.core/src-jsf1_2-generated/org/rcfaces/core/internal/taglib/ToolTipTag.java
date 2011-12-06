package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.ToolTipComponent;
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

public class ToolTipTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolTipTag.class);

	private ValueExpression backgroundImageHorizontalPosition;
	private ValueExpression backgroundImageHorizontalRepeat;
	private ValueExpression backgroundImageURL;
	private ValueExpression backgroundImageVerticalPosition;
	private ValueExpression backgroundImageVerticalRepeat;
	private ValueExpression border;
	private ValueExpression initListeners;
	private ValueExpression loadListeners;
	private ValueExpression asyncRenderMode;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression toolTipId;
	private ValueExpression position;
	public String getComponentType() {
		return ToolTipComponent.COMPONENT_TYPE;
	}

	public void setBackgroundImageHorizontalPosition(ValueExpression backgroundImageHorizontalPosition) {
		this.backgroundImageHorizontalPosition = backgroundImageHorizontalPosition;
	}

	public void setBackgroundImageHorizontalRepeat(ValueExpression backgroundImageHorizontalRepeat) {
		this.backgroundImageHorizontalRepeat = backgroundImageHorizontalRepeat;
	}

	public void setBackgroundImageURL(ValueExpression backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
	}

	public void setBackgroundImageVerticalPosition(ValueExpression backgroundImageVerticalPosition) {
		this.backgroundImageVerticalPosition = backgroundImageVerticalPosition;
	}

	public void setBackgroundImageVerticalRepeat(ValueExpression backgroundImageVerticalRepeat) {
		this.backgroundImageVerticalRepeat = backgroundImageVerticalRepeat;
	}

	public void setBorder(ValueExpression border) {
		this.border = border;
	}

	public void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public void setAsyncRenderMode(ValueExpression asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public void setToolTipId(ValueExpression toolTipId) {
		this.toolTipId = toolTipId;
	}

	public void setPosition(ValueExpression position) {
		this.position = position;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolTipComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  toolTipId='"+toolTipId+"'");
			LOG.debug("  position='"+position+"'");
		}
		if ((uiComponent instanceof ToolTipComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolTipComponent'.");
		}

		super.setProperties(uiComponent);

		ToolTipComponent component = (ToolTipComponent) uiComponent;
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

		if (toolTipId != null) {
			if (toolTipId.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_ID, toolTipId);

			} else {
				component.setToolTipId(toolTipId.getExpressionString());
			}
		}

		if (position != null) {
			if (position.isLiteralText()==false) {
				component.setValueExpression(Properties.POSITION, position);

			} else {
				component.setPosition(position.getExpressionString());
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
		initListeners = null;
		loadListeners = null;
		asyncRenderMode = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		toolTipId = null;
		position = null;

		super.release();
	}

}
