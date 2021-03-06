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
public class NumTranslator extends AbstractClientValidatorTask implements
        ITranslatorTask {
    

    private static final Log LOG = LogFactory.getLog(NumTranslator.class);

    public char applyTranslator(IClientValidatorContext context, char keyChar) {

        String separators = getParameter(context, "num.decSign");
        if (separators == null || separators.length() < 1) {
            return keyChar;
        }

        if (separators.indexOf(keyChar) >= 0) {
            return separators.charAt(0);
        }

        return keyChar;
    }
}
