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

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ISortListener;


/**
 * L'�lement qui impl�mente cette interface g�n�re un �v�nement d�s que
 * l'utilisateur interagit avec lui.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISortEventCapability {

	String SORT_INTEGER = "integer";

	String SORT_NUMBER = "number";

	String SORT_ALPHA = "alpha";

	String SORT_ALPHA_IGNORE_CASE = "alphaIgnoreCase";

	String SORT_TIME = "time";

	String SORT_DATE = "date";

	String SORT_SERVER = "server";

	void addSortListener(ISortListener facesListener);

	void removeSortListener(ISortListener facesListener);

	FacesListener[] listSortListeners();
}