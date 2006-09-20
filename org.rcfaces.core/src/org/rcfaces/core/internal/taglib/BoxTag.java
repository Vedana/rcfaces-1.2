package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.BoxComponent;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class BoxTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(BoxTag.class);

	private String backgroundImageHorizontalPosition;
	private String backgroundImageHorizontalRepeat;
	private String backgroundImageURL;
	private String backgroundImageVerticalPosition;
	private String backgroundImageVerticalRepeat;
	private String border;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String initListeners;
	private String loadListeners;
	private String asyncRenderMode;
	public String getComponentType() {
		return BoxComponent.COMPONENT_TYPE;
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

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final String getAsyncRenderMode() {
		return asyncRenderMode;
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (BoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof BoxComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'BoxComponent'.");
		}

		BoxComponent component = (BoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (backgroundImageHorizontalPosition != null) {
			if (isValueReference(backgroundImageHorizontalPosition)) {
				ValueBinding vb = application.createValueBinding(backgroundImageHorizontalPosition);

				component.setBackgroundImageHorizontalPosition(vb);
			} else {
				component.setBackgroundImageHorizontalPosition(getInt(backgroundImageHorizontalPosition));
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
				component.setBackgroundImageVerticalPosition(getInt(backgroundImageVerticalPosition));
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
			parseActionListener(application, component, MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			parseActionListener(application, component, MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			parseActionListener(application, component, INIT_LISTENER_TYPE, initListeners);
		}

		if (loadListeners != null) {
			parseActionListener(application, component, LOAD_LISTENER_TYPE, loadListeners);
		}

		if (asyncRenderMode != null) {
			if (isValueReference(asyncRenderMode)) {
				ValueBinding vb = application.createValueBinding(asyncRenderMode);

				component.setAsyncRenderMode(vb);
			} else {
				component.setAsyncRenderMode(asyncRenderMode);
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
		loadListeners = null;
		asyncRenderMode = null;

		super.release();
	}

}
