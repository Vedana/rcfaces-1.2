package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.iterator.IToolFolderIterator;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.internal.tools.ToolBarTools;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolBarComponent extends AbstractBasicComponent implements 
	IInitEventCapability,
	IVerticalAlignmentCapability,
	IImageAccessorsCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"controlImageURL","separatorImageWidth","separatorImageURL","itemPadding","verticalAlignment","initListener","controlImageWidth","separatorImageHeight","controlImageHeight"}));
	}

	public ToolBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final IToolFolderIterator listToolFolders() {


		return ToolBarTools.listToolFolders(this);
		
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGNMENT);
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	/**
	 * See {@link #setVerticalAlignment(String) setVerticalAlignment(String)} for more details
	 */
	public void setVerticalAlignment(ValueBinding verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public final String getSeparatorImageURL() {
		return getSeparatorImageURL(null);
	}

	public final String getSeparatorImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SEPARATOR_IMAGE_URL, facesContext);
	}

	public final void setSeparatorImageURL(String separatorImageURL) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_URL, separatorImageURL);
	}

	public final void setSeparatorImageURL(ValueBinding separatorImageURL) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_URL, separatorImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSeparatorImageURLSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_URL);
	}

	public final int getSeparatorImageWidth() {
		return getSeparatorImageWidth(null);
	}

	public final int getSeparatorImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SEPARATOR_IMAGE_WIDTH, 0, facesContext);
	}

	public final void setSeparatorImageWidth(int separatorImageWidth) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_WIDTH, separatorImageWidth);
	}

	public final void setSeparatorImageWidth(ValueBinding separatorImageWidth) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_WIDTH, separatorImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSeparatorImageWidthSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_WIDTH);
	}

	public final int getSeparatorImageHeight() {
		return getSeparatorImageHeight(null);
	}

	public final int getSeparatorImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SEPARATOR_IMAGE_HEIGHT, 0, facesContext);
	}

	public final void setSeparatorImageHeight(int separatorImageHeight) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_HEIGHT, separatorImageHeight);
	}

	public final void setSeparatorImageHeight(ValueBinding separatorImageHeight) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_HEIGHT, separatorImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSeparatorImageHeightSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_HEIGHT);
	}

	public final String getControlImageURL() {
		return getControlImageURL(null);
	}

	public final String getControlImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CONTROL_IMAGE_URL, facesContext);
	}

	public final void setControlImageURL(String controlImageURL) {
		engine.setProperty(Properties.CONTROL_IMAGE_URL, controlImageURL);
	}

	public final void setControlImageURL(ValueBinding controlImageURL) {
		engine.setProperty(Properties.CONTROL_IMAGE_URL, controlImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isControlImageURLSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_URL);
	}

	public final int getControlImageWidth() {
		return getControlImageWidth(null);
	}

	public final int getControlImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CONTROL_IMAGE_WIDTH, 0, facesContext);
	}

	public final void setControlImageWidth(int controlImageWidth) {
		engine.setProperty(Properties.CONTROL_IMAGE_WIDTH, controlImageWidth);
	}

	public final void setControlImageWidth(ValueBinding controlImageWidth) {
		engine.setProperty(Properties.CONTROL_IMAGE_WIDTH, controlImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isControlImageWidthSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_WIDTH);
	}

	public final int getControlImageHeight() {
		return getControlImageHeight(null);
	}

	public final int getControlImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CONTROL_IMAGE_HEIGHT, 0, facesContext);
	}

	public final void setControlImageHeight(int controlImageHeight) {
		engine.setProperty(Properties.CONTROL_IMAGE_HEIGHT, controlImageHeight);
	}

	public final void setControlImageHeight(ValueBinding controlImageHeight) {
		engine.setProperty(Properties.CONTROL_IMAGE_HEIGHT, controlImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isControlImageHeightSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_HEIGHT);
	}

	public final int getItemPadding() {
		return getItemPadding(null);
	}

	public final int getItemPadding(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ITEM_PADDING, 0, facesContext);
	}

	public final void setItemPadding(int itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	public final void setItemPadding(ValueBinding itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemPadding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isItemPaddingSetted() {
		return engine.isPropertySetted(Properties.ITEM_PADDING);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
