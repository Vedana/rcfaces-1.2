/*
 * $Id$
 */
package org.rcfaces.core.internal.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.validator.IParameter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServerConverter {

    String getId();

    String getClassName();

    IParameter[] listParameters();

    Converter getInstance(FacesContext facesContext, UIComponent component);
}
