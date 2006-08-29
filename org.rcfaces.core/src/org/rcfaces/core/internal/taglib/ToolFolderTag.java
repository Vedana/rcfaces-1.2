package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ToolFolderComponent;

public class ToolFolderTag extends AbstractItemTag {

private static final Log LOG=LogFactory.getLog(ToolFolderTag.class);
	public String getComponentType() {
		return ToolFolderComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
