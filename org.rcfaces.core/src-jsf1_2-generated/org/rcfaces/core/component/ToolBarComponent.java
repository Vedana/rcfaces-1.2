package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IToolFolderIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.AbstractBasicComponent;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.internal.tools.ToolBarTools;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolBarComponent extends AbstractBasicComponent implements 
	IInitEventCapability,
	IVerticalAlignmentCapability,
	IBorderTypeCapability,
	IImageAccessorsCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"controlImageWidth","borderType","separatorImageHeight","separatorImageWidth","initListener","verticalAlignment","separatorAlternateText","separatorImageURL","locked","controlImageHeight","itemPadding","controlImageURL","controlAlternateText"}));
	}

	public ToolBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public IToolFolderIterator listToolFolders() {


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

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return engine.isPropertySetted(Properties.BORDER_TYPE);
	}

	public void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public String getSeparatorImageURL() {
		return getSeparatorImageURL(null);
	}

	public String getSeparatorImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SEPARATOR_IMAGE_URL, facesContext);
	}

	public void setSeparatorImageURL(String separatorImageURL) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_URL, separatorImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageURLSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_URL);
	}

	public int getSeparatorImageWidth() {
		return getSeparatorImageWidth(null);
	}

	public int getSeparatorImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SEPARATOR_IMAGE_WIDTH, 0, facesContext);
	}

	public void setSeparatorImageWidth(int separatorImageWidth) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_WIDTH, separatorImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageWidthSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_WIDTH);
	}

	public int getSeparatorImageHeight() {
		return getSeparatorImageHeight(null);
	}

	public int getSeparatorImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SEPARATOR_IMAGE_HEIGHT, 0, facesContext);
	}

	public void setSeparatorImageHeight(int separatorImageHeight) {
		engine.setProperty(Properties.SEPARATOR_IMAGE_HEIGHT, separatorImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageHeightSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_IMAGE_HEIGHT);
	}

	public String getSeparatorAlternateText() {
		return getSeparatorAlternateText(null);
	}

	public String getSeparatorAlternateText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SEPARATOR_ALTERNATE_TEXT, facesContext);
	}

	public void setSeparatorAlternateText(String separatorAlternateText) {
		engine.setProperty(Properties.SEPARATOR_ALTERNATE_TEXT, separatorAlternateText);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorAlternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorAlternateTextSetted() {
		return engine.isPropertySetted(Properties.SEPARATOR_ALTERNATE_TEXT);
	}

	public String getControlImageURL() {
		return getControlImageURL(null);
	}

	public String getControlImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CONTROL_IMAGE_URL, facesContext);
	}

	public void setControlImageURL(String controlImageURL) {
		engine.setProperty(Properties.CONTROL_IMAGE_URL, controlImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageURLSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_URL);
	}

	public int getControlImageWidth() {
		return getControlImageWidth(null);
	}

	public int getControlImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CONTROL_IMAGE_WIDTH, 0, facesContext);
	}

	public void setControlImageWidth(int controlImageWidth) {
		engine.setProperty(Properties.CONTROL_IMAGE_WIDTH, controlImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageWidthSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_WIDTH);
	}

	public int getControlImageHeight() {
		return getControlImageHeight(null);
	}

	public int getControlImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CONTROL_IMAGE_HEIGHT, 0, facesContext);
	}

	public void setControlImageHeight(int controlImageHeight) {
		engine.setProperty(Properties.CONTROL_IMAGE_HEIGHT, controlImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageHeightSetted() {
		return engine.isPropertySetted(Properties.CONTROL_IMAGE_HEIGHT);
	}

	public String getControlAlternateText() {
		return getControlAlternateText(null);
	}

	public String getControlAlternateText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CONTROL_ALTERNATE_TEXT, facesContext);
	}

	public void setControlAlternateText(String controlAlternateText) {
		engine.setProperty(Properties.CONTROL_ALTERNATE_TEXT, controlAlternateText);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlAlternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlAlternateTextSetted() {
		return engine.isPropertySetted(Properties.CONTROL_ALTERNATE_TEXT);
	}

	public int getItemPadding() {
		return getItemPadding(null);
	}

	public int getItemPadding(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ITEM_PADDING, 0, facesContext);
	}

	public void setItemPadding(int itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemPadding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemPaddingSetted() {
		return engine.isPropertySetted(Properties.ITEM_PADDING);
	}

	public boolean isLocked() {
		return isLocked(null);
	}

	public boolean isLocked(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.LOCKED, false, facesContext);
	}

	public void setLocked(boolean locked) {
		engine.setProperty(Properties.LOCKED, locked);
	}

	/**
	 * Returns <code>true</code> if the attribute "locked" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLockedSetted() {
		return engine.isPropertySetted(Properties.LOCKED);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
