package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuCheckItemComponent;

public class MenuCheckItemTag extends MenuItemTag implements Tag {

    private static final Log LOG = LogFactory.getLog(MenuCheckItemTag.class);

    public String getComponentType() {
        return MenuCheckItemComponent.COMPONENT_TYPE;
    }

    public void release() {

        super.release();
    }

}
