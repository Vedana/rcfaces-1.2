/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.decorator.ISelectItemMapper;
import org.rcfaces.core.item.IImagesItem;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemMappers {
    private static final String REVISION = "$Revision$";

    public static final ISelectItemMapper SEARCH_IMAGE_MAPPER = new ISelectItemMapper() {
        private static final String REVISION = "$Revision$";

        public boolean map(SelectItem si) {
            if (si instanceof IImagesItem) {
                return false;
            }
            return true;
        }

        public void unknownComponent(UIComponent component) {
        }

        public boolean acceptCollections() {
            return true;
        }

    };

}
