/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Map;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDataCapability {
    String setClientData(String name, String data);

    String removeClientData(String name);

    String getClientData(String name);

    String[] listClientDataKeys();

    int getClientDataCount();

    Map getClientDataMap();
}
