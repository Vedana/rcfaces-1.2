/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.ITranslatorTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DateTranslator extends AbstractClientValidatorTask implements
        ITranslatorTask {
    

    private static final Log LOG = LogFactory.getLog(DateTranslator.class);

    public char applyTranslator(IClientValidatorContext context, char keyChar) {

        String separators = getParameter(context, "date.sepSign");
        if (separators == null || separators.length() < 1) {
            return keyChar;
        }

        if (separators.indexOf(keyChar) >= 0) {
            return separators.charAt(0);
        }

        return keyChar;
    }
}
