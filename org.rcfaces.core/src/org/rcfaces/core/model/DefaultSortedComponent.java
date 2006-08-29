/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 */
package org.rcfaces.core.model;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class DefaultSortedComponent implements ISortedComponent {
    private static final String REVISION = "$Revision$";

    private final UIComponent component;

    private final int index;

    private final int sortMode;

    public DefaultSortedComponent(UIComponent component, int index, int sortMode) {
        this.component = component;
        this.index = index;
        this.sortMode = sortMode;
    }

    public DefaultSortedComponent(UIComponent component, int index,
            boolean sortOrder) {
        this(component, index, (sortOrder) ? ASCENDING : DESCENDING);
    }

    public UIComponent getComponent() {
        return component;
    }

    public int getIndex() {
        return index;
    }

    public int getSortMode() {
        return sortMode;
    }

    public final boolean isAscending() {
        return getSortMode() == ASCENDING;
    }

}
