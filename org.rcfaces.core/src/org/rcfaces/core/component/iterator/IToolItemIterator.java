/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.ToolItemComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IToolItemIterator extends
        IComponentIterator<ToolItemComponent> {
    ToolItemComponent next();

    ToolItemComponent[] toArray();
}
