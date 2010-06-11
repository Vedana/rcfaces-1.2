/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPartialRenderingCapability {
    boolean isPartialRendering();

    void setPartialRendering(boolean partialRendering);
}
