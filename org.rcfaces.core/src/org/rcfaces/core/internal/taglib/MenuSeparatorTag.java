package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuSeparatorComponent;
import org.apache.commons.logging.Log;

public class MenuSeparatorTag extends AbstractSeparatorTag {

private static final Log LOG=LogFactory.getLog(MenuSeparatorTag.class);
	public String getComponentType() {
		return MenuSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
