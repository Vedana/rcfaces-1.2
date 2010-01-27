/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.renderkit.html.component.capability.IUserAgentVaryCapability;
import org.rcfaces.renderkit.html.item.UserAgentVaryFileItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserAgentVaryFilesCollectorDecorator extends
        FilesCollectorDecorator {

    public UserAgentVaryFilesCollectorDecorator(UIComponent component) {
        super(component);
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof IUserAgentVaryCapability) {
            return new UserAgentVaryFileItem(component);
        }

        return super.createSelectItem(component);
    }

}
