/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.rcfaces.core.component.capability.IClientDataCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientFacesMessage extends FacesMessage implements
        IClientDataCapability {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 4456702905941225305L;

    private Map clientDataMap;

    public ClientFacesMessage() {
        super();
    }

    public ClientFacesMessage(Severity severity, String summary, String detail) {
        super(severity, summary, detail);
    }

    public ClientFacesMessage(String summary, String detail) {
        super(summary, detail);
    }

    public ClientFacesMessage(String summary) {
        super(summary);
    }

    public String getClientData(String name) {
        if (clientDataMap == null) {
            return null;
        }

        return (String) clientDataMap.get(name);
    }

    public int getClientDataCount() {
        if (clientDataMap == null) {
            return 0;
        }

        return clientDataMap.size();
    }

    public Map getClientDataMap() {
        if (clientDataMap == null) {
            return Collections.EMPTY_MAP;
        }
        return new HashMap(clientDataMap);
    }

    public String[] listClientDataKeys() {
        if (clientDataMap == null || clientDataMap.isEmpty()) {
            return new String[] {};
        }

        Collection keys = clientDataMap.keySet();

        return (String[]) keys.toArray(new String[keys.size()]);
    }

    public String removeClientData(String name) {
        if (clientDataMap == null) {
            return null;
        }

        return (String) clientDataMap.remove(name);
    }

    public String setClientData(String name, String data) {
        if (clientDataMap == null) {
            return null;
        }

        return (String) clientDataMap.put(name, data);
    }

}
