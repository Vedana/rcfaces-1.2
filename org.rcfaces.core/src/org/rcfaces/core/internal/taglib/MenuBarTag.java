package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuBarComponent;

public class MenuBarTag extends AbstractMenuTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuBarTag.class);

	public String getComponentType() {
		return MenuBarComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
