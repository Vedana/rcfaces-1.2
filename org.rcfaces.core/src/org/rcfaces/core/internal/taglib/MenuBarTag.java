package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.MenuBarComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class MenuBarTag extends AbstractMenuTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuBarTag.class);

	public String getComponentType() {
		return MenuBarComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
