package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.HyperLinkComponent;

public class HyperLinkTag extends ButtonTag {


	private static final Log LOG=LogFactory.getLog(HyperLinkTag.class);

	public String getComponentType() {
		return HyperLinkComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
