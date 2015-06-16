package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.AbstractMenuComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractMenuTag extends AbstractConverterCommandTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractMenuTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression checkListeners;
	private ValueExpression checkedValues;
	private ValueExpression readOnly;
	private ValueExpression removeAllWhenShown;
	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public void setRemoveAllWhenShown(ValueExpression removeAllWhenShown) {
		this.removeAllWhenShown = removeAllWhenShown;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
		}
		if ((uiComponent instanceof AbstractMenuComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractMenuComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractMenuComponent component = (AbstractMenuComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				component.setValueExpression(Properties.CHECKED_VALUES, checkedValues);
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (removeAllWhenShown != null) {
			if (removeAllWhenShown.isLiteralText()==false) {
				component.setValueExpression(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);

			} else {
				component.setRemoveAllWhenShown(getBool(removeAllWhenShown.getExpressionString()));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		readOnly = null;
		removeAllWhenShown = null;

		super.release();
	}

}
