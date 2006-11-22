/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelected3StatesCapability {

    String NOT_SELECTED_STATE = "notSelected";

    String SELECTED_STATE = "selected";

    String UNDETERMINATED_STATE = "undeterminated";

    /**
     * 
     */
    String getSelectedState();

    /**
     * 
     */
    void setSelectedState(String selectedState);

    boolean isSelected();

    boolean isUndeterminated();
}
