package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuTag.class);

	private String menuListeners;
	private String selectionListeners;
	private String checkListeners;
	private String checkedValues;
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

	public final String getCheckedValues() {
		return checkedValues;
	}

	public final void setCheckedValues(String checkedValues) {
		this.checkedValues = checkedValues;
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
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  menuId='"+menuId+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
			LOG.debug("  converter='"+converter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuComponent'.");
		}

		MenuComponent component = (MenuComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (menuListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				ValueBinding vb = application.createValueBinding(checkedValues);

				component.setCheckedValues(vb);
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
		checkedValues = null;
		menuId = null;
		removeAllWhenShown = null;
		converter = null;
		value = null;

		super.release();
	}

}
