/*
 * $Id$
 */
package org.rcfaces.core.validator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFormatterTask extends IClientValidatorTask {

    String applyFormatter(IClientValidatorContext context, String value);
}
