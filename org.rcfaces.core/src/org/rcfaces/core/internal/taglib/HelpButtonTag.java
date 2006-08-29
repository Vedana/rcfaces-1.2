package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.HelpButtonComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class HelpButtonTag extends ImageButtonTag {

private static final Log LOG=LogFactory.getLog(HelpButtonTag.class);
	public String getComponentType() {
		return HelpButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
