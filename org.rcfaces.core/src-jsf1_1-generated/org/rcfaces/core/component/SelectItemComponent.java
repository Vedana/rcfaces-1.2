package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractItemComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IToolTipCapability;
import java.util.Set;
import java.util.HashSet;

public class SelectItemComponent extends AbstractItemComponent implements 
	IToolTipCapability {

	private static final Log LOG = LogFactory.getLog(SelectItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"toolTipText"}));
	}

	public SelectItemComponent() {
		setRendererType(null);
	}

	public SelectItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setToolTip(String text) {


			setItemDescription(text);
			
	}

	public String getToolTip() {


			return getItemDescription();
			
	}

	public java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_TEXT);
	}

	public void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueBinding(String name, ValueBinding binding) {
		if (Properties.TOOL_TIP_TEXT.equals(name)) {
			name=Properties.ITEM_DESCRIPTION;
		}
		super.setValueBinding(name, binding);
	}
}
