/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.5  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.4  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.1  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
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

    private static final boolean TEST_SORT = false;

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

    public static int[] createTranslation(FacesContext facesContext,
            DataGridComponent data, DataModel dataModel,
            ISortedComponent sortedComponents[]) {
        DataColumnComponent columnComponent = (DataColumnComponent) sortedComponents[0]
                .getComponent();

        ISortMethod sortMethod = getSortMethod(columnComponent, dataModel);
        if (sortMethod == null) {
            // Returns NULL !
            return null;
        }

        return sortMethod.sort(facesContext, data, dataModel, columnComponent,
                sortedComponents[0].getIndex(), sortedComponents[0]
                        .isAscending());
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

        int[] sort(FacesContext facesContext, DataGridComponent data,
                DataModel dataModel, DataColumnComponent column, int sortIndex,
                boolean sortOrder);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class AbstractSortMethod implements ISortMethod,
            Comparator {
        private static final String REVISION = "$Revision$";

        public int[] sort(FacesContext facesContext,
                DataGridComponent dataGrid, DataModel dataModel,
                DataColumnComponent column, int sortColumnIndex,
                boolean sortOrder) {

            int rowCount = dataGrid.getRowCount();

            List datas;
            if (rowCount > 0) {
                datas = new ArrayList(rowCount);

            } else {
                datas = new ArrayList();
            }

            if (dataModel instanceof IRangeDataModel) {
                // Charge tout !
                ((IRangeDataModel) dataModel).setRowRange(0, rowCount);
            }

            Set testSort;
            if (TEST_SORT) {
                testSort = new HashSet();
            }
            try {
                for (int rowIndex = 0;; rowIndex++) {
                    dataGrid.setRowIndex(rowIndex);

                    if (dataGrid.isRowAvailable() == false) {
                        break;
                    }

                    if (TEST_SORT) {
                        Object rowData1 = dataGrid.getRowData();
                        Object rowData2 = dataGrid.getRowData();
                        if (testSort.add(rowData1) == false) {
                            System.err.println("**** SAME OBJECT !");
                        }
                        if (rowData1 != rowData2) {
                            System.err.println("**** Not SAME DATA !");
                        }
                    }

                    Object value = column.getValue(facesContext);

                    value = convertValue(facesContext, column, value);

                    datas.add(value);
                }
            } finally {
                dataGrid.setRowIndex(-1);
            }

            int translations[] = new int[datas.size()];
            for (int i = 0; i < translations.length; i++) {
                translations[i] = i;
            }
            if (translations.length < 2) {
                return translations;
            }

            Object ds[] = datas.toArray();

            Comparator comparator = getComparator();

            /*
             * for (int i=low; i <high; i++) for (int j=i; j>low &&
             * c.compare(dest[j-1], dest[j])>0; j--) swap(dest, j, j-1);
             */
            for (int i = 0; i < translations.length; i++) {
                for (int j = i; j > 0; j--) {
                    int j0 = translations[j - 1];
                    int j1 = translations[j];

                    Object o1 = ds[j0];
                    Object o2 = ds[j1];

                    int order = comparator.compare(o1, o2);
                    if (sortOrder) {
                        if (order <= 0) {
                            break;
                        }
                    } else if (order >= 0) {
                        break;
                    }

                    translations[j] = j0;
                    translations[j - 1] = j1;
                }
            }

            if (TEST_SORT) {
                Set set2 = new HashSet();
                System.err.println("*** TEST SORT");
                for (int i = 0; i < translations.length; i++) {

                    if (set2.add(new Integer(translations[i])) == false) {

                        System.err
                                .println("******* INDEX FAILED ******************");
                        continue;
                    }
                }
            }

            return translations;
        }

        protected abstract Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value);

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

        protected Object convertValue(FacesContext facesContext,
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

    private static class SortDouble extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        protected Object convertValue(FacesContext facesContext,
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

    private static class SortAlpha extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        protected Object convertValue(FacesContext facesContext,
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

    private static class SortAlphaIgnoreCase extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        protected Object convertValue(FacesContext facesContext,
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

    private static class SortDate extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        protected Object convertValue(FacesContext facesContext,
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

    private static class SortAction extends AbstractSortMethod {
        private static final String REVISION = "$Revision$";

        private final IServerActionListener listener;

        public SortAction(IServerActionListener listener) {
            this.listener = listener;
        }

        protected Object convertValue(FacesContext facesContext,
                DataColumnComponent component, Object value) {
            return value;
        }

        public Comparator getComparator() {
            // TODO Auto-generated method stub
            return null;
        }
    }

}
