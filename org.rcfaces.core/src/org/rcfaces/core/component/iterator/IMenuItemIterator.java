/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.MenuItemComponent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IMenuItemIterator extends IComponentIterator {

    MenuItemComponent next();

    MenuItemComponent[] toArray();
}