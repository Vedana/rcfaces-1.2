/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ItemActionEvent extends ActionEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8692727663634080090L;

    private final Object itemValue;

    public ItemActionEvent(UIComponent component, Object itemValue) {
        super(component);

        this.itemValue = itemValue;
    }

    public Object getItemValue() {
        return itemValue;
    }

}
