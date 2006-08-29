package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ResetButtonComponent;

public class ResetButtonTag extends ButtonTag {

private static final Log LOG=LogFactory.getLog(ResetButtonTag.class);
	public String getComponentType() {
		return ResetButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
