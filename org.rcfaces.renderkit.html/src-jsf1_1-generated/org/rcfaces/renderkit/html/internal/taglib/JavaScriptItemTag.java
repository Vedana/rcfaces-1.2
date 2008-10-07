package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.renderkit.html.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.taglib.FileItemTag;
import org.rcfaces.renderkit.html.component.JavaScriptItemComponent;

public class JavaScriptItemTag extends FileItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptItemTag.class);

	public String getComponentType() {
		return JavaScriptItemComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
