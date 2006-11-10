/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IServerDataCapability;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServerDataManager extends IServerDataCapability {
    void setServerData(String name, ValueBinding binding);
}
