/*
 * $Id$
 */
package org.rcfaces.core.component.capability;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPagedCapability {

    /**
     * Returns a boolean value specifying wether the results should be displayed
     * by page (thus letting the user choose what page to display via the
     * pager). It is ignored if the attribute "rows" is undefined. The default
     * value is true. If "rows" is defined and "paged"'s value is set to false,
     * pages are downloaded automatically when the last displayed row is
     * selected.
     * 
     * @return true if display by page
     */
    boolean isPaged();

    /**
     * Sets a boolean value specifying wether the results should be displayed by
     * page (thus letting the user choose what page to display via the pager).
     * It is ignored if the attribute "rows" is undefined. The default value is
     * true. If "rows" is defined and "paged"'s value is set to false, pages are
     * downloaded automatically when the last displayed row is selected.
     * 
     * @param paged
     *            true if display by page
     */
    void setPaged(boolean paged);
    
    /**
     * 
     */
    boolean isPagedSetted();
}
