/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.model.SelectItem;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SeparatorSelectItem {
    private static final String REVISION = "$Revision$";

    private static final String SEPARATOR_KEY = "$-camelia#separator-$";

    public static final SelectItem SEPARATOR = new SelectItem() {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 3542510884321447395L;

        public Object getValue() {
            return SEPARATOR_KEY;
        }

        public void setValue(Object value) {
            throw new IllegalStateException(
                    "Can not change value of Separator item !");
        }
    };

    public static final boolean isSeparator(SelectItem selectItem) {
        if (selectItem == SEPARATOR) {
            return true;
        }

        if (SEPARATOR_KEY.equals(selectItem.getValue())) {
            return true;
        }

        return false;
    }

}
