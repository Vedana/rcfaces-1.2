package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.DateItemComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DateItemTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateItemTag.class);

	private String text;
	private String styleClass;
	private String menuPopupId;
	private String literalLocale;
	private String literalTimeZone;
	private String date;
	public String getComponentType() {
		return DateItemComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getMenuPopupId() {
		return menuPopupId;
	}

	public final void setMenuPopupId(String menuPopupId) {
		this.menuPopupId = menuPopupId;
	}

	public final String getLiteralLocale() {
		return literalLocale;
	}

	public final void setLiteralLocale(String literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final String getLiteralTimeZone() {
		return literalTimeZone;
	}

	public final void setLiteralTimeZone(String literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final String getDate() {
		return date;
	}

	public final void setDate(String date) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateItemComponent'.");
		}

		DateItemComponent component = (DateItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
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

		if (menuPopupId != null) {
			if (isValueReference(menuPopupId)) {
				ValueBinding vb = application.createValueBinding(menuPopupId);
				component.setValueBinding(Properties.MENU_POPUP_ID, vb);

			} else {
				component.setMenuPopupId(menuPopupId);
			}
		}

		if (literalLocale != null) {
			if (isValueReference(literalLocale)) {
				ValueBinding vb = application.createValueBinding(literalLocale);
				component.setValueBinding(Properties.LITERAL_LOCALE, vb);

			} else {
				component.setLiteralLocale(literalLocale);
			}
		}

		if (literalTimeZone != null) {
			if (isValueReference(literalTimeZone)) {
				ValueBinding vb = application.createValueBinding(literalTimeZone);
				component.setValueBinding(Properties.LITERAL_TIME_ZONE, vb);

			} else {
				component.setLiteralTimeZone(literalTimeZone);
			}
		}

		if (date != null) {
			if (isValueReference(date)) {
				ValueBinding vb = application.createValueBinding(date);
				component.setValueBinding(Properties.DATE, vb);

			} else {
				component.setDate(date);
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
