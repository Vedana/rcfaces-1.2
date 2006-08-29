package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.MenuSeparatorComponent;

public class MenuSeparatorTag extends AbstractSeparatorTag {

private static final Log LOG=LogFactory.getLog(MenuSeparatorTag.class);
	public String getComponentType() {
		return MenuSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
