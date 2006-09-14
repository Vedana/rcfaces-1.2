package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IToolItemIterator;
import org.rcfaces.core.internal.tools.ToolBarTools;

public class ToolFolderComponent extends AbstractItemComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolFolder";


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

	public void release() {
		super.release();
	}
}
