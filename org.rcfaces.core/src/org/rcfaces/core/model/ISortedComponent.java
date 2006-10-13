/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortedComponent {

    int ASCENDING = 0;

    int DESCENDING = 1;

    UIComponent getComponent();

    int getIndex();

    int getSortMode();

    boolean isAscending();
}
