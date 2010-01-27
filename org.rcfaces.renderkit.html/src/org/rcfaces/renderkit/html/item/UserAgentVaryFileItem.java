/*
 * $Id$
 */
package org.rcfaces.renderkit.html.item;

import javax.faces.component.UISelectItem;

import org.rcfaces.core.item.FileItem;
import org.rcfaces.core.item.IFileItem;
import org.rcfaces.renderkit.html.component.capability.IUserAgentVaryCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserAgentVaryFileItem extends FileItem implements
        IUserAgentVaryFileItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7658229482218017683L;

    private String userAgent;

    public UserAgentVaryFileItem() {
    }

    public UserAgentVaryFileItem(IFileItem fileItem) {
        super(fileItem);

        if (fileItem instanceof IUserAgentVaryFileItem) {
            userAgent = ((IUserAgentVaryFileItem) fileItem).getUserAgent();
        }
    }

    public UserAgentVaryFileItem(UISelectItem component) {
        super(component);

        if (component instanceof IUserAgentVaryCapability) {
            this.userAgent = ((IUserAgentVaryCapability) component)
                    .getUserAgent();
        }
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
