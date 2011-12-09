/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import java.io.Serializable;

import javax.faces.component.StateHelper;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.component.IDataMapAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentEngine {

    IComponentEngine copy();
    
    StateHelper getStateHelper();

    Object getProperty(Serializable attributeName, FacesContext facesContext);

    String getStringProperty(Serializable propertyName,
            FacesContext facesContext);

    void setProperty(Serializable propertyName, Object value);

    int getIntProperty(Serializable propertyName, int defaultValue,
            FacesContext facesContext);

    Object getLocalValue(Serializable propertyName);

    Object saveState(FacesContext currentInstance);

    IDataMapAccessor getDataMapAccessor(FacesContext context, String mapName,
            boolean create);

    void processDecodes(FacesContext context);

    void processValidation(FacesContext context);

    void processUpdates(FacesContext context);
}
