package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolItemSeparatorComponent;

public class ToolItemSeparatorTag extends AbstractSeparatorTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemSeparatorTag.class);

	public String getComponentType() {
		return ToolItemSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
