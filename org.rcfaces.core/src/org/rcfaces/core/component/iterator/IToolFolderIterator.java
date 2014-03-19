/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.ToolFolderComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IToolFolderIterator extends
        IComponentIterator<ToolFolderComponent> {
    ToolFolderComponent next();

    ToolFolderComponent[] toArray();
}
