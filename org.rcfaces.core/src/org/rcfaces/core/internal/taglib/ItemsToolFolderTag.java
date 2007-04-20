package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.internal.tools.ListenersTools;

public class ItemsToolFolderTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ItemsToolFolderTag.class);

	private String doubleClickListeners;
	private String borderType;
	private String textPosition;
	private String selectionListeners;
	private String checkListeners;
	private String checkedValues;
	private String readOnly;
	private String verticalAlignment;
	private String itemHiddenMode;
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

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
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

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getVerticalAlignment() {
		return verticalAlignment;
	}

	public final void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final String getItemHiddenMode() {
		return itemHiddenMode;
	}

	public final void setItemHiddenMode(String itemHiddenMode) {
		this.itemHiddenMode = itemHiddenMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ItemsToolFolderComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  itemHiddenMode='"+itemHiddenMode+"'");
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

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);

				component.setTextPosition(vb);
			} else {
				component.setTextPosition(textPosition);
			}
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

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (verticalAlignment != null) {
			if (isValueReference(verticalAlignment)) {
				ValueBinding vb = application.createValueBinding(verticalAlignment);

				component.setVerticalAlignment(vb);
			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (itemHiddenMode != null) {
			if (isValueReference(itemHiddenMode)) {
				ValueBinding vb = application.createValueBinding(itemHiddenMode);
				component.setItemHiddenMode(vb);
			} else {
				component.setItemHiddenMode(itemHiddenMode);
			}
		}
	}

	public void release() {
		doubleClickListeners = null;
		borderType = null;
		textPosition = null;
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		readOnly = null;
		verticalAlignment = null;
		itemHiddenMode = null;

		super.release();
	}

}
