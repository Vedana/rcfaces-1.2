/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import org.rcfaces.core.component.TabComponent;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.component.iterator.ITabIterator;
import org.rcfaces.core.internal.util.ComponentIterators;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TabbedPaneTools extends CardBoxTools {
    private static final String REVISION = "$Revision$";

    private static final ITabIterator EMPTY_COMPONENT_ITERATOR = new TabListIterator(
            Collections.EMPTY_LIST);

    public static ITabIterator listTabs(TabbedPaneComponent component) {
        List list = ComponentIterators.list(component, TabComponent.class);
        if (list.isEmpty()) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        return new TabListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class TabListIterator extends
            ComponentIterators.ComponentListIterator implements ITabIterator {
        private static final String REVISION = "$Revision$";

        public TabListIterator(List list) {
            super(list);
        }

        public final TabComponent next() {
            return (TabComponent) nextComponent();
        }

        public TabComponent[] toArray() {
            return (TabComponent[]) toArray(new TabComponent[count()]);
        }
    }
}
