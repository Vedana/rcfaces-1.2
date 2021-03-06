package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IToolTipIdCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.internal.tools.ToolTipTools;
import java.lang.String;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.component.iterator.IToolTipIterator;
import org.rcfaces.core.internal.capability.IToolTipComponent;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IFilterCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;

/**
 * <p>The image Component shows an image (it's equivalent to &lt;img .../&gt;).</p>
 * <p>The image Component has the following capabilities :
 * <ul>
 * <li>IImageCapability</li>
 * <li>IImageSizeCapability</li>
 * <li>IAlternateTextCapability</li>
 * <li>IFilterCapability</li>
 * <li>IImageAccessorsCapability</li>      </ul>
 * </p>
 * 
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ImageComponent.html">image</a> renderer is linked to the <a href="/jsdocs/index.html?f_image.html" target="_blank">f_image</a> javascript class. f_image extends f_filtredComponent</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_image</td>
 * <td width="50%">Defines styles for the wrapper IMG element</td>
 * </tr>
 * 
 * 
 * </tbody>
 * </table>
 */
public class ImageComponent extends AbstractOutputComponent implements 
	IImageCapability,
	IImageSizeCapability,
	IAlternateTextCapability,
	IFilterCapability,
	IToolTipIdCapability,
	IToolTipComponent,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(ImageComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.image";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"imageHeight","filterProperties","alternateText","toolTipId","imageWidth","imageURL"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="imageURL";

	public ImageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


				Object value=getLocalValue();
				if (value==null) {
				value=getValueExpression(Properties.VALUE);
				}

				return ImageAccessorTools.createImageAccessor(facesContext, value);
			
	}

	public IToolTipIterator listToolTips() {


			return ToolTipTools.listToolTips(this);
		
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return engine.isPropertySetted(Properties.IMAGE_URL);
	}

	public void setImageURL(java.lang.String imageURL) {
		setValue(imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return engine.isPropertySetted(Properties.IMAGE_HEIGHT);
	}

	public void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return engine.isPropertySetted(Properties.IMAGE_WIDTH);
	}

	public void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public java.lang.String getAlternateText() {
		return getAlternateText(null);
	}

	/**
	 * See {@link #getAlternateText() getAlternateText()} for more details
	 */
	public java.lang.String getAlternateText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALTERNATE_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return engine.isPropertySetted(Properties.ALTERNATE_TEXT);
	}

	public void setAlternateText(java.lang.String alternateText) {
		engine.setProperty(Properties.ALTERNATE_TEXT, alternateText);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public java.lang.String getToolTipId() {
		return getToolTipId(null);
	}

	/**
	 * See {@link #getToolTipId() getToolTipId()} for more details
	 */
	public java.lang.String getToolTipId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipIdSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_ID);
	}

	public void setToolTipId(java.lang.String toolTipId) {
		engine.setProperty(Properties.TOOL_TIP_ID, toolTipId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
