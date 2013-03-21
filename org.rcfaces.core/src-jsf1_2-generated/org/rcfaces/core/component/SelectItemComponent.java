package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IToolTipTextCapability;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.AbstractItemComponent;

public class SelectItemComponent extends AbstractItemComponent implements 
	IToolTipTextCapability {

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

	public void setToolTipText(String text) {


				setItemDescription(text);
			
	}

	public String getToolTipText() {


				return getItemDescription();
			
	}

	public boolean isToolTipTextSetted() {


				return getItemDescription()!=null;
			
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TOOL_TIP_TEXT.equals(name)) {
			name=Properties.ITEM_DESCRIPTION;
		}
		super.setValueExpression(name, binding);
	}
}
