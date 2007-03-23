/*
 * $Id$
 */
package org.rcfaces.core.util;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.lang.ClientStorage;
import org.rcfaces.core.lang.IClientStorage;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientStorageManager {
    private static final String REVISION = "$Revision$";

    private static final String CLIENT_STORAGE_PROPERTY = Constants
            .getPackagePrefix()
            + ".CLIENT_STORAGE";

    public static IClientStorage get(FacesContext facesContext, boolean create) {

        ExternalContext externalContext = facesContext.getExternalContext();

        if (externalContext.getSession(create) == null) {
            return null;
        }

        Map sessionMap = externalContext.getSessionMap();

        IClientStorage clientStorage = (IClientStorage) sessionMap
                .get(CLIENT_STORAGE_PROPERTY);

        if (clientStorage != null) {
            return clientStorage;
        }

        clientStorage = new ClientStorage();
        sessionMap.put(CLIENT_STORAGE_PROPERTY, clientStorage);

        return clientStorage;
    }
}
