/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.core.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISelectItemMapper {

    boolean map(SelectItem si);

    void unknownComponent(UIComponent component);

    boolean acceptCollections();
}