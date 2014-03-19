/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.validator;

import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidatorsRegistry {
    IClientValidatorDescriptor getClientValidatorById(
            FacesContext facesContext, String clientValidatorId, Locale locale,
            TimeZone timeZone);
}
