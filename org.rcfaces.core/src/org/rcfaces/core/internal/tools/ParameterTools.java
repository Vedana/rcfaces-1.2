/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

import org.rcfaces.core.component.iterator.IParameterIterator;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ParameterTools {
    private static final String REVISION = "$Revision$";

    private static final IParameterIterator EMPTY_PARAMETER_ITERATOR = new ParameterListIterator(
            Collections.EMPTY_LIST);

    public static IParameterIterator listParameters(UIComponent component) {
        List list = ComponentIterators.list(component, UIParameter.class);
        if (list.isEmpty()) {
            return EMPTY_PARAMETER_ITERATOR;
        }

        return new ParameterListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ParameterListIterator extends
            ComponentIterators.ComponentListIterator implements
            IParameterIterator {
        private static final String REVISION = "$Revision$";

        public ParameterListIterator(List list) {
            super(list);
        }

        public final UIParameter next() {
            return (UIParameter) nextComponent();
        }

        public UIParameter[] toArray() {
            return (UIParameter[]) toArray(new UIParameter[count()]);
        }
    }
}
