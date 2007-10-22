package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.MenuBarComponent;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;

public class MenuBarTag extends AbstractMenuTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuBarTag.class);

	public String getComponentType() {
		return MenuBarComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
