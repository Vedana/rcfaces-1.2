package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.DateItemComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DateItemTag extends SelectItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateItemTag.class);

	private ValueExpression text;
	private ValueExpression styleClass;
	private ValueExpression menuPopupId;
	private ValueExpression literalLocale;
	private ValueExpression literalTimeZone;
	private ValueExpression date;
	public String getComponentType() {
		return DateItemComponent.COMPONENT_TYPE;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public void setMenuPopupId(ValueExpression menuPopupId) {
		this.menuPopupId = menuPopupId;
	}

	public void setLiteralLocale(ValueExpression literalLocale) {
		this.literalLocale = literalLocale;
	}

	public void setLiteralTimeZone(ValueExpression literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final void setDate(ValueExpression date) {
		this.date = date;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DateItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  menuPopupId='"+menuPopupId+"'");
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  literalTimeZone='"+literalTimeZone+"'");
			LOG.debug("  date='"+date+"'");
		}
		if ((uiComponent instanceof DateItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateItemComponent'.");
		}

		super.setProperties(uiComponent);

		DateItemComponent component = (DateItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (menuPopupId != null) {
			if (menuPopupId.isLiteralText()==false) {
				component.setValueExpression(Properties.MENU_POPUP_ID, menuPopupId);

			} else {
				component.setMenuPopupId(menuPopupId.getExpressionString());
			}
		}

		if (literalLocale != null) {
			if (literalLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_LOCALE, literalLocale);

			} else {
				component.setLiteralLocale(literalLocale.getExpressionString());
			}
		}

		if (literalTimeZone != null) {
			if (literalTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_TIME_ZONE, literalTimeZone);

			} else {
				component.setLiteralTimeZone(literalTimeZone.getExpressionString());
			}
		}

		if (date != null) {
			if (date.isLiteralText()==false) {
				component.setValueExpression(Properties.DATE, date);

			} else {
				component.setDate(date.getExpressionString());
			}
		}
	}

	public void release() {
		text = null;
		styleClass = null;
		menuPopupId = null;
		literalLocale = null;
		literalTimeZone = null;
		date = null;

		super.release();
	}

}
