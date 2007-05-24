/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.validator;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.rcfaces.core.internal.renderkit.IRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidatorsRegistry {
    IClientValidatorDescriptor getClientValidatorById(
            FacesContext facesContext, String clientValidatorId);

    String convertFromValidatorToExpression(IRenderContext renderContext,
            Validator validator);
}
