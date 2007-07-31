package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageSubmitButtonComponent;

public class ImageSubmitButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageSubmitButtonTag.class);

	public String getComponentType() {
		return ImageSubmitButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
