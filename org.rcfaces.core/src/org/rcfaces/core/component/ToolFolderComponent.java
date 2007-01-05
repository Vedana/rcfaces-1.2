package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.ToolBarTools;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractItemComponent;
import org.rcfaces.core.component.iterator.IToolItemIterator;
import java.util.Arrays;
import org.rcfaces.core.component.ToolBarComponent;
import java.util.Set;
import java.util.HashSet;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolFolderComponent extends AbstractItemComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolFolder";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public ToolFolderComponent() {
		setRendererType(null);
	}

	public ToolFolderComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IToolItemIterator listToolItems() {


		return ToolBarTools.listToolItems(this);
		
	}

	public final ToolBarComponent getToolBar() {


		return (ToolBarComponent)getParent();
		
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
