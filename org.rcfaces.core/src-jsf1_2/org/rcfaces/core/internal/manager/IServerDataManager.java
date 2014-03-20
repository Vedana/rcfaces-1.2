/*
 * $Id: IServerDataManager.java,v 1.2 2008/06/03 14:50:22 oeuillot Exp $
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.el.ValueExpression;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.2 $ $Date: 2008/06/03 14:50:22 $
 */
public interface IServerDataManager {
    Object setServerData(String name, Object data);

    void setServerData(String name, ValueExpression binding);
}
