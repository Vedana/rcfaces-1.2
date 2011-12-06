/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Iterator;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFiltredCollection2<T> {
	Iterator<T> iterator(UIComponent component,
			IFilterProperties filterProperties, int maximumResultNumber);

}
