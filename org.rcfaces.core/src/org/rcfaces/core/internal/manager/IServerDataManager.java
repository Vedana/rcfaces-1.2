/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
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

import org.rcfaces.core.component.capability.IServerDataCapability;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IServerDataManager extends IServerDataCapability {
    void setServerData(String name, ValueBinding binding);
}
