/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataMapAccessor {

    Object getData(String name, FacesContext facesContext);

    ValueExpression getValueExpression(String key);

    Object setData(String name, Object data, FacesContext facesContext);

    void setData(String name, ValueExpression value, FacesContext facesContext);

    Object removeData(String name, FacesContext facesContext);

    void clearDatas(FacesContext facesContext);

    int getDataCount();

    String[] listDataKeys(FacesContext facesContext);

    Map<String, Object> getDataMap(FacesContext facesContext);
}
