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
public class CssStyleItem extends FileItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7658229482218017683L;

    public CssStyleItem() {
    }

    public CssStyleItem(IFileItem fileItem) {
        super(fileItem);
    }

    public CssStyleItem(UISelectItem component) {
        super(component);
    }

}
