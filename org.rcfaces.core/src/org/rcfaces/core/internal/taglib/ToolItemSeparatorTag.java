package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ToolItemSeparatorComponent;

public class ToolItemSeparatorTag extends AbstractSeparatorTag {

private static final Log LOG=LogFactory.getLog(ToolItemSeparatorTag.class);
	public String getComponentType() {
		return ToolItemSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
