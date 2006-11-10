/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataMapAccessor {

    Object getData(String name, FacesContext facesContext);

    Object setData(String name, Object data, FacesContext facesContext);

    void setData(String name, ValueBinding value, FacesContext facesContext);

    Object removeData(String name, FacesContext facesContext);

    int getDataCount();

    String[] listDataKeys(FacesContext facesContext);

    Map getDataMap(FacesContext facesContext);
}
