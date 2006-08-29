package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.ResetButtonComponent;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ResetButtonTag extends ButtonTag {

private static final Log LOG=LogFactory.getLog(ResetButtonTag.class);
	public String getComponentType() {
		return ResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
