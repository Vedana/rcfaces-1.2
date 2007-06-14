package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import javax.faces.component.UIViewRoot;

public class ToolItemSeparatorTag extends AbstractSeparatorTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemSeparatorTag.class);

	public String getComponentType() {
		return ToolItemSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
