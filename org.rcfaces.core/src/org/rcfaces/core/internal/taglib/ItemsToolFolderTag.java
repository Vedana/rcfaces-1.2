package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ItemsToolFolderTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ItemsToolFolderTag.class);

	private String doubleClickListeners;
	private String borderType;
	private String selectionListeners;
	private String checkListeners;
	private String readOnly;
	private String itemPadding;
	public String getComponentType() {
		return ItemsToolFolderComponent.COMPONENT_TYPE;
	}

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
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

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getItemPadding() {
		return itemPadding;
	}

	public final void setItemPadding(String itemPadding) {
		this.itemPadding = itemPadding;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ItemsToolFolderComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  itemPadding='"+itemPadding+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ItemsToolFolderComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ItemsToolFolderComponent'.");
		}

		ItemsToolFolderComponent component = (ItemsToolFolderComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);

				component.setBorderType(vb);
			} else {
				component.setBorderType(borderType);
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (itemPadding != null) {
			if (isValueReference(itemPadding)) {
				ValueBinding vb = application.createValueBinding(itemPadding);
				component.setItemPadding(vb);
			} else {
				component.setItemPadding(getInt(itemPadding));
			}
		}
	}

	public void release() {
		doubleClickListeners = null;
		borderType = null;
		selectionListeners = null;
		checkListeners = null;
		readOnly = null;
		itemPadding = null;

		super.release();
	}

}
