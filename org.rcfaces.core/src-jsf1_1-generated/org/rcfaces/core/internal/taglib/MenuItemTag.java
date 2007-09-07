package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MenuItemTag extends ExpandableItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuItemTag.class);

	private String accessKey;
	private String acceleratorKey;
	private String styleClass;
	private String menuListeners;
	private String removeAllWhenShown;
	public String getComponentType() {
		return MenuItemComponent.COMPONENT_TYPE;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getAcceleratorKey() {
		return acceleratorKey;
	}

	public final void setAcceleratorKey(String acceleratorKey) {
		this.acceleratorKey = acceleratorKey;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getMenuListener() {
		return menuListeners;
	}

	public final void setMenuListener(String menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final String getRemoveAllWhenShown() {
		return removeAllWhenShown;
	}

	public final void setRemoveAllWhenShown(String removeAllWhenShown) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuItemComponent'.");
		}

		MenuItemComponent component = (MenuItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);
				component.setValueBinding(Properties.ACCESS_KEY, vb);

			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (acceleratorKey != null) {
			if (isValueReference(acceleratorKey)) {
				ValueBinding vb = application.createValueBinding(acceleratorKey);
				component.setValueBinding(Properties.ACCELERATOR_KEY, vb);

			} else {
				component.setAcceleratorKey(acceleratorKey);
			}
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (menuListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (removeAllWhenShown != null) {
			if (isValueReference(removeAllWhenShown)) {
				ValueBinding vb = application.createValueBinding(removeAllWhenShown);
				component.setValueBinding(Properties.REMOVE_ALL_WHEN_SHOWN, vb);

			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown));
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
