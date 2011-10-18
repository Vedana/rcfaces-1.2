package org.rcfaces.renderkit.svg.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.renderkit.svg.component.GroupComponent;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;

public class GroupTag extends NodeTag implements Tag {


	private static final Log LOG=LogFactory.getLog(GroupTag.class);

	public String getComponentType() {
		return GroupComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
