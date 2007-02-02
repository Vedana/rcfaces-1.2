package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.InputTypeConverter;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.ToolFolderComponent;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.component.UIImageItemComponent;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolItemComponent extends UIImageItemComponent implements 
	IRadioGroupCapability,
	IInputTypeCapability,
	IImageSizeCapability,
	ILookAndFeelCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(UIImageItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"imageHeight","inputType","imageWidth","lookId","groupName"}));
	}

	public ToolItemComponent() {
		setRendererType(null);
	}

	public ToolItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setInputType(String inputType) {


			setInputType(((Integer)InputTypeConverter.SINGLETON.getAsObject(null, this, inputType)).intValue());
		
	}

	public final ToolFolderComponent getToolFolder() {


		return (ToolFolderComponent)getParent();
		
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	/**
	 * See {@link #setGroupName(String) setGroupName(String)} for more details
	 */
	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final int getInputType() {
		return getInputType(null);
	}

	/**
	 * See {@link #getInputType() getInputType()} for more details
	 */
	public final int getInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.INPUT_TYPE,0, facesContext);
	}

	public final void setInputType(int inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	/**
	 * See {@link #setInputType(int) setInputType(int)} for more details
	 */
	public final void setInputType(ValueBinding inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
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

	public final java.lang.String getLookId() {
		return getLookId(null);
	}

	/**
	 * See {@link #getLookId() getLookId()} for more details
	 */
	public final java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LOOK_ID, facesContext);
	}

	public final void setLookId(java.lang.String lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	/**
	 * See {@link #setLookId(String) setLookId(String)} for more details
	 */
	public final void setLookId(ValueBinding lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
