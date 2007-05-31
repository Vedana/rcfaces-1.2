package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

/**
 * A select item (member of a selectable list) that shows an image.
 */
public class SelectImageItemComponent extends CameliaItemComponent implements
        IImageCapability, IImageAccessorsCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.selectImageItem";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            CameliaItemComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "itemDescription", "imageURL", "itemLabel", "itemDisabled",
                "itemValue" }));
    }

    public SelectImageItemComponent() {
        setRendererType(null);
    }

    public SelectImageItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final IContentAccessors getImageAccessors(FacesContext facesContext) {

        return ImageAccessorTools.createImageAccessors(facesContext, this,
                engine);

    }

    public java.lang.String getImageURL() {
        return getImageURL(null);
    }

    /**
     * See {@link #getImageURL() getImageURL()} for more details
     */
    public java.lang.String getImageURL(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "imageURL" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isImageURLSetted() {
        return engine.isPropertySetted(Properties.IMAGE_URL);
    }

    public void setImageURL(java.lang.String imageURL) {
        engine.setProperty(Properties.IMAGE_URL, imageURL);
    }

    public final IContentAccessors getImageAccessors() {

        return getImageAccessors(null);

    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
