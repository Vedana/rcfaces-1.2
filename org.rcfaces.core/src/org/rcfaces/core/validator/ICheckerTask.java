/*
 * $Id$
 */
package org.rcfaces.core.validator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckerTask extends IClientValidatorTask {

    String applyChecker(IClientValidatorContext context, String value);
}
