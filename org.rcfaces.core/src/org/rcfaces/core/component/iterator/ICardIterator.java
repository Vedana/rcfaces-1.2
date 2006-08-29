/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.2  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2004/08/19 15:44:31  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.CardComponent;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ICardIterator extends IComponentIterator {
    CardComponent next();

    CardComponent[] toArray();
}
