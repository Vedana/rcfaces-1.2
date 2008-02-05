/*
 * $Id$
 */
package org.rcfaces.core.item;

import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FileItem extends SelectItem implements IFileItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -691526095374431782L;

    public FileItem() {
    }

    public FileItem(IFileItem fileItem) {
        super(fileItem.getSrc(), null, null, false);
    }

    public FileItem(UISelectItem component) {
        super(component.getItemValue(), component.getItemLabel(), component
                .getItemDescription(), component.isItemDisabled());
    }

    public void setSrc(String src) {
        setValue(src);
    }

    public String getSrc() {
        return (String) getValue();
    }
}
