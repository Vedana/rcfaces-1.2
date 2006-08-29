package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.PasswordEntryComponent;
import org.apache.commons.logging.Log;

public class PasswordEntryTag extends TextEntryTag {

private static final Log LOG=LogFactory.getLog(PasswordEntryTag.class);
	public String getComponentType() {
		return PasswordEntryComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
