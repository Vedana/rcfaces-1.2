package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.MenuBarComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class MenuBarTag extends AbstractMenuTag {

private static final Log LOG=LogFactory.getLog(MenuBarTag.class);
	public String getComponentType() {
		return MenuBarComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
