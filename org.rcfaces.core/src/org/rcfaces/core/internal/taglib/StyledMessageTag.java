package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.StyledMessageComponent;
import javax.faces.application.Application;

public class StyledMessageTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(StyledMessageTag.class);

	private String backgroundImageHorizontalPosition;
	private String backgroundImageHorizontalRepeat;
	private String backgroundImageURL;
	private String backgroundImageVerticalPosition;
	private String backgroundImageVerticalRepeat;
	private String border;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String initListeners;
	private String forValue;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String showIfMessage;
	private String setFocusIfMessage;
	public String getComponentType() {
		return StyledMessageComponent.COMPONENT_TYPE;
	}

	public final String getBackgroundImageHorizontalPosition() {
		return backgroundImageHorizontalPosition;
	}

	public final void setBackgroundImageHorizontalPosition(String backgroundImageHorizontalPosition) {
		this.backgroundImageHorizontalPosition = backgroundImageHorizontalPosition;
	}

	public final String getBackgroundImageHorizontalRepeat() {
		return backgroundImageHorizontalRepeat;
	}

	public final void setBackgroundImageHorizontalRepeat(String backgroundImageHorizontalRepeat) {
		this.backgroundImageHorizontalRepeat = backgroundImageHorizontalRepeat;
	}

	public final String getBackgroundImageURL() {
		return backgroundImageURL;
	}

	public final void setBackgroundImageURL(String backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
	}

	public final String getBackgroundImageVerticalPosition() {
		return backgroundImageVerticalPosition;
	}

	public final void setBackgroundImageVerticalPosition(String backgroundImageVerticalPosition) {
		this.backgroundImageVerticalPosition = backgroundImageVerticalPosition;
	}

	public final String getBackgroundImageVerticalRepeat() {
		return backgroundImageVerticalRepeat;
	}

	public final void setBackgroundImageVerticalRepeat(String backgroundImageVerticalRepeat) {
		this.backgroundImageVerticalRepeat = backgroundImageVerticalRepeat;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getMouseOutListener() {
		return mouseOutListeners;
	}

	public final void setMouseOutListener(String mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final String getMouseOverListener() {
		return mouseOverListeners;
	}

	public final void setMouseOverListener(String mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final String getErrorStyleClass() {
		return errorStyleClass;
	}

	public final void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final String getFatalStyleClass() {
		return fatalStyleClass;
	}

	public final void setFatalStyleClass(String fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final String getInfoStyleClass() {
		return infoStyleClass;
	}

	public final void setInfoStyleClass(String infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final String getWarnStyleClass() {
		return warnStyleClass;
	}

	public final void setWarnStyleClass(String warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final String getShowIfMessage() {
		return showIfMessage;
	}

	public final void setShowIfMessage(String showIfMessage) {
		this.showIfMessage = showIfMessage;
	}

	public final String getSetFocusIfMessage() {
		return setFocusIfMessage;
	}

	public final void setSetFocusIfMessage(String setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (StyledMessageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof StyledMessageComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'StyledMessageComponent'.");
		}

		StyledMessageComponent component = (StyledMessageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (backgroundImageHorizontalPosition != null) {
			if (isValueReference(backgroundImageHorizontalPosition)) {
				ValueBinding vb = application.createValueBinding(backgroundImageHorizontalPosition);

				component.setBackgroundImageHorizontalPosition(vb);
			} else {
				component.setBackgroundImageHorizontalPosition(backgroundImageHorizontalPosition);
			}
		}

		if (backgroundImageHorizontalRepeat != null) {
			if (isValueReference(backgroundImageHorizontalRepeat)) {
				ValueBinding vb = application.createValueBinding(backgroundImageHorizontalRepeat);

				component.setBackgroundImageHorizontalRepeat(vb);
			} else {
				component.setBackgroundImageHorizontalRepeat(getBool(backgroundImageHorizontalRepeat));
			}
		}

		if (backgroundImageURL != null) {
			if (isValueReference(backgroundImageURL)) {
				ValueBinding vb = application.createValueBinding(backgroundImageURL);

				component.setBackgroundImageURL(vb);
			} else {
				component.setBackgroundImageURL(backgroundImageURL);
			}
		}

		if (backgroundImageVerticalPosition != null) {
			if (isValueReference(backgroundImageVerticalPosition)) {
				ValueBinding vb = application.createValueBinding(backgroundImageVerticalPosition);

				component.setBackgroundImageVerticalPosition(vb);
			} else {
				component.setBackgroundImageVerticalPosition(backgroundImageVerticalPosition);
			}
		}

		if (backgroundImageVerticalRepeat != null) {
			if (isValueReference(backgroundImageVerticalRepeat)) {
				ValueBinding vb = application.createValueBinding(backgroundImageVerticalRepeat);

				component.setBackgroundImageVerticalRepeat(vb);
			} else {
				component.setBackgroundImageVerticalRepeat(getBool(backgroundImageVerticalRepeat));
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (mouseOutListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.INIT_LISTENER_TYPE, initListeners);
		}

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);

				component.setFor(vb);
			} else {
				component.setFor(forValue);
			}
		}

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);

				component.setErrorStyleClass(vb);
			} else {
				component.setErrorStyleClass(errorStyleClass);
			}
		}

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);

				component.setFatalStyleClass(vb);
			} else {
				component.setFatalStyleClass(fatalStyleClass);
			}
		}

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);

				component.setInfoStyleClass(vb);
			} else {
				component.setInfoStyleClass(infoStyleClass);
			}
		}

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);

				component.setWarnStyleClass(vb);
			} else {
				component.setWarnStyleClass(warnStyleClass);
			}
		}

		if (showIfMessage != null) {
			if (isValueReference(showIfMessage)) {
				ValueBinding vb = application.createValueBinding(showIfMessage);
				component.setShowIfMessage(vb);
			} else {
				component.setShowIfMessage(getBool(showIfMessage));
			}
		}

		if (setFocusIfMessage != null) {
			if (isValueReference(setFocusIfMessage)) {
				ValueBinding vb = application.createValueBinding(setFocusIfMessage);
				component.setSetFocusIfMessage(vb);
			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage));
			}
		}
	}

	public void release() {
		backgroundImageHorizontalPosition = null;
		backgroundImageHorizontalRepeat = null;
		backgroundImageURL = null;
		backgroundImageVerticalPosition = null;
		backgroundImageVerticalRepeat = null;
		border = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		forValue = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		showIfMessage = null;
		setFocusIfMessage = null;

		super.release();
	}

}
