/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPagerMessageCapability {
    String getMessage();

    void setMessage(String message);

    String getZeroResultMessage();

    void setZeroResultMessage(String message);

    String getOneResultMessage();

    void setOneResultMessage(String message);

    String getManyResultsMessage();

    void setManyResultsMessage(String message);
}
