package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * An item specialized for date values.
 */
public class DateItemComponent extends AbstractItemComponent implements 
	ITextCapability,
	IStyleClassCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"styleClass","text","date"}));
	}

	public DateItemComponent() {
		setRendererType(null);
	}

	public DateItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setDate(Object date) {


				setItemValue(date);
			
	}

	public final void setDate(ValueBinding valueBinding) {


				setValueBinding("itemValue", valueBinding);
			
	}

	public final Object getDate() {


			return getItemValue();
			
	}

	public final void setText(String text) {


			setItemLabel(text);
			
	}

	public final void setText(ValueBinding valueBinding) {


			setValueBinding("itemLabel", valueBinding);
			
	}

	public final String getText() {


			return getItemLabel();
			
	}

	public final java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	/**
	 * See {@link #setStyleClass(String) setStyleClass(String)} for more details
	 */
	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
