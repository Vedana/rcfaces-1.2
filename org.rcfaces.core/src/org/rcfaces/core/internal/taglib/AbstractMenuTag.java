package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractMenuComponent;

public abstract class AbstractMenuTag extends AbstractConverterCommandTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractMenuTag.class);

	private String selectionListeners;
	private String checkListeners;
	private String readOnly;
	private String removeAllWhenShown;
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

	public final String getRemoveAllWhenShown() {
		return removeAllWhenShown;
	}

	public final void setRemoveAllWhenShown(String removeAllWhenShown) {
		this.removeAllWhenShown = removeAllWhenShown;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  removeAllWhenShown='"+removeAllWhenShown+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractMenuComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractMenuComponent'.");
		}

		AbstractMenuComponent component = (AbstractMenuComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			parseActionListener(application, component, CHECK_LISTENER_TYPE, checkListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
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
	}

	public void release() {
		selectionListeners = null;
		checkListeners = null;
		readOnly = null;
		removeAllWhenShown = null;

		super.release();
	}

}
