package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.UIImageItemComponent;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.component.capability.IExpandImageCapability;
import org.rcfaces.core.component.capability.ITextCapability;

public abstract class ExpandableItemComponent extends UIImageItemComponent implements 
	IForegroundBackgroundColorCapability,
	ITextCapability,
	IExpandImageCapability {

	private static final Log LOG = LogFactory.getLog(ExpandableItemComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=UIImageItemComponent.BEHAVIOR_EVENT_NAMES;


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
		return (String)getStateHelper().eval(Properties.BACKGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return getStateHelper().get(Properties.BACKGROUND_COLOR)!=null;
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		getStateHelper().put(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOREGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return getStateHelper().get(Properties.FOREGROUND_COLOR)!=null;
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		getStateHelper().put(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public java.lang.String getExpandedImageURL() {
		return getExpandedImageURL(null);
	}

	/**
	 * See {@link #getExpandedImageURL() getExpandedImageURL()} for more details
	 */
	public java.lang.String getExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.EXPANDED_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "expandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpandedImageURLSetted() {
		return getStateHelper().get(Properties.EXPANDED_IMAGE_URL)!=null;
	}

	public void setExpandedImageURL(java.lang.String expandedImageURL) {
		getStateHelper().put(Properties.EXPANDED_IMAGE_URL, expandedImageURL);
	}


	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TEXT.toString().equals(name)) {
			name=Properties.ITEM_LABEL.toString();
		}
		super.setValueExpression(name, binding);
	}
}
