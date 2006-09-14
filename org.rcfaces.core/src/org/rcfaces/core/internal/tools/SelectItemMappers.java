/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/01/03 15:21:40  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.1  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.decorator.ISelectItemMapper;
import org.rcfaces.core.model.IImagesSelectItem;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectItemMappers {
    private static final String REVISION = "$Revision$";

    public static final ISelectItemMapper SEARCH_IMAGE_MAPPER = new ISelectItemMapper() {
        private static final String REVISION = "$Revision$";

        public boolean map(SelectItem si) {
            if (si instanceof IImagesSelectItem) {
                return false;
            }
            return true;
        }

        public void unknownComponent(UIComponent component) {
        }

        public boolean acceptCollections() {
            return false;
        }

    };

}