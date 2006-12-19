package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rcfaces.core.component.iterator.IToolItemIterator;
import org.rcfaces.core.internal.tools.ToolBarTools;

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
