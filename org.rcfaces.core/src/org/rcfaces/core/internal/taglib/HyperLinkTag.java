package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.HyperLinkComponent;

public class HyperLinkTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HyperLinkTag.class);

	public String getComponentType() {
		return HyperLinkComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
