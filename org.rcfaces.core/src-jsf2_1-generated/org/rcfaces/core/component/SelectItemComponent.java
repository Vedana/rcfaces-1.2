package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.AbstractItemComponent;
import java.util.Collection;

public class SelectItemComponent extends AbstractItemComponent implements 
	IToolTipCapability {

	private static final Log LOG = LogFactory.getLog(SelectItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectItem";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractItemComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.TOOL_TIP_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return getStateHelper().get(Properties.TOOL_TIP_TEXT)!=null;
	}

	public void setToolTipText(java.lang.String toolTipText) {
		getStateHelper().put(Properties.TOOL_TIP_TEXT, toolTipText);
	}


	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TOOL_TIP_TEXT.toString().equals(name)) {
			name=Properties.ITEM_DESCRIPTION.toString();
		}
		super.setValueExpression(name, binding);
	}
}
