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
 * Revision 1.1  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.ToolItemComponent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IToolItemIterator extends IComponentIterator {
    ToolItemComponent next();

    ToolItemComponent[] toArray();
}
