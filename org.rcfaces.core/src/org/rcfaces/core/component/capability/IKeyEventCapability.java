/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;


/**
 * 
 * Aggregates keyDown, keyUp and keyPressed capabilities
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyEventCapability extends IKeyPressEventCapability,
        IKeyUpEventCapability, IKeyDownEventCapability {

}
