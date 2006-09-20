package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuTag.class);

	private String menuListeners;
	private String selectionListeners;
	private String checkListeners;
	private String selectionValues;
	private String checkValues;
	private String menuId;
	private String removeAllWhenShown;
	private String converter;
	private String value;
	public String getComponentType() {
		return MenuComponent.COMPONENT_TYPE;
	}

	public final String getMenuListener() {
		return menuListeners;
	}

	public final void setMenuListener(String menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getCheckListener() {
		return checkListeners;
	}

	public final void setCheckListener(String checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final String getSelectionValues() {
		return selectionValues;
	}

	public final void setSelectionValues(String selectionValues) {
		this.selectionValues = selectionValues;
	}

	public final String getCheckValues() {
		return checkValues;
	}

	public final void setCheckValues(String checkValues) {
		this.checkValues = checkValues;
	}

	public final String getMenuId() {
		return menuId;
	}

	public final void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public final String getRemoveAllWhenShown() {
		return removeAllWhenShown;
	}

	public final void setRemoveAllWhenShown(String removeAllWhenShown) {
		this.removeAllWhenShown = removeAllWhenShown;
	}

	public final String getConverter() {
		return converter;
	}

	public final void setConverter(String converter) {
		this.converter = converter;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MenuComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectionValues='"+selectionValues+"'");
			LOG.debug("  checkValues='"+checkValues+"'");
			LOG.debug("  menuId='"+menuId+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
			LOG.debug("  converter='"+converter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuComponent'.");
		}

		MenuComponent component = (MenuComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (menuListeners != null) {
			parseActionListener(application, component, MENU_LISTENER_TYPE, menuListeners);
		}

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			parseActionListener(application, component, CHECK_LISTENER_TYPE, checkListeners);
		}

		if (selectionValues != null) {
			if (isValueReference(selectionValues)) {
				ValueBinding vb = application.createValueBinding(selectionValues);
				component.setSelectionValues(vb);
			} else {
				throw new javax.faces.FacesException("Attribute 'selectionValues' accept only a binding expression !");
			}
		}

		if (checkValues != null) {
			if (isValueReference(checkValues)) {
				ValueBinding vb = application.createValueBinding(checkValues);
				component.setCheckValues(vb);
			} else {
				throw new javax.faces.FacesException("Attribute 'checkValues' accept only a binding expression !");
			}
		}

		if (menuId != null) {
			if (isValueReference(menuId)) {
				ValueBinding vb = application.createValueBinding(menuId);
				component.setMenuId(vb);
			} else {
				component.setMenuId(menuId);
			}
		}

		if (removeAllWhenShown != null) {
			if (isValueReference(removeAllWhenShown)) {
				ValueBinding vb = application.createValueBinding(removeAllWhenShown);
				component.setRemoveAllWhenShown(vb);
			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown));
			}
		}

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setConverter(vb);
			} else {
				component.setConverter(converter);
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValue(vb);
			} else {
				component.setValue(value);
			}
		}
	}

	public void release() {
		menuListeners = null;
		selectionListeners = null;
		checkListeners = null;
		selectionValues = null;
		checkValues = null;
		menuId = null;
		removeAllWhenShown = null;
		converter = null;
		value = null;

		super.release();
	}

}
