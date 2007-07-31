package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuSeparatorComponent;

public class MenuSeparatorTag extends AbstractSeparatorTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuSeparatorTag.class);

	public String getComponentType() {
		return MenuSeparatorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
