/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.component.UIColumn;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortedDataModel2 {
    boolean isColumnSortable(UIColumn column);
}
