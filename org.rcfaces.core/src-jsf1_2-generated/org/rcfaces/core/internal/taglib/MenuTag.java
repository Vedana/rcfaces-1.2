package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MenuTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuTag.class);

	private ValueExpression preloadedLevelDepth;
	private ValueExpression menuListeners;
	private ValueExpression selectionListeners;
	private ValueExpression checkListeners;
	private ValueExpression checkedValues;
	private ValueExpression menuId;
	private ValueExpression removeAllWhenShown;
	private ValueExpression converter;
	private ValueExpression value;
	public String getComponentType() {
		return MenuComponent.COMPONENT_TYPE;
	}

	public final void setPreloadedLevelDepth(ValueExpression preloadedLevelDepth) {
		this.preloadedLevelDepth = preloadedLevelDepth;
	}

	public final void setMenuListener(ValueExpression menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public final void setMenuId(ValueExpression menuId) {
		this.menuId = menuId;
	}

	public final void setRemoveAllWhenShown(ValueExpression removeAllWhenShown) {
		this.removeAllWhenShown = removeAllWhenShown;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MenuComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  preloadedLevelDepth='"+preloadedLevelDepth+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  menuId='"+menuId+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
			LOG.debug("  converter='"+converter+"'");
		}
		if ((uiComponent instanceof MenuComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuComponent'.");
		}

		super.setProperties(uiComponent);

		MenuComponent component = (MenuComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (preloadedLevelDepth != null) {
			if (preloadedLevelDepth.isLiteralText()==false) {
				component.setValueExpression(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);

			} else {
				component.setPreloadedLevelDepth(getInt(preloadedLevelDepth.getExpressionString()));
			}
		}

		if (menuListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				component.setValueExpression(Properties.CHECKED_VALUES, checkedValues);
		}

		if (menuId != null) {
			if (menuId.isLiteralText()==false) {
				component.setValueExpression(Properties.MENU_ID, menuId);

			} else {
				component.setMenuId(menuId.getExpressionString());
			}
		}

		if (removeAllWhenShown != null) {
			if (removeAllWhenShown.isLiteralText()==false) {
				component.setValueExpression(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);

			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown.getExpressionString()));
			}
		}

		if (converter != null) {
			if (converter.isLiteralText()==false) {
				component.setValueExpression(Properties.CONVERTER, converter);

			} else {
				component.setConverter(converter.getExpressionString());
			}
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}
	}

	public void release() {
		preloadedLevelDepth = null;
		menuListeners = null;
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		menuId = null;
		removeAllWhenShown = null;
		converter = null;
		value = null;

		super.release();
	}

}
