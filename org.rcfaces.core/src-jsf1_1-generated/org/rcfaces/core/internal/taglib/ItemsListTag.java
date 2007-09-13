package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.ItemsListComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ItemsListTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ItemsListTag.class);

	private String doubleClickListeners;
	private String textPosition;
	private String borderType;
	private String selectionListeners;
	private String checkListeners;
	private String checkedValues;
	private String readOnly;
	private String defaultImageURL;
	private String defaultSelectedImageURL;
	private String defaultHoverImageURL;
	private String defaultDisabledImageURL;
	private String itemPadding;
	private String itemHiddenMode;
	public String getComponentType() {
		return ItemsListComponent.COMPONENT_TYPE;
	}

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
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

	public final void setDefaultImageURL(String defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	public final void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		this.defaultSelectedImageURL = defaultSelectedImageURL;
	}

	public final void setDefaultHoverImageURL(String defaultHoverImageURL) {
		this.defaultHoverImageURL = defaultHoverImageURL;
	}

	public final void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		this.defaultDisabledImageURL = defaultDisabledImageURL;
	}

	public final void setItemPadding(String itemPadding) {
		this.itemPadding = itemPadding;
	}

	public final void setItemHiddenMode(String itemHiddenMode) {
		this.itemHiddenMode = itemHiddenMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ItemsListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  defaultImageURL='"+defaultImageURL+"'");
			LOG.debug("  defaultSelectedImageURL='"+defaultSelectedImageURL+"'");
			LOG.debug("  defaultHoverImageURL='"+defaultHoverImageURL+"'");
			LOG.debug("  defaultDisabledImageURL='"+defaultDisabledImageURL+"'");
			LOG.debug("  itemPadding='"+itemPadding+"'");
			LOG.debug("  itemHiddenMode='"+itemHiddenMode+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ItemsListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ItemsListComponent'.");
		}

		ItemsListComponent component = (ItemsListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);
				component.setValueBinding(Properties.TEXT_POSITION, vb);

			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);
				component.setValueBinding(Properties.BORDER_TYPE, vb);

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

		if (checkedValues != null) {
				ValueBinding vb = application.createValueBinding(checkedValues);
				component.setValueBinding(Properties.CHECKED_VALUES, vb);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (defaultImageURL != null) {
			if (isValueReference(defaultImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultImageURL);
				component.setValueBinding(Properties.DEFAULT_IMAGE_URL, vb);

			} else {
				component.setDefaultImageURL(defaultImageURL);
			}
		}

		if (defaultSelectedImageURL != null) {
			if (isValueReference(defaultSelectedImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultSelectedImageURL);
				component.setValueBinding(Properties.DEFAULT_SELECTED_IMAGE_URL, vb);

			} else {
				component.setDefaultSelectedImageURL(defaultSelectedImageURL);
			}
		}

		if (defaultHoverImageURL != null) {
			if (isValueReference(defaultHoverImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultHoverImageURL);
				component.setValueBinding(Properties.DEFAULT_HOVER_IMAGE_URL, vb);

			} else {
				component.setDefaultHoverImageURL(defaultHoverImageURL);
			}
		}

		if (defaultDisabledImageURL != null) {
			if (isValueReference(defaultDisabledImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultDisabledImageURL);
				component.setValueBinding(Properties.DEFAULT_DISABLED_IMAGE_URL, vb);

			} else {
				component.setDefaultDisabledImageURL(defaultDisabledImageURL);
			}
		}

		if (itemPadding != null) {
			if (isValueReference(itemPadding)) {
				ValueBinding vb = application.createValueBinding(itemPadding);
				component.setValueBinding(Properties.ITEM_PADDING, vb);

			} else {
				component.setItemPadding(getInt(itemPadding));
			}
		}

		if (itemHiddenMode != null) {
			if (isValueReference(itemHiddenMode)) {
				ValueBinding vb = application.createValueBinding(itemHiddenMode);
				component.setValueBinding(Properties.ITEM_HIDDEN_MODE, vb);

			} else {
				component.setItemHiddenMode(itemHiddenMode);
			}
		}
	}

	public void release() {
		doubleClickListeners = null;
		textPosition = null;
		borderType = null;
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		readOnly = null;
		defaultImageURL = null;
		defaultSelectedImageURL = null;
		defaultHoverImageURL = null;
		defaultDisabledImageURL = null;
		itemPadding = null;
		itemHiddenMode = null;

		super.release();
	}

}
