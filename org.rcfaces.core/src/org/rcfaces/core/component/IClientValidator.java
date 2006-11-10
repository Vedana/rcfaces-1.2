/*
 * $Id$
 * 
 */
package org.rcfaces.core.component;

import javax.faces.validator.Validator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidator extends Validator {
    String getExpression();
}
