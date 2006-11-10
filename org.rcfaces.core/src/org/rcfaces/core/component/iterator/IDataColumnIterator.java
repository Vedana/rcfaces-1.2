/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.DataColumnComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataColumnIterator extends IComponentIterator {

    DataColumnComponent next();

    DataColumnComponent[] toArray();
}
