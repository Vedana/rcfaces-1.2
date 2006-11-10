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
public interface IServerDataCapability {
    Object setServerData(String name, Object data);

    Object removeServerData(String name);

    Object getServerData(String name);

    String[] listServerDataKeys();

    int getServerDataCount();

    Map getServerDataMap();
}
