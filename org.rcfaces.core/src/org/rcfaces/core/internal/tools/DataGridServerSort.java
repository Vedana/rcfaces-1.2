/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.listener.IServerActionListener;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.ISortedComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class DataGridServerSort {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(DataGridServerSort.class);

    private static final Long LONG_0 = new Long(0l);

    private static final Double DOUBLE_0 = new Double(0.0);

    private static final Map SORT_ALIASES = new HashMap(8);

    static {
        SORT_ALIASES.put(ISortEventCapability.SORT_INTEGER, new SortLong());
        SORT_ALIASES.put(ISortEventCapability.SORT_NUMBER, new SortDouble());
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA, new SortAlpha());
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA_IGNORE_CASE,
                new SortAlphaIgnoreCase());
        SORT_ALIASES.put(ISortEventCapability.SORT_TIME, new SortDate());
        SORT_ALIASES.put(ISortEventCapability.SORT_DATE, new SortDate());
    }

    public static int[] computeSortedTranslation(FacesContext facesContext,
            DataGridComponent data, DataModel dataModel,
            ISortedComponent sortedComponents[]) {

        ISortMethod sortMethods[] = new ISortMethod[sortedComponents.length];

        for (int i = 0; i < sortMethods.length; i++) {
            DataColumnComponent columnComponent = (DataColumnComponent) sortedComponents[i]
                    .getComponent();

            sortMethods[i] = getSortMethod(columnComponent, dataModel);
        }

        int rowCount = data.getRowCount();

        List datas[] = new List[sortedComponents.length];
        for (int i = 0; i < datas.length; i++) {
            if (rowCount > 0) {
                datas[i] = new ArrayList(rowCount);

            } else {
                datas[i] = new ArrayList();
            }
        }

        if (dataModel instanceof IRangeDataModel) {
            // Charge tout !
            ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
        }

        try {
            for (int rowIndex = 0;; rowIndex++) {
                data.setRowIndex(rowIndex);

                if (data.isRowAvailable() == false) {
                    break;
                }

                for (int i = 0; i < datas.length; i++) {
                    DataColumnComponent column = (DataColumnComponent) sortedComponents[i]
                            .getComponent();

                    Object value = column.getValue(facesContext);

                    value = sortMethods[i].convertValue(facesContext, column,
                            value);

                    datas[i].add(value);
                }
            }
        } finally {
            data.setRowIndex(-1);
        }

        int translations[] = new int[datas[0].size()];
        for (int i = 0; i < translations.length; i++) {
            translations[i] = i;
        }
        if (translations.length < 2) {
            return translations;
        }

        Object ds[][] = new Object[datas.length][];
        Comparator comparators[] = new Comparator[datas.length];
        boolean sortOrders[] = new boolean[datas.length];
        for (int i = 0; i < ds.length; i++) {
            ds[i] = datas[i].toArray();
            comparators[i] = sortMethods[i].getComparator();
            sortOrders[i] = sortedComponents[i].isAscending();
        }

        for (int i = 0; i < translations.length; i++) {

            next_element: for (int j = i; j > 0; j--) {
                int j0 = translations[j - 1];
                int j1 = translations[j];

                for (int k = 0; k < sortMethods.length; k++) {
                    Object o1 = ds[k][j0];
                    Object o2 = ds[k][j1];

                    if (comparators[k] == null) {
                        continue;
                    }

                    int order = comparators[k].compare(o1, o2);
                    if (order == 0) {
                        continue;
                    }

                    if (sortOrders[k]) {
                        if (order < 0) {
                            break next_element;
                        }
                    } else if (order > 0) {
                        break next_element;
                    }

                    translations[j] = j0;
                    translations[j - 1] = j1;
                    continue next_element;
                }
            }
        }

        if (LOG.isDebugEnabled()) {
            Set set2 = new HashSet(translations.length);
            LOG.debug("Valid SORT translation ...");
            for (int i = 0; i < translations.length; i++) {
                if (set2.add(new Integer(translations[i])) == false) {

                    LOG.debug("*** INVALID TRANSLATION ***");
                    continue;
                }
            }
        }

        return translations;
    }

    private static ISortMethod getSortMethod(
            DataColumnComponent columnComponent, DataModel dataModel) {

        FacesListener facesListeners[] = columnComponent.listSortListeners();

        for (int j = 0; j < facesListeners.length; j++) {
            FacesListener facesListener = facesListeners[j];

            // Priorité coté JAVASCRIPT, on verra le serveur dans un
            // deuxieme temps ...
            if (facesListener instanceof IServerActionListener) {
                return new SortAction((IServerActionListener) facesListener);
            }

            if ((facesListener instanceof IScriptListener) == false) {
                continue;
            }

            IScriptListener scriptListener = (IScriptListener) facesListener;

            ISortMethod sortMethod = (ISortMethod) SORT_ALIASES
                    .get(scriptListener.getCommand());
            if (sortMethod == null) {
                continue;
            }

            return sortMethod;
        }

        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private interface ISortMethod {

        Comparator getComparator();

        Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value);

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class AbstractSortMethod implements ISortMethod,
            Comparator {
        private static final String REVISION = "$Revision$";

        public Comparator getComparator() {
            return this;
        }

        public int compare(Object o1, Object o2) {
            if (o1 == null) {
                return (o2 == null) ? 0 : -1;

            } else if (o2 == null) {
                return 1;
            }

            return ((Comparable) o1).compareTo(o2);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortLong extends AbstractSortMethod {

        private static final String REVISION = "$Revision$";

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            if (value == null) {
                return LONG_0;
            }

            if (value instanceof Number) {
                return value;
            }

            if (value instanceof String) {
                String s = (String) value;
                if (s.length() < 1) {
                    return LONG_0;
                }

                long l = Long.parseLong(s);
                if (l == 0l) {
                    return LONG_0;
                }

                return new Long(l);
            }

            return LONG_0;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortDouble extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            if (value == null) {
                return DOUBLE_0;
            }

            if (value instanceof Number) {
                return value;
            }

            if (value instanceof String) {
                String s = (String) value;
                if (s.length() < 1) {
                    return DOUBLE_0;
                }

                double d = Double.parseDouble(s);
                if (d == 0.0) {
                    return DOUBLE_0;
                }

                return new Double(d);
            }

            return DOUBLE_0;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortAlpha extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            if (value == null) {
                return "";
            }

            if (value instanceof String) {
                return value;
            }

            value = ValuesTools.valueToString(value, component, facesContext);

            if (value == null) {
                return "";
            }

            return value;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortAlphaIgnoreCase extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            if (value == null) {
                return "";
            }

            if (value instanceof String) {
                return ((String) value).toLowerCase();
            }

            value = ValuesTools.valueToString(value, component, facesContext);
            if (value == null) {
                return "";
            }

            return ((String) value).toLowerCase();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortDate extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            if (value == null) {
                return null;
            }

            if (value instanceof Date) {
                return value;
            }

            throw new FacesException("Invalid Date for \"date\" sort method !");
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SortAction extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        private final IServerActionListener listener;

        public SortAction(IServerActionListener listener) {
            this.listener = listener;
        }

        public Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            return value;
        }

        public Comparator getComparator() {
            return (Comparator) listener; // @XXX TODO
        }
    }
}
