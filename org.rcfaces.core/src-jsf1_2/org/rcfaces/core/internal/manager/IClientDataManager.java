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
public interface IClientDataManager {
    String setClientData(String name, String value);

    void setClientData(String name, ValueExpression binding);
}
