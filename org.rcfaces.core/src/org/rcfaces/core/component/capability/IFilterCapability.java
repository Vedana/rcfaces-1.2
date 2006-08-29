/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IFilterCapability {
    IFilterProperties getFilterProperties();

    void setFilterProperties(IFilterProperties properties);
}
