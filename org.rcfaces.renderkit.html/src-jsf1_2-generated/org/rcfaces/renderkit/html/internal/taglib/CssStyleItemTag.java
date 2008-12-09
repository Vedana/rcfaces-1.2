package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.renderkit.html.component.CssStyleItemComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.taglib.FileItemTag;

public class CssStyleItemTag extends FileItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CssStyleItemTag.class);

	public String getComponentType() {
		return CssStyleItemComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
