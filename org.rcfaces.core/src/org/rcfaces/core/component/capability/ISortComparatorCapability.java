/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.component.capability;

import java.util.Comparator;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISortComparatorCapability {
	Comparator getSortComparator();
	
	void setSortComparator(Comparator sortComparator);
}
