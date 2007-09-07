package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.SubmitButtonComponent;
import org.rcfaces.core.internal.tools.ListenersTools;

public class SubmitButtonTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SubmitButtonTag.class);

	public String getComponentType() {
		return SubmitButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
