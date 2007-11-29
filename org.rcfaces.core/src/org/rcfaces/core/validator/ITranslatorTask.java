/*
 * $Id$
 */
package org.rcfaces.core.validator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITranslatorTask extends IClientValidatorTask {

    char applyTranslator(IClientValidatorContext context, char keyChar);
}
