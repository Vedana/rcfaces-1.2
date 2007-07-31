package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.HyperLinkComponent;

public class HyperLinkTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HyperLinkTag.class);

	public String getComponentType() {
		return HyperLinkComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
