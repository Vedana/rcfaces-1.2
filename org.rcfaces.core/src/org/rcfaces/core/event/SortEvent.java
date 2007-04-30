/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import java.util.Comparator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SortEvent extends ActionEvent {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -2421248938907618744L;

    private final Object dataModel;

    private Comparator sortComparator;

    private ISortConverter sortConverter;

    public SortEvent(UIComponent component, Object dataModel) {
        super(component);
        this.dataModel = dataModel;
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof ISortListener);
    }

    public void processListener(FacesListener listener) {
        ((ISortListener) listener).processSort(this);
    }

    public void setSortComparator(Comparator comparator) {
        this.sortComparator = comparator;
    }

    public Comparator getSortComparator() {
        return sortComparator;
    }

    public final ISortConverter getSortConverter() {
        return sortConverter;
    }

    public final void setSortConverter(ISortConverter converter) {
        this.sortConverter = converter;
    }

    public final Object getDataModel() {
        return dataModel;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface ISortConverter {
        Object convertValue(FacesContext facesContext, UIComponent component,
                Object value);
    }
}
