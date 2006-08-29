package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.MenuItemComponent;
import javax.faces.application.Application;

public class MenuItemTag extends ExpandableItemTag {

private static final Log LOG=LogFactory.getLog(MenuItemTag.class);
	private String accessKey;
	private String acceleratorKey;
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuItemComponent'.");
		}

		MenuItemComponent component = (MenuItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);

				component.setAccessKey(vb);
			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (acceleratorKey != null) {
			if (isValueReference(acceleratorKey)) {
				ValueBinding vb = application.createValueBinding(acceleratorKey);

				component.setAcceleratorKey(vb);
			} else {
				component.setAcceleratorKey(acceleratorKey);
			}
		}

		if (menuListeners != null) {
			parseActionListener(application, component, MENU_LISTENER_TYPE, menuListeners);
		}

		if (removeAllWhenShown != null) {
			if (isValueReference(removeAllWhenShown)) {
				ValueBinding vb = application.createValueBinding(removeAllWhenShown);
				component.setRemoveAllWhenShown(vb);
			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown));
			}
		}
	}

	public void release() {
		accessKey = null;
		acceleratorKey = null;
		menuListeners = null;
		removeAllWhenShown = null;

		super.release();
	}

}
