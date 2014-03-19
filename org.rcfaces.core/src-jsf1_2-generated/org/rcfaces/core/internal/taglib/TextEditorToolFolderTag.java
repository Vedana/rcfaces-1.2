package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TextEditorToolFolderComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TextEditorToolFolderTag extends ToolFolderTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEditorToolFolderTag.class);

	private ValueExpression itemTypes;
	private ValueExpression fontSizes;
	private ValueExpression fontNames;
	private ValueExpression forVal;
	public String getComponentType() {
		return TextEditorToolFolderComponent.COMPONENT_TYPE;
	}

	public void setItemTypes(ValueExpression itemTypes) {
		this.itemTypes = itemTypes;
	}

	public void setFontSizes(ValueExpression fontSizes) {
		this.fontSizes = fontSizes;
	}

	public void setFontNames(ValueExpression fontNames) {
		this.fontNames = fontNames;
	}

	public void setFor(ValueExpression forVal) {
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
		if ((uiComponent instanceof TextEditorToolFolderComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEditorToolFolderComponent'.");
		}

		super.setProperties(uiComponent);

		TextEditorToolFolderComponent component = (TextEditorToolFolderComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (itemTypes != null) {
			if (itemTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_TYPES, itemTypes);

			} else {
				component.setItemTypes(itemTypes.getExpressionString());
			}
		}

		if (fontSizes != null) {
			if (fontSizes.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZES, fontSizes);

			} else {
				component.setFontSizes(fontSizes.getExpressionString());
			}
		}

		if (fontNames != null) {
			if (fontNames.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_NAMES, fontNames);

			} else {
				component.setFontNames(fontNames.getExpressionString());
			}
		}

		if (forVal != null) {
			if (forVal.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forVal);

			} else {
				component.setFor(forVal.getExpressionString());
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
