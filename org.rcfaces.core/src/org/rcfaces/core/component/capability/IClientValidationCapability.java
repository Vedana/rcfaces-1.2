/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidationCapability {
    String getClientValidator();

    void setClientValidator(String validator);
}
