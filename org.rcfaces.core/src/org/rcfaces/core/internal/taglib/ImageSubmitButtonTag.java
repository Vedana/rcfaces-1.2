package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImageSubmitButtonComponent;
import javax.faces.component.UIViewRoot;

public class ImageSubmitButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageSubmitButtonTag.class);

	public String getComponentType() {
		return ImageSubmitButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
