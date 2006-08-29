package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.PasswordEntryComponent;

public class PasswordEntryTag extends TextEntryTag {

private static final Log LOG=LogFactory.getLog(PasswordEntryTag.class);
	public String getComponentType() {
		return PasswordEntryComponent.COMPONENT_TYPE;
	}

	public void release() {

		super.release();
	}

}
