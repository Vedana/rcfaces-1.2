package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ResetButtonComponent;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;

public class ResetButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ResetButtonTag.class);

	public String getComponentType() {
		return ResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
