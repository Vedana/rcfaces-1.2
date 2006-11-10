/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.service;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientService {
    String getClientServiceId();

    int getStatus();

    /**
     * 
     * @return 0 to 1000 (-1 unknown)
     */
    int getProgress();

    int getErrorCode();

    String getErrorMessage();

    void cancel();
}
