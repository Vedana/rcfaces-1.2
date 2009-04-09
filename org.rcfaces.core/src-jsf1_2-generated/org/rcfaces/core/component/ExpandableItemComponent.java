package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IExpandImageCapability;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.UIImageItemComponent;

public abstract class ExpandableItemComponent extends UIImageItemComponent implements 
	IForegroundBackgroundColorCapability,
	ITextCapability,
	IExpandImageCapability {

	private static final Log LOG = LogFactory.getLog(ExpandableItemComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(UIImageItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"hoverImageURL","imageURL","text","expandedImageURL","disabledImageURL","selectedImageURL","foregroundColor","backgroundColor"}));
	}


	public void setText(String text) {


			setItemLabel(text);
			
	}

	public String getText() {


			return getItemLabel();
			
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_COLOR);
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return engine.isPropertySetted(Properties.FOREGROUND_COLOR);
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public java.lang.String getExpandedImageURL() {
		return getExpandedImageURL(null);
	}

	/**
	 * See {@link #getExpandedImageURL() getExpandedImageURL()} for more details
	 */
	public java.lang.String getExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EXPANDED_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "expandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpandedImageURLSetted() {
		return engine.isPropertySetted(Properties.EXPANDED_IMAGE_URL);
	}

	public void setExpandedImageURL(java.lang.String expandedImageURL) {
		engine.setProperty(Properties.EXPANDED_IMAGE_URL, expandedImageURL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TEXT.equals(name)) {
			name=Properties.ITEM_LABEL;
		}
		super.setValueExpression(name, binding);
	}
}
