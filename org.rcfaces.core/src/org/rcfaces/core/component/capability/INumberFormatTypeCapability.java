/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface INumberFormatTypeCapability {
    /**
     * 
     */
    int NUMBER_FORMAT_TYPE = 0;

    String NUMBER_FORMAT_TYPE_NAME = "number";

    /**
     * 
     */
    int INTEGER_FORMAT_TYPE = 1;

    String INTEGER_FORMAT_TYPE_NAME = "integer";

    /**
     * 
     */
    int CURRENCY_FORMAT_TYPE = 2;

    String CURRENCY_FORMAT_TYPE_NAME = "currency";

    /**
     * 
     */
    int PERCENT_FORMAT_TYPE = 3;

    String PERCENT_FORMAT_TYPE_NAME = "percent";

    int getNumberFormatType();

    void setNumberFormatType(int numberFormatType);

}
