package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.TabNamingContainerComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class TabNamingContainerTag extends TabTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabNamingContainerTag.class);

	public String getComponentType() {
		return TabNamingContainerComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}