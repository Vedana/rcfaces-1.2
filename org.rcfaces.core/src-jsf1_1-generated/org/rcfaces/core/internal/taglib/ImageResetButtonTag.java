package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImageResetButtonComponent;
import javax.faces.component.UIViewRoot;

public class ImageResetButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageResetButtonTag.class);

	public String getComponentType() {
		return ImageResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
