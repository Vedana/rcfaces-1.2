package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ExpandBarComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ExpandBarTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ExpandBarTag.class);

	private ValueExpression asyncRenderMode;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression disabled;
	private ValueExpression readOnly;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression textAlignment;
	private ValueExpression collapsed;
	private ValueExpression border;
	private ValueExpression accessKey;
	private ValueExpression tabIndex;
	private ValueExpression groupName;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression selectionListeners;
	private ValueExpression loadListeners;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression collapseEffect;
	private ValueExpression collapsedText;
	public String getComponentType() {
		return ExpandBarComponent.COMPONENT_TYPE;
	}

	public final void setAsyncRenderMode(ValueExpression asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public final void setFontBold(ValueExpression fontBold) {
		this.fontBold = fontBold;
	}

	public final void setFontItalic(ValueExpression fontItalic) {
		this.fontItalic = fontItalic;
	}

	public final void setFontName(ValueExpression fontName) {
		this.fontName = fontName;
	}

	public final void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public final void setFontUnderline(ValueExpression fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public final void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public final void setTextAlignment(ValueExpression textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final void setCollapsed(ValueExpression collapsed) {
		this.collapsed = collapsed;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public final void setTabIndex(ValueExpression tabIndex) {
		this.tabIndex = tabIndex;
	}

	public final void setGroupName(ValueExpression groupName) {
		this.groupName = groupName;
	}

	public final void setBlurListener(ValueExpression blurListeners) {
		this.blurListeners = blurListeners;
	}

	public final void setFocusListener(ValueExpression focusListeners) {
		this.focusListeners = focusListeners;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final void setCollapseEffect(ValueExpression collapseEffect) {
		this.collapseEffect = collapseEffect;
	}

	public final void setCollapsedText(ValueExpression collapsedText) {
		this.collapsedText = collapsedText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ExpandBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  collapsed='"+collapsed+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  collapseEffect='"+collapseEffect+"'");
			LOG.debug("  collapsedText='"+collapsedText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ExpandBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ExpandBarComponent'.");
		}

		ExpandBarComponent component = (ExpandBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (asyncRenderMode != null) {
			if (asyncRenderMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ASYNC_RENDER_MODE, asyncRenderMode);

			} else {
				component.setAsyncRenderMode(asyncRenderMode.getExpressionString());
			}
		}

		if (fontBold != null) {
			if (fontBold.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_BOLD, fontBold);

			} else {
				component.setFontBold(getBoolean(fontBold.getExpressionString()));
			}
		}

		if (fontItalic != null) {
			if (fontItalic.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_ITALIC, fontItalic);

			} else {
				component.setFontItalic(getBoolean(fontItalic.getExpressionString()));
			}
		}

		if (fontName != null) {
			if (fontName.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_NAME, fontName);

			} else {
				component.setFontName(fontName.getExpressionString());
			}
		}

		if (fontSize != null) {
			if (fontSize.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE, fontSize);

			} else {
				component.setFontSize(fontSize.getExpressionString());
			}
		}

		if (fontUnderline != null) {
			if (fontUnderline.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_UNDERLINE, fontUnderline);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline.getExpressionString()));
			}
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textDirection != null) {
			if (textDirection.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DIRECTION, textDirection);

			} else {
				component.setTextDirection(getInt(textDirection.getExpressionString()));
			}
		}

		if (textAlignment != null) {
			if (textAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGNMENT, textAlignment);

			} else {
				component.setTextAlignment(textAlignment.getExpressionString());
			}
		}

		if (collapsed != null) {
			if (collapsed.isLiteralText()==false) {
				component.setValueExpression(Properties.COLLAPSED, collapsed);

			} else {
				component.setCollapsed(getBool(collapsed.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (tabIndex != null) {
			if (tabIndex.isLiteralText()==false) {
				component.setValueExpression(Properties.TAB_INDEX, tabIndex);

			} else {
				component.setTabIndex(getInteger(tabIndex.getExpressionString()));
			}
		}

		if (groupName != null) {
			if (groupName.isLiteralText()==false) {
				component.setValueExpression(Properties.GROUP_NAME, groupName);

			} else {
				component.setGroupName(groupName.getExpressionString());
			}
		}

		if (blurListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (scopeValue != null) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}

		if (collapseEffect != null) {
			if (collapseEffect.isLiteralText()==false) {
				component.setValueExpression(Properties.COLLAPSE_EFFECT, collapseEffect);

			} else {
				component.setCollapseEffect(collapseEffect.getExpressionString());
			}
		}

		if (collapsedText != null) {
			if (collapsedText.isLiteralText()==false) {
				component.setValueExpression(Properties.COLLAPSED_TEXT, collapsedText);

			} else {
				component.setCollapsedText(collapsedText.getExpressionString());
			}
		}
	}

	public void release() {
		asyncRenderMode = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		disabled = null;
		readOnly = null;
		text = null;
		textDirection = null;
		textAlignment = null;
		collapsed = null;
		border = null;
		accessKey = null;
		tabIndex = null;
		groupName = null;
		blurListeners = null;
		focusListeners = null;
		selectionListeners = null;
		loadListeners = null;
		scopeValue = null;
		scopeVar = null;
		collapseEffect = null;
		collapsedText = null;

		super.release();
	}

}
