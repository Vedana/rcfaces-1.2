/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.faces.el.ValueBinding;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServerDataManager {
    Object setServerData(String name, Object data);

    void setServerData(String name, ValueBinding binding);
}
