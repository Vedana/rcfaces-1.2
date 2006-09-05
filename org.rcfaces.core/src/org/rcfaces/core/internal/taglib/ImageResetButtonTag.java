package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ImageResetButtonComponent;

public class ImageResetButtonTag extends ImageButtonTag {


	private static final Log LOG=LogFactory.getLog(ImageResetButtonTag.class);

	public String getComponentType() {
		return ImageResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
