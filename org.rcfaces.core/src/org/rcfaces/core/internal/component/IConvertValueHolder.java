/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IConvertValueHolder extends ValueHolder {
	Converter getConverter(FacesContext facesContext);
}	
