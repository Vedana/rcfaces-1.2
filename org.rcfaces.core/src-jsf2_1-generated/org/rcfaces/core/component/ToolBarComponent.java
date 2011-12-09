package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IToolFolderIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractBasicComponent;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.ToolBarTools;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolBarComponent extends AbstractBasicComponent implements 
	IInitEventCapability,
	IVerticalAlignmentCapability,
	IBorderTypeCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(ToolBarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolBar";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

	public ToolBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
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
		return (String)getStateHelper().eval(Properties.VERTICAL_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return getStateHelper().get(Properties.VERTICAL_ALIGNMENT)!=null;
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		getStateHelper().put(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BORDER_TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return getStateHelper().get(Properties.BORDER_TYPE)!=null;
	}

	public void setBorderType(java.lang.String borderType) {
		getStateHelper().put(Properties.BORDER_TYPE, borderType);
	}

	public String getSeparatorImageURL() {
		return getSeparatorImageURL(null);
	}

	public String getSeparatorImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SEPARATOR_IMAGE_URL);
	}

	public void setSeparatorImageURL(String separatorImageURL) {
		 getStateHelper().put(Properties.SEPARATOR_IMAGE_URL, separatorImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageURLSetted() {
		return getStateHelper().get(Properties.SEPARATOR_IMAGE_URL)!=null;
	}

	public int getSeparatorImageWidth() {
		return getSeparatorImageWidth(null);
	}

	public int getSeparatorImageWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.SEPARATOR_IMAGE_WIDTH, 0);
	}

	public void setSeparatorImageWidth(int separatorImageWidth) {
		 getStateHelper().put(Properties.SEPARATOR_IMAGE_WIDTH, separatorImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageWidthSetted() {
		return getStateHelper().get(Properties.SEPARATOR_IMAGE_WIDTH)!=null;
	}

	public int getSeparatorImageHeight() {
		return getSeparatorImageHeight(null);
	}

	public int getSeparatorImageHeight(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.SEPARATOR_IMAGE_HEIGHT, 0);
	}

	public void setSeparatorImageHeight(int separatorImageHeight) {
		 getStateHelper().put(Properties.SEPARATOR_IMAGE_HEIGHT, separatorImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorImageHeightSetted() {
		return getStateHelper().get(Properties.SEPARATOR_IMAGE_HEIGHT)!=null;
	}

	public String getSeparatorAlternateText() {
		return getSeparatorAlternateText(null);
	}

	public String getSeparatorAlternateText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SEPARATOR_ALTERNATE_TEXT);
	}

	public void setSeparatorAlternateText(String separatorAlternateText) {
		 getStateHelper().put(Properties.SEPARATOR_ALTERNATE_TEXT, separatorAlternateText);
	}

	/**
	 * Returns <code>true</code> if the attribute "separatorAlternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSeparatorAlternateTextSetted() {
		return getStateHelper().get(Properties.SEPARATOR_ALTERNATE_TEXT)!=null;
	}

	public String getControlImageURL() {
		return getControlImageURL(null);
	}

	public String getControlImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CONTROL_IMAGE_URL);
	}

	public void setControlImageURL(String controlImageURL) {
		 getStateHelper().put(Properties.CONTROL_IMAGE_URL, controlImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageURLSetted() {
		return getStateHelper().get(Properties.CONTROL_IMAGE_URL)!=null;
	}

	public int getControlImageWidth() {
		return getControlImageWidth(null);
	}

	public int getControlImageWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CONTROL_IMAGE_WIDTH, 0);
	}

	public void setControlImageWidth(int controlImageWidth) {
		 getStateHelper().put(Properties.CONTROL_IMAGE_WIDTH, controlImageWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageWidthSetted() {
		return getStateHelper().get(Properties.CONTROL_IMAGE_WIDTH)!=null;
	}

	public int getControlImageHeight() {
		return getControlImageHeight(null);
	}

	public int getControlImageHeight(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CONTROL_IMAGE_HEIGHT, 0);
	}

	public void setControlImageHeight(int controlImageHeight) {
		 getStateHelper().put(Properties.CONTROL_IMAGE_HEIGHT, controlImageHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlImageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlImageHeightSetted() {
		return getStateHelper().get(Properties.CONTROL_IMAGE_HEIGHT)!=null;
	}

	public String getControlAlternateText() {
		return getControlAlternateText(null);
	}

	public String getControlAlternateText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CONTROL_ALTERNATE_TEXT);
	}

	public void setControlAlternateText(String controlAlternateText) {
		 getStateHelper().put(Properties.CONTROL_ALTERNATE_TEXT, controlAlternateText);
	}

	/**
	 * Returns <code>true</code> if the attribute "controlAlternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isControlAlternateTextSetted() {
		return getStateHelper().get(Properties.CONTROL_ALTERNATE_TEXT)!=null;
	}

	public int getItemPadding() {
		return getItemPadding(null);
	}

	public int getItemPadding(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ITEM_PADDING, 0);
	}

	public void setItemPadding(int itemPadding) {
		 getStateHelper().put(Properties.ITEM_PADDING, itemPadding);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemPadding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemPaddingSetted() {
		return getStateHelper().get(Properties.ITEM_PADDING)!=null;
	}

	public boolean isLocked() {
		return isLocked(null);
	}

	public boolean isLocked(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.LOCKED, false);
	}

	public void setLocked(boolean locked) {
		 getStateHelper().put(Properties.LOCKED, locked);
	}

	/**
	 * Returns <code>true</code> if the attribute "locked" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLockedSetted() {
		return getStateHelper().get(Properties.LOCKED)!=null;
	}

}
