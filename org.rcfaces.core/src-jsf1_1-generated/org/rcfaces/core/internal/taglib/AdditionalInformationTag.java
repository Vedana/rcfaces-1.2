package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.component.AdditionalInformationComponent;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class AdditionalInformationTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AdditionalInformationTag.class);

	private String propertyChangeListeners;
	private String userEventListeners;
	private String errorListeners;
	private String waiRole;
	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String backgroundColor;
	private String foregroundColor;
	private String backgroundImageHorizontalPosition;
	private String backgroundImageHorizontalRepeat;
	private String backgroundImageURL;
	private String backgroundImageVerticalPosition;
	private String backgroundImageVerticalRepeat;
	private String lookId;
	private String styleClass;
	private String height;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String initListeners;
	private String loadListeners;
	private String scopeSaveValue;
	private String scopeValue;
	private String scopeVar;
	private String margins;
	public String getComponentType() {
		return AdditionalInformationComponent.COMPONENT_TYPE;
	}

	public final String getPropertyChangeListener() {
		return propertyChangeListeners;
	}

	public final void setPropertyChangeListener(String propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final String getUserEventListener() {
		return userEventListeners;
	}

	public final void setUserEventListener(String userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final String getErrorListener() {
		return errorListeners;
	}

	public final void setErrorListener(String errorListeners) {
		this.errorListeners = errorListeners;
	}

	public final String getWaiRole() {
		return waiRole;
	}

	public final void setWaiRole(String waiRole) {
		this.waiRole = waiRole;
	}

	public final String getMarginBottom() {
		return marginBottom;
	}

	public final void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public final String getMarginLeft() {
		return marginLeft;
	}

	public final void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public final String getMarginRight() {
		return marginRight;
	}

	public final void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public final String getMarginTop() {
		return marginTop;
	}

	public final void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public final String getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final String getForegroundColor() {
		return foregroundColor;
	}

	public final void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
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

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getHeight() {
		return height;
	}

	public final void setHeight(String height) {
		this.height = height;
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

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final String getScopeSaveValue() {
		return scopeSaveValue;
	}

	public final void setScopeSaveValue(String scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public final String getScopeValue() {
		return scopeValue;
	}

	public final void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final String getScopeVar() {
		return scopeVar;
	}

	public final void setScopeVar(String scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (AdditionalInformationComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		if ((uiComponent instanceof AdditionalInformationComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AdditionalInformationComponent'.");
		}

		super.setProperties(uiComponent);

		AdditionalInformationComponent component = (AdditionalInformationComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (propertyChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (userEventListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (errorListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (waiRole != null) {
			if (isValueReference(waiRole)) {
				ValueBinding vb = application.createValueBinding(waiRole);
				component.setValueBinding(Properties.WAI_ROLE, vb);

			} else {
				component.setWaiRole(waiRole);
			}
		}

		if (marginBottom != null) {
			if (isValueReference(marginBottom)) {
				ValueBinding vb = application.createValueBinding(marginBottom);
				component.setValueBinding(Properties.MARGIN_BOTTOM, vb);

			} else {
				component.setMarginBottom(marginBottom);
			}
		}

		if (marginLeft != null) {
			if (isValueReference(marginLeft)) {
				ValueBinding vb = application.createValueBinding(marginLeft);
				component.setValueBinding(Properties.MARGIN_LEFT, vb);

			} else {
				component.setMarginLeft(marginLeft);
			}
		}

		if (marginRight != null) {
			if (isValueReference(marginRight)) {
				ValueBinding vb = application.createValueBinding(marginRight);
				component.setValueBinding(Properties.MARGIN_RIGHT, vb);

			} else {
				component.setMarginRight(marginRight);
			}
		}

		if (marginTop != null) {
			if (isValueReference(marginTop)) {
				ValueBinding vb = application.createValueBinding(marginTop);
				component.setValueBinding(Properties.MARGIN_TOP, vb);

			} else {
				component.setMarginTop(marginTop);
			}
		}

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);
				component.setValueBinding(Properties.BACKGROUND_COLOR, vb);

			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);
				component.setValueBinding(Properties.FOREGROUND_COLOR, vb);

			} else {
				component.setForegroundColor(foregroundColor);
			}
		}

		if (backgroundImageHorizontalPosition != null) {
			if (isValueReference(backgroundImageHorizontalPosition)) {
				ValueBinding vb = application.createValueBinding(backgroundImageHorizontalPosition);
				component.setValueBinding(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, vb);

			} else {
				component.setBackgroundImageHorizontalPosition(backgroundImageHorizontalPosition);
			}
		}

		if (backgroundImageHorizontalRepeat != null) {
			if (isValueReference(backgroundImageHorizontalRepeat)) {
				ValueBinding vb = application.createValueBinding(backgroundImageHorizontalRepeat);
				component.setValueBinding(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, vb);

			} else {
				component.setBackgroundImageHorizontalRepeat(getBool(backgroundImageHorizontalRepeat));
			}
		}

		if (backgroundImageURL != null) {
			if (isValueReference(backgroundImageURL)) {
				ValueBinding vb = application.createValueBinding(backgroundImageURL);
				component.setValueBinding(Properties.BACKGROUND_IMAGE_URL, vb);

			} else {
				component.setBackgroundImageURL(backgroundImageURL);
			}
		}

		if (backgroundImageVerticalPosition != null) {
			if (isValueReference(backgroundImageVerticalPosition)) {
				ValueBinding vb = application.createValueBinding(backgroundImageVerticalPosition);
				component.setValueBinding(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, vb);

			} else {
				component.setBackgroundImageVerticalPosition(backgroundImageVerticalPosition);
			}
		}

		if (backgroundImageVerticalRepeat != null) {
			if (isValueReference(backgroundImageVerticalRepeat)) {
				ValueBinding vb = application.createValueBinding(backgroundImageVerticalRepeat);
				component.setValueBinding(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, vb);

			} else {
				component.setBackgroundImageVerticalRepeat(getBool(backgroundImageVerticalRepeat));
			}
		}

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);
				component.setValueBinding(Properties.LOOK_ID, vb);

			} else {
				component.setLookId(lookId);
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

		if (height != null) {
			if (isValueReference(height)) {
				ValueBinding vb = application.createValueBinding(height);
				component.setValueBinding(Properties.HEIGHT, vb);

			} else {
				component.setHeight(height);
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (scopeSaveValue != null) {
			if (isValueReference(scopeSaveValue)) {
				ValueBinding vb = application.createValueBinding(scopeSaveValue);
				component.setValueBinding(Properties.SCOPE_SAVE_VALUE, vb);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue));
			}
		}

		if (scopeValue != null) {
			if (isValueReference(scopeValue)) {
				ValueBinding vb = application.createValueBinding(scopeValue);
				component.setValueBinding(Properties.SCOPE_VALUE, vb);

			} else {
				component.setScopeValue(scopeValue);
			}
		}

		if (scopeVar != null) {
			if (isValueReference(scopeVar)) {
				ValueBinding vb = application.createValueBinding(scopeVar);
				component.setValueBinding(Properties.SCOPE_VAR, vb);

			} else {
				component.setScopeVar(scopeVar);
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
		}
	}

	public void release() {
		propertyChangeListeners = null;
		userEventListeners = null;
		errorListeners = null;
		waiRole = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		backgroundColor = null;
		foregroundColor = null;
		backgroundImageHorizontalPosition = null;
		backgroundImageHorizontalRepeat = null;
		backgroundImageURL = null;
		backgroundImageVerticalPosition = null;
		backgroundImageVerticalRepeat = null;
		lookId = null;
		styleClass = null;
		height = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		loadListeners = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		margins = null;

		super.release();
	}

}
