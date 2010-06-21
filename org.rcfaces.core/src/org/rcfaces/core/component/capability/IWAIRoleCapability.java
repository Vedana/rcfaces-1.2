/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IWAIRoleCapability {
    String getWaiRole();

    void setWaiRole(String role);
    
    int getAriaLevel();
    
    void setAriaLevel(int ariaLevel);
}
