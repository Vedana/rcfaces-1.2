/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.io.Serializable;
import java.util.Map;

import javax.faces.component.StateHolder;

import org.rcfaces.core.internal.renderkit.IProperties;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFilterProperties extends IProperties, Serializable,
        StateHolder {

    Object put(String propertyName, Object value);

    Object remove(String propertyName);

    String[] listNames();

    void clear();

    boolean isEmpty();

    int size();

    void putAll(Map map);

    Map toMap();
}
