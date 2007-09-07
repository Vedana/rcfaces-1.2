/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.IReleasable;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentEngine extends IReleasable,
        ITransientAttributesManager {

    void restoreState(FacesContext context, Object object);

    Object saveState(FacesContext context);

    boolean getBoolProperty(String propertyName, boolean defaultValue,
            FacesContext facesContext);

    void setProperty(String propertyName, boolean value);

    void setProperty(String propertyName, int value);

    void setProperty(String propertyName, double value);

    void setProperty(String propertyName, Integer value);

    void setProperty(String propertyName, Boolean value);

    void setProperty(String propertyName, Object value);

    void setProperty(String propertyName, ValueBinding dataSource);

    Boolean getBooleanProperty(String propertyName, FacesContext facesContext);

    Integer getIntegerProperty(String propertyName, FacesContext facesContext);

    int getIntProperty(String propertyName, int defaultValue,
            FacesContext facesContext);

    String getStringProperty(String propertyName, FacesContext facesContext);

    Object getProperty(String propertyName, FacesContext facesContext);

    ValueBinding getValueBindingProperty(String propertyName);

    // Converter getConverter(FacesContext facesContext);

    // void setConverter(Converter converter);

    // void setConverterId(ValueBinding converter);

    // void setConverterId(String converterId);

    boolean isPropertySetted(String propertyName);

    Object getValue(String valueName, FacesContext context);

    Object getLocalValue(String valueName);

    void setValue(String valueName, Object value);

    void setValueBinding(String valueName, ValueBinding valueBinding);

    void startDecodes(FacesContext context);

    void processUpdates(FacesContext context);

    IDataMapAccessor getDataMapAccessor(FacesContext context, String name,
            boolean modify);

    double getDoubleProperty(String propertyName, double value,
            FacesContext facesContext);

    IStateChildrenList createStateChildrenList();
}
