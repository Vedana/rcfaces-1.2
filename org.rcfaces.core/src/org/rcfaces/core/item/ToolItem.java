/*
 * $Id$
 */
package org.rcfaces.core.item;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.ToolItemComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolItem extends DefaultItem implements IToolItem {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 4088175556368874150L;

    public ToolItem() {
    }

    public ToolItem(String label) {
        super(label);
    }

    public ToolItem(String label, String description, boolean disabled,
            SelectItem items[]) {
        super(label, description, disabled, items);
    }

    public ToolItem(IToolItem toolItem) {
        super(toolItem);

        setBorderType(toolItem.getBorderType());

        setTextPosition(toolItem.getTextPosition());
    }

    public ToolItem(ToolItemComponent toolItemComponent) {
        super(toolItemComponent);

        FacesContext facesContext = FacesContext.getCurrentInstance();

        setBorderType(toolItemComponent.getBorderType(facesContext));

        setTextPosition(toolItemComponent.getTextPosition(facesContext));
    }

}
