package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolFolderComponent;

public class ToolFolderTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolFolderTag.class);

	public String getComponentType() {
		return ToolFolderComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
