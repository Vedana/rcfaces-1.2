package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.SubmitButtonComponent;

public class SubmitButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SubmitButtonTag.class);

	public String getComponentType() {
		return SubmitButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
