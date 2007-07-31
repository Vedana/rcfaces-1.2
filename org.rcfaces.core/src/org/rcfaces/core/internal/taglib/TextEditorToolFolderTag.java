package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TextEditorToolFolderComponent;
import org.rcfaces.core.internal.component.Properties;

public class TextEditorToolFolderTag extends ToolFolderTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEditorToolFolderTag.class);

	private String itemTypes;
	private String fontSizes;
	private String fontNames;
	private String forVal;
	public String getComponentType() {
		return TextEditorToolFolderComponent.COMPONENT_TYPE;
	}

	public final String getItemTypes() {
		return itemTypes;
	}

	public final void setItemTypes(String itemTypes) {
		this.itemTypes = itemTypes;
	}

	public final String getFontSizes() {
		return fontSizes;
	}

	public final void setFontSizes(String fontSizes) {
		this.fontSizes = fontSizes;
	}

	public final String getFontNames() {
		return fontNames;
	}

	public final void setFontNames(String fontNames) {
		this.fontNames = fontNames;
	}

	public final String getFor() {
		return forVal;
	}

	public final void setFor(String forVal) {
		this.forVal = forVal;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextEditorToolFolderComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  itemTypes='"+itemTypes+"'");
			LOG.debug("  fontSizes='"+fontSizes+"'");
			LOG.debug("  fontNames='"+fontNames+"'");
			LOG.debug("  forVal='"+forVal+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextEditorToolFolderComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEditorToolFolderComponent'.");
		}

		TextEditorToolFolderComponent component = (TextEditorToolFolderComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (itemTypes != null) {
			if (isValueReference(itemTypes)) {
				ValueBinding vb = application.createValueBinding(itemTypes);
				component.setValueBinding(Properties.ITEM_TYPES, vb);

			} else {
				component.setItemTypes(itemTypes);
			}
		}

		if (fontSizes != null) {
			if (isValueReference(fontSizes)) {
				ValueBinding vb = application.createValueBinding(fontSizes);
				component.setValueBinding(Properties.FONT_SIZES, vb);

			} else {
				component.setFontSizes(fontSizes);
			}
		}

		if (fontNames != null) {
			if (isValueReference(fontNames)) {
				ValueBinding vb = application.createValueBinding(fontNames);
				component.setValueBinding(Properties.FONT_NAMES, vb);

			} else {
				component.setFontNames(fontNames);
			}
		}

		if (forVal != null) {
			if (isValueReference(forVal)) {
				ValueBinding vb = application.createValueBinding(forVal);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forVal);
			}
		}
	}

	public void release() {
		itemTypes = null;
		fontSizes = null;
		fontNames = null;
		forVal = null;

		super.release();
	}

}
