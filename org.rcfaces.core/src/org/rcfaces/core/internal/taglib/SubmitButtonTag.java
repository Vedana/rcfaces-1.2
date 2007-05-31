package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.SubmitButtonComponent;

public class SubmitButtonTag extends ButtonTag implements Tag {

    private static final Log LOG = LogFactory.getLog(SubmitButtonTag.class);

    public String getComponentType() {
        return SubmitButtonComponent.COMPONENT_TYPE;
    }

    public void release() {

        super.release();
    }

}
