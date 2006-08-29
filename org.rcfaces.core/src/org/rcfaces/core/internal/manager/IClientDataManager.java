/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.internal.manager;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClientDataCapability;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IClientDataManager extends IClientDataCapability {
    void setClientData(String name, ValueBinding binding);
}
