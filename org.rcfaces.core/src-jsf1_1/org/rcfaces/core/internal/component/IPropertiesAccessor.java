/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertiesAccessor {

    boolean isPropertySetted(String propertyName);

    Object getProperty(String propertyName);

    Object setProperty(FacesContext context, String propertyName, Object value);

    void setProperty(FacesContext context, String propertyName,
            ValueBinding value);

    Object removeProperty(FacesContext context, String name);

    Object saveState(FacesContext context);

    IDeltaPropertiesAccessor restoreState(FacesContext context, Object state);

    void release();

    IDeltaPropertiesAccessor createDeltaPropertiesAccessor();

    Set keySet();

    int size();

}