/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.component.ToolFolderComponent;
import org.rcfaces.core.component.ToolItemComponent;
import org.rcfaces.core.component.iterator.IToolFolderIterator;
import org.rcfaces.core.component.iterator.IToolItemIterator;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolBarTools {
    private static final String REVISION = "$Revision$";

    private static final IToolItemIterator EMPTY_TOOL_ITEM_ITERATOR = new ToolItemListIterator(
            Collections.EMPTY_LIST);

    private static final IToolFolderIterator EMPTY_TOOL_FOLDER_ITERATOR = new ToolFolderListIterator(
            Collections.EMPTY_LIST);

    public static IToolItemIterator listToolItems(ToolFolderComponent component) {
        List list = ComponentIterators.list(component, ToolItemComponent.class);
        if (list.isEmpty()) {
            return EMPTY_TOOL_ITEM_ITERATOR;
        }

        return new ToolItemListIterator(list);
    }

    public static IToolFolderIterator listToolFolders(ToolBarComponent component) {
        List list = ComponentIterators.list(component,
                ToolFolderComponent.class);
        if (list.isEmpty()) {
            return EMPTY_TOOL_FOLDER_ITERATOR;
        }

        return new ToolFolderListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ToolItemListIterator extends
            ComponentIterators.ComponentListIterator implements
            IToolItemIterator {
        private static final String REVISION = "$Revision$";

        public ToolItemListIterator(List list) {
            super(list);
        }

        public final ToolItemComponent next() {
            return (ToolItemComponent) nextComponent();
        }

        public ToolItemComponent[] toArray() {
            return (ToolItemComponent[]) toArray(new ToolItemComponent[count()]);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ToolFolderListIterator extends
            ComponentIterators.ComponentListIterator implements
            IToolFolderIterator {
        private static final String REVISION = "$Revision$";

        public ToolFolderListIterator(List list) {
            super(list);
        }

        public final ToolFolderComponent next() {
            return (ToolFolderComponent) nextComponent();
        }

        public ToolFolderComponent[] toArray() {
            return (ToolFolderComponent[]) toArray(new ToolFolderComponent[count()]);
        }
    }

}
