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
 * Revision 1.1  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.8  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.7  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.6  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.5  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.4  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.3  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.tools;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.model.CollectionIndexesModel;
import org.rcfaces.core.model.DefaultSortedComponent;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.IndexesModels;
import org.rcfaces.core.model.MapIndexesModel;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GridTools {
    private static final String REVISION = "$Revision$";

    private static final IDataColumnIterator EMPTY_COMPONENT_ITERATOR = new DataColumnListIterator(
            Collections.EMPTY_LIST);

    private static final String ALL_INDEX = "all";

    private static final ISortedComponent[] SORTED_COMPONENTS_EMPTY_ARRAY = new ISortedComponent[0];

    public static IDataColumnIterator listColumns(DataGridComponent component) {
        List list = ComponentIterators.list(component,
                DataColumnComponent.class);
        if (list.isEmpty()) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        return new DataColumnListIterator(list);
    }

    private static final class DataColumnListIterator extends
            ComponentIterators.ComponentListIterator implements
            IDataColumnIterator {
        private static final String REVISION = "$Revision$";

        public DataColumnListIterator(List list) {
            super(list);
        }

        public final DataColumnComponent next() {
            return (DataColumnComponent) nextComponent();
        }

        public DataColumnComponent[] toArray() {
            return (DataColumnComponent[]) toArray(new DataColumnComponent[count()]);
        }

    }

    public static final IIndexesModel getIndexesModel(Object object,
            DataModel dataModel) {
        if (object == null) {
            return new ArrayIndexesModel();
        }

        if (object instanceof IIndexesModel) {
            return (IIndexesModel) object;
        }

        if (ALL_INDEX.equals(object)) {
            Object value = dataModel;

            if (value == null) {
                return new ArrayIndexesModel();
            }

            Class valueClass = value.getClass();
            if (valueClass.isArray()) {
                int size = Array.getLength(value);

                return IndexesModels.selectAll(size);
            }

            if (value instanceof Collection) {
                Collection collection = (Collection) value;

                return IndexesModels.selectAll(collection.size());
            }

            if (value instanceof Map) {
                Map collection = (Map) value;

                return IndexesModels.selectAll(collection.size());
            }

            throw new FacesException(
                    "'all' keyword for index model, does not support value type: '"
                            + value.getClass() + "'.");
        }

        if (object instanceof Collection) {
            return new CollectionIndexesModel((Collection) object);
        }

        if (object instanceof Map) {
            return new MapIndexesModel((Map) object);
        }

        List l = new ArrayList(1);
        l.add(object);

        return new CollectionIndexesModel(l);
    }

    public static final Object getFirst(DataModel dataModel, Object selection) {
        if (selection == null) {
            return null;
        }

        if (selection instanceof Object[]) {
            Object array[] = (Object[]) selection;

            if (array.length == 0) {
                return null;
            }

            return array[0];
        }

        if (selection instanceof Collection) {
            Collection collection = (Collection) selection;

            if (collection.isEmpty()) {
                return null;
            }

            return collection.iterator().next();
        }

        if (selection instanceof IIndexesModel) {
            IIndexesModel indexesModel = (IIndexesModel) selection;

            return indexesModel.getFirstSelectedObject(dataModel);
        }

        return selection;
    }

    public static int getCount(Object selection) {
        if (selection == null) {
            return 0;
        }

        if (selection instanceof Object[]) {
            Object array[] = (Object[]) selection;

            return array.length;
        }

        if (selection instanceof Collection) {
            Collection collection = (Collection) selection;

            return collection.size();
        }

        if (selection instanceof IIndexesModel) {
            IIndexesModel indexesModel = (IIndexesModel) selection;

            return indexesModel.countIndexes();
        }

        return 1;
    }

    public static ISortedComponent[] listSortedComponents(FacesContext context,
            DataGridComponent dataGridComponent) {

        String ids = dataGridComponent.getSortedColumnIds(context);
        if (ids == null) {
            return SORTED_COMPONENTS_EMPTY_ARRAY;
        }

        DataColumnComponent columns[] = dataGridComponent.listColumns()
                .toArray();
        StringTokenizer st = new StringTokenizer(ids, ",");
        List l = new ArrayList(st.countTokens());

        for (; st.hasMoreTokens();) {
            String id = st.nextToken();

            if (id.startsWith("#")) {
                int idx = Integer.parseInt(id.substring(1));

                DataColumnComponent column = columns[idx];

                l.add(new DefaultSortedComponent(column, idx, column
                        .isAscending(context)));
                continue;
            }

            for (int i = 0; i < columns.length; i++) {
                DataColumnComponent column = columns[i];

                if (id.equals(column.getId()) == false) {
                    continue;
                }

                l.add(new DefaultSortedComponent(column, i, column
                        .isAscending(context)));
                break;
            }
        }

        return (ISortedComponent[]) l.toArray(new ISortedComponent[l.size()]);
    }

    public static DataColumnComponent getSortedColumn(FacesContext context,
            DataGridComponent dataGridComponent) {

        String ids = dataGridComponent.getSortedColumnIds(context);
        if (ids == null) {
            return null;
        }

        DataColumnComponent columns[] = dataGridComponent.listColumns()
                .toArray();
        StringTokenizer st = new StringTokenizer(ids, ",");

        for (; st.hasMoreTokens();) {
            String id = st.nextToken();

            for (int i = 0; i < columns.length; i++) {
                DataColumnComponent column = columns[i];

                if (id.equals(column.getId()) == false) {
                    continue;
                }

                return column;
            }
        }

        return null;
    }

    public static boolean setSortedColumn(DataGridComponent component,
            DataColumnComponent dataColumn) {
        String id = dataColumn.getId();
        if (id == null || ComponentTools.isAnonymousComponentId(id)) {
            // On utilise l'index alors ...

            id = null;

            int idx = 0;
            for (IDataColumnIterator it = component.listColumns(); it.hasNext(); idx++) {
                DataColumnComponent column = it.next();

                if (column != dataColumn) {
                    continue;
                }

                id = "#" + idx;
                break;
            }

            if (id == null) {
                throw new FacesException("Can not get id of DataColumn !");
            }
        }

        component.setSortedColumnIds(id);

        return true;
    }

    public static DataModel getDataModel(Object current,
            FacesContext facesContext) {

        if (current == null) {
            return new ListDataModel(Collections.EMPTY_LIST);
        }

        if (current instanceof DataModel) {
            return (DataModel) current;
        }

        if (current instanceof List) {
            return new ListDataModel((List) current);
        }
        if (Object[].class.isAssignableFrom(current.getClass())) {
            return new ArrayDataModel((Object[]) current);
        }

        if (current instanceof ResultSet) {
            return new ResultSetDataModel((ResultSet) current);
        }

        if (current instanceof Result) {
            return new ResultDataModel((Result) current);
        }

        return new ScalarDataModel(current);
    }
}