package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>The image Component shows an image (it's equivalent to &lt;img .../&gt;).</p>
 * <p>The image Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Image</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ImageComponent extends AbstractOutputComponent implements 
	IImageCapability,
	IImageSizeCapability,
	IFilterCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.image";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"imageHeight","imageURL","filterProperties","imageWidth"}));
	}

	public ImageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors() {


				return getImageAccessors(null);
			
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


				Object value=getLocalValue();
				if (value==null) {
					value=getValueBinding("value");
				}
			
				return ImageAccessorTools.createImageAccessor(facesContext, value);
			
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		setValue(imageURL);
	}

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public final void setImageURL(ValueBinding imageURL) {
		setValue(imageURL);
	}

	public final int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public final int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	public final void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	/**
	 * See {@link #setImageHeight(int) setImageHeight(int)} for more details
	 */
	public final void setImageHeight(ValueBinding imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public final int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	public final void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	/**
	 * See {@link #setImageWidth(int) setImageWidth(int)} for more details
	 */
	public final void setImageWidth(ValueBinding imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	/**
	 * See {@link #setFilterProperties(org.rcfaces.core.model.IFilterProperties) setFilterProperties(org.rcfaces.core.model.IFilterProperties)} for more details
	 */
	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
