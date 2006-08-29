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
 * Revision 1.1  2004/08/19 15:44:31  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.TabComponent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ITabIterator extends IComponentIterator {
    TabComponent next();

    TabComponent[] toArray();
}
