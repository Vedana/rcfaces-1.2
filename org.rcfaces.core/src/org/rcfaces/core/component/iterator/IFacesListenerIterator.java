/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFacesListenerIterator {
    int count();

    boolean hasNext();

    FacesListener next();

    FacesListener[] toArray();
}
