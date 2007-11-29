package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.renderkit.html.component.JavaScriptCollectorComponent;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;

public class JavaScriptCollectorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptCollectorTag.class);

	public String getComponentType() {
		return JavaScriptCollectorComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
