/*
 * $Id: IClientDataManager.java,v 1.2 2008/06/03 14:50:15 oeuillot Exp $
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.el.ValueExpression;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.2 $ $Date: 2008/06/03 14:50:15 $
 */
public interface IClientDataManager {
    String setClientData(String name, String value);

    void setClientData(String name, ValueExpression binding);
}
