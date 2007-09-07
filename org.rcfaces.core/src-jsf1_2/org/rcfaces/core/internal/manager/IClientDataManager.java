/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.el.ValueExpression;

import org.rcfaces.core.component.capability.IClientDataCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDataManager extends IClientDataCapability {
    void setClientData(String name, ValueExpression binding);
}
