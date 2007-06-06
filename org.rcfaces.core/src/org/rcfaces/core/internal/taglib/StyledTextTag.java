package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.StyledTextComponent;

public class StyledTextTag extends TextTag implements Tag {


	private static final Log LOG=LogFactory.getLog(StyledTextTag.class);

	public String getComponentType() {
		return StyledTextComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
