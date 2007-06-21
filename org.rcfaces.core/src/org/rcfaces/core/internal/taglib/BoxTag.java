package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.BoxComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;

public class BoxTag extends AbstractBasicTag implements Tag {

    private static final Log LOG = LogFactory.getLog(BoxTag.class);

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

    private String scopeValue;

    private String scopeVar;

    private String horizontalScroll;

    private String verticalScroll;

    public String getComponentType() {
        return BoxComponent.COMPONENT_TYPE;
    }

    public final String getBackgroundImageHorizontalPosition() {
        return backgroundImageHorizontalPosition;
    }

    public final void setBackgroundImageHorizontalPosition(
            String backgroundImageHorizontalPosition) {
        this.backgroundImageHorizontalPosition = backgroundImageHorizontalPosition;
    }

    public final String getBackgroundImageHorizontalRepeat() {
        return backgroundImageHorizontalRepeat;
    }

    public final void setBackgroundImageHorizontalRepeat(
            String backgroundImageHorizontalRepeat) {
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

    public final void setBackgroundImageVerticalPosition(
            String backgroundImageVerticalPosition) {
        this.backgroundImageVerticalPosition = backgroundImageVerticalPosition;
    }

    public final String getBackgroundImageVerticalRepeat() {
        return backgroundImageVerticalRepeat;
    }

    public final void setBackgroundImageVerticalRepeat(
            String backgroundImageVerticalRepeat) {
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

    public final String getHorizontalScroll() {
        return horizontalScroll;
    }

    public final void setHorizontalScroll(String horizontalScroll) {
        this.horizontalScroll = horizontalScroll;
    }

    public final String getVerticalScroll() {
        return verticalScroll;
    }

    public final void setVerticalScroll(String verticalScroll) {
        this.verticalScroll = verticalScroll;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (BoxComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  backgroundImageHorizontalPosition='"
                    + backgroundImageHorizontalPosition + "'");
            LOG.debug("  backgroundImageHorizontalRepeat='"
                    + backgroundImageHorizontalRepeat + "'");
            LOG.debug("  backgroundImageURL='" + backgroundImageURL + "'");
            LOG.debug("  backgroundImageVerticalPosition='"
                    + backgroundImageVerticalPosition + "'");
            LOG.debug("  backgroundImageVerticalRepeat='"
                    + backgroundImageVerticalRepeat + "'");
            LOG.debug("  border='" + border + "'");
            LOG.debug("  asyncRenderMode='" + asyncRenderMode + "'");
            LOG.debug("  scopeValue='" + scopeValue + "'");
            LOG.debug("  scopeVar='" + scopeVar + "'");
            LOG.debug("  horizontalScroll='" + horizontalScroll + "'");
            LOG.debug("  verticalScroll='" + verticalScroll + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof BoxComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'BoxComponent'.");
        }

        BoxComponent component = (BoxComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (backgroundImageHorizontalPosition != null) {
            if (isValueReference(backgroundImageHorizontalPosition)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundImageHorizontalPosition);
                component.setValueBinding(
                        Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, vb);

            } else {
                component
                        .setBackgroundImageHorizontalPosition(backgroundImageHorizontalPosition);
            }
        }

        if (backgroundImageHorizontalRepeat != null) {
            if (isValueReference(backgroundImageHorizontalRepeat)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundImageHorizontalRepeat);
                component.setValueBinding(
                        Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, vb);

            } else {
                component
                        .setBackgroundImageHorizontalRepeat(getBool(backgroundImageHorizontalRepeat));
            }
        }

        if (backgroundImageURL != null) {
            if (isValueReference(backgroundImageURL)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundImageURL);
                component.setValueBinding(Properties.BACKGROUND_IMAGE_URL, vb);

            } else {
                component.setBackgroundImageURL(backgroundImageURL);
            }
        }

        if (backgroundImageVerticalPosition != null) {
            if (isValueReference(backgroundImageVerticalPosition)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundImageVerticalPosition);
                component.setValueBinding(
                        Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, vb);

            } else {
                component
                        .setBackgroundImageVerticalPosition(backgroundImageVerticalPosition);
            }
        }

        if (backgroundImageVerticalRepeat != null) {
            if (isValueReference(backgroundImageVerticalRepeat)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundImageVerticalRepeat);
                component.setValueBinding(
                        Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, vb);

            } else {
                component
                        .setBackgroundImageVerticalRepeat(getBool(backgroundImageVerticalRepeat));
            }
        }

        if (border != null) {
            if (isValueReference(border)) {
                ValueBinding vb = application.createValueBinding(border);
                component.setValueBinding(Properties.BORDER, vb);

            } else {
                component.setBorder(getBool(border));
            }
        }

        if (mouseOutListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
        }

        if (mouseOverListeners != null) {
            ListenersTools
                    .parseListener(facesContext, component,
                            ListenersTools.MOUSE_OVER_LISTENER_TYPE,
                            mouseOverListeners);
        }

        if (initListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.INIT_LISTENER_TYPE, initListeners);
        }

        if (loadListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
        }

        if (asyncRenderMode != null) {
            if (isValueReference(asyncRenderMode)) {
                ValueBinding vb = application
                        .createValueBinding(asyncRenderMode);
                component.setValueBinding(Properties.ASYNC_RENDER_MODE, vb);

            } else {
                component.setAsyncRenderMode(asyncRenderMode);
            }
        }

        if (scopeValue != null) {
            ValueBinding vb = application.createValueBinding(scopeValue);
            component.setValueBinding(Properties.SCOPE_VALUE, vb);
        }

        if (scopeVar != null) {
            if (isValueReference(scopeVar)) {
                ValueBinding vb = application.createValueBinding(scopeVar);
                component.setValueBinding(Properties.SCOPE_VAR, vb);

            } else {
                component.setScopeVar(scopeVar);
            }
        }

        if (horizontalScroll != null) {
            if (isValueReference(horizontalScroll)) {
                ValueBinding vb = application
                        .createValueBinding(horizontalScroll);
                component.setValueBinding(Properties.HORIZONTAL_SCROLL, vb);

            } else {
                component.setHorizontalScroll(getBool(horizontalScroll));
            }
        }

        if (verticalScroll != null) {
            if (isValueReference(verticalScroll)) {
                ValueBinding vb = application
                        .createValueBinding(verticalScroll);
                component.setValueBinding(Properties.VERTICAL_SCROLL, vb);

            } else {
                component.setVerticalScroll(getBool(verticalScroll));
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
        scopeValue = null;
        scopeVar = null;
        horizontalScroll = null;
        verticalScroll = null;

        super.release();
    }

}
