package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.MenuItemComponent;
import javax.faces.application.Application;

public class MenuItemTag extends ExpandableItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuItemTag.class);

	private ValueExpression accessKey;
	private ValueExpression acceleratorKey;
	private ValueExpression styleClass;
	private ValueExpression menuListeners;
	private ValueExpression removeAllWhenShown;
	public String getComponentType() {
		return MenuItemComponent.COMPONENT_TYPE;
	}

	public final void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public final void setAcceleratorKey(ValueExpression acceleratorKey) {
		this.acceleratorKey = acceleratorKey;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setMenuListener(ValueExpression menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final void setRemoveAllWhenShown(ValueExpression removeAllWhenShown) {
		this.removeAllWhenShown = removeAllWhenShown;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MenuItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  acceleratorKey='"+acceleratorKey+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
		}
		if ((uiComponent instanceof MenuItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuItemComponent'.");
		}

		super.setProperties(uiComponent);

		MenuItemComponent component = (MenuItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (acceleratorKey != null) {
			if (acceleratorKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCELERATOR_KEY, acceleratorKey);

			} else {
				component.setAcceleratorKey(acceleratorKey.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (menuListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (removeAllWhenShown != null) {
			if (removeAllWhenShown.isLiteralText()==false) {
				component.setValueExpression(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);

			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown.getExpressionString()));
			}
		}
	}

	public void release() {
		accessKey = null;
		acceleratorKey = null;
		styleClass = null;
		menuListeners = null;
		removeAllWhenShown = null;

		super.release();
	}

}
