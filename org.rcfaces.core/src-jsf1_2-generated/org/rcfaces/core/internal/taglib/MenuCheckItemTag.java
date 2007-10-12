package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.MenuCheckItemComponent;
import javax.faces.component.UIViewRoot;

public class MenuCheckItemTag extends MenuItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuCheckItemTag.class);

	public String getComponentType() {
		return MenuCheckItemComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
