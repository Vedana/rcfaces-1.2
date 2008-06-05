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
public interface IClientDataManager {
    String setClientData(String name, String data);

    void setClientData(String name, ValueBinding binding);
}
