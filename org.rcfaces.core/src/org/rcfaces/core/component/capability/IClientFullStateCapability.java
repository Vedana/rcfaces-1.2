/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientFullStateCapability {

    int NONE_CLIENT_FULL_STATE = 0;

    int ONEWAY_CLIENT_FULL_STATE = 1;

    int TWOWAYS_CLIENT_FULL_STATE = 2;

    int DEFAULT_CLIENT_FULL_STATE = NONE_CLIENT_FULL_STATE;

    /**
     * @deprecated Replaced by ONEWAY_CLIENT_FULL_STATE
     */
    int TRUE_CLIENT_FULL_STATE = ONEWAY_CLIENT_FULL_STATE;

    /**
     * @deprecated Replaced by NONE_CLIENT_FULL_STATE
     */
    int FALSE_CLIENT_FULL_STATE = NONE_CLIENT_FULL_STATE;
}
