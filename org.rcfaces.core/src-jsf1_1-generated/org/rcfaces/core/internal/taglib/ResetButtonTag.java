package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.ResetButtonComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class ResetButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ResetButtonTag.class);

	public String getComponentType() {
		return ResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
