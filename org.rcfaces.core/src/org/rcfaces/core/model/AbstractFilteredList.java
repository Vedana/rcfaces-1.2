/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFilteredList extends AbstractFilteredCollection
        implements List {
    private static final String REVISION = "$Revision$";

    public static final List EMPTY_LIST;
    static {
        EMPTY_LIST = new AbstractFilteredList(Collections.EMPTY_LIST) {
            private static final String REVISION = "$Revision$";

            protected boolean accept(IFilterProperties filter,
                    SelectItem selectItem) {
                return false;
            }
        };
    }

    public AbstractFilteredList() {
        super(new ArrayList());
    }

    public AbstractFilteredList(List list) {
        super(list);
    }

    public AbstractFilteredList(SelectItem selectItem[]) {
        super(selectItem);
    }

    public Object get(int index) {
        return ((List) collection).get(index);
    }

    public Object remove(int index) {
        return ((List) collection).remove(index);
    }

    public void add(int index, Object element) {
        ((List) collection).add(index, element);
    }

    public int indexOf(Object o) {
        return ((List) collection).indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return ((List) collection).lastIndexOf(o);
    }

    public boolean addAll(int index, Collection c) {
        return ((List) collection).addAll(index, c);
    }

    public List subList(int fromIndex, int toIndex) {
        return ((List) collection).subList(fromIndex, toIndex);
    }

    public ListIterator listIterator() {
        return ((List) collection).listIterator();
    }

    public ListIterator listIterator(int index) {
        return ((List) collection).listIterator(index);
    }

    public Object set(int index, Object element) {
        return ((List) collection).set(index, element);
    }

}
