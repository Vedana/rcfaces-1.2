/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.TabComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITabIterator extends IComponentIterator<TabComponent> {
    TabComponent next();

    TabComponent[] toArray();
}
