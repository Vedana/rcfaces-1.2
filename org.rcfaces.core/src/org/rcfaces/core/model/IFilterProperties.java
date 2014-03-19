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

<<<<<<< HEAD
    Object put(Serializable propertyName, Object value);
=======
	Object put(String propertyName, Object value);
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0

<<<<<<< HEAD
    Object remove(Serializable propertyName);
=======
	Object remove(String propertyName);
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0

	String[] listNames();

	void clear();

	boolean isEmpty();

	int size();

	void putAll(Map<String, Object> map);

	Map<String, Object> toMap();
}
