package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageComponent;
import org.rcfaces.core.internal.component.Properties;

public class ImageTag extends AbstractOutputTag implements Tag {

    private static final Log LOG = LogFactory.getLog(ImageTag.class);

    private String imageURL;

    private String imageHeight;

    private String imageWidth;

    private String filterProperties;

    public String getComponentType() {
        return ImageComponent.COMPONENT_TYPE;
    }

    public final String getImageURL() {
        return imageURL;
    }

    public final void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public final String getImageHeight() {
        return imageHeight;
    }

    public final void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public final String getImageWidth() {
        return imageWidth;
    }

    public final void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public final String getFilterProperties() {
        return filterProperties;
    }

    public final void setFilterProperties(String filterProperties) {
        this.filterProperties = filterProperties;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (ImageComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  imageURL='" + imageURL + "'");
            LOG.debug("  imageHeight='" + imageHeight + "'");
            LOG.debug("  imageWidth='" + imageWidth + "'");
            LOG.debug("  filterProperties='" + filterProperties + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof ImageComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'ImageComponent'.");
        }

        ImageComponent component = (ImageComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (imageURL != null) {
            if (isValueReference(imageURL)) {
                ValueBinding vb = application.createValueBinding(imageURL);
                component.setValueBinding(Properties.IMAGE_URL, vb);

            } else {
                component.setImageURL(imageURL);
            }
        }

        if (imageHeight != null) {
            if (isValueReference(imageHeight)) {
                ValueBinding vb = application.createValueBinding(imageHeight);
                component.setValueBinding(Properties.IMAGE_HEIGHT, vb);

            } else {
                component.setImageHeight(getInt(imageHeight));
            }
        }

        if (imageWidth != null) {
            if (isValueReference(imageWidth)) {
                ValueBinding vb = application.createValueBinding(imageWidth);
                component.setValueBinding(Properties.IMAGE_WIDTH, vb);

            } else {
                component.setImageWidth(getInt(imageWidth));
            }
        }

        if (filterProperties != null) {
            ValueBinding vb = application.createValueBinding(filterProperties);
            component.setValueBinding(Properties.FILTER_PROPERTIES, vb);
        }
    }

    public void release() {
        imageURL = null;
        imageHeight = null;
        imageWidth = null;
        filterProperties = null;

        super.release();
    }

}
