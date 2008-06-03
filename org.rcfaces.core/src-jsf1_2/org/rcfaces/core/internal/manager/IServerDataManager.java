/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.el.ValueExpression;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServerDataManager {
    Object setServerData(String name, Object data);

    void setServerData(String name, ValueExpression binding);
}
