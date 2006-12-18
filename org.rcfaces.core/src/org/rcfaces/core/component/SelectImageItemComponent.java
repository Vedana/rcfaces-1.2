package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import java.util.Set;
import java.util.HashSet;

/**
 * A select item (member of a selectable list) that shows an image.
 */
public class SelectImageItemComponent extends CameliaItemComponent implements 
	IImageCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectImageItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"itemDescription","imageURL","itemLabel","itemDisabled","itemValue"}));
	}

	public SelectImageItemComponent() {
		setRendererType(null);
	}

	public SelectImageItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
