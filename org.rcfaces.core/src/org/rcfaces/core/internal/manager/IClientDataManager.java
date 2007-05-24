/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClientDataCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDataManager extends IClientDataCapability {
    void setClientData(String name, ValueBinding binding);
}
