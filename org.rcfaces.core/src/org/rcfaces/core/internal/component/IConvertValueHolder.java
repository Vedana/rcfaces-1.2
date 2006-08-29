/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.component;

import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IConvertValueHolder extends ValueHolder {
	Converter getConverter(FacesContext facesContext);
}	
