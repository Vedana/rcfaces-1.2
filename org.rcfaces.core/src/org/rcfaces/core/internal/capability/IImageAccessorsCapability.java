/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.familly.IContentAccessors;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageAccessorsCapability {
    IContentAccessors getImageAccessors(FacesContext facesContext);
}
