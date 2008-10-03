package org.rcfaces.renderkit.svg.component;

import java.lang.String;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import java.util.Arrays;
import org.rcfaces.renderkit.svg.component.Properties;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import java.util.Set;
import java.util.HashSet;

public abstract class NodeComponent extends CameliaItemComponent implements 
	IAlternateTextCapability {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"targetId","alternateText","itemDescription","itemLabel","itemDisabled","rendered","itemValue","selectable"}));
	}


	public void setTargetId(String src) {


			setItemValue(src);
			
	}

	public String getTargetId() {


			return (String)getItemValue();
			
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

	public boolean isRendered() {
		return isRendered(null);
	}

	public boolean isRendered(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDERED, true, facesContext);
	}

	public void setRendered(boolean rendered) {
		engine.setProperty(Properties.RENDERED, rendered);
	}

	/**
	 * Returns <code>true</code> if the attribute "rendered" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRenderedSetted() {
		return engine.isPropertySetted(Properties.RENDERED);
	}

	public boolean isSelectable() {
		return isSelectable(null);
	}

	public boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTABLE, false, facesContext);
	}

	public void setSelectable(boolean selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectableSetted() {
		return engine.isPropertySetted(Properties.SELECTABLE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TARGET_ID.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueExpression(name, binding);
	}
}
