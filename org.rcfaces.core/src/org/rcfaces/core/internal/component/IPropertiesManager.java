/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertiesManager {

    void releaseManager();

    void commitManager(FacesContext context);

    IPropertiesAccessor getPropertiesAccessor(boolean enableDelta,
            boolean forceDelta);

    void restoreManagerState(FacesContext context, Object props);

    Object saveManagerState(FacesContext context);

    IPropertiesManager copyOriginalState(FacesContext facesContext);

}
