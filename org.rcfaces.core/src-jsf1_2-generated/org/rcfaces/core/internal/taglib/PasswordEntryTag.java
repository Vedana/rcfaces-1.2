package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.PasswordEntryComponent;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class PasswordEntryTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PasswordEntryTag.class);

	public String getComponentType() {
		return PasswordEntryComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
