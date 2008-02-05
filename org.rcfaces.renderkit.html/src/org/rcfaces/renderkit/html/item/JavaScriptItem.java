/*
 * $Id$
 */
package org.rcfaces.renderkit.html.item;

import javax.faces.component.UISelectItem;

import org.rcfaces.core.item.FileItem;
import org.rcfaces.core.item.IFileItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptItem extends FileItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -4358051605814119312L;

    public JavaScriptItem() {
    }

    public JavaScriptItem(IFileItem fileItem) {
        super(fileItem);
    }

    public JavaScriptItem(UISelectItem component) {
        super(component);
    }

}
