package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.HelpButtonComponent;

public class HelpButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HelpButtonTag.class);

	public String getComponentType() {
		return HelpButtonComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
