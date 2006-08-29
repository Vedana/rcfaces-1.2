package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImageSubmitButtonComponent;

public class ImageSubmitButtonTag extends ImageButtonTag {

private static final Log LOG=LogFactory.getLog(ImageSubmitButtonTag.class);
	public String getComponentType() {
		return ImageSubmitButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
