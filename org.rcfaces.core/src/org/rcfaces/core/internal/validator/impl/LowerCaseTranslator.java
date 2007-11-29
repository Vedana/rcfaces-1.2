/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.ITranslatorTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LowerCaseTranslator extends AbstractPatternTask implements
        ITranslatorTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(LowerCaseTranslator.class);

    private static final Pattern LOWERCASE_PATTERN = Pattern
            .compile("[ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÑÓÔÕÖÒÙÚÛÜÝ]");

    protected Pattern getPattern(IClientValidatorContext context) {
        return LOWERCASE_PATTERN;
    }

    public char applyTranslator(IClientValidatorContext context, char keyChar) {
        int keyCode = keyChar;

        if (keyCode >= 65 && keyCode <= 90) {
            return (char) (keyCode + 32);
        }
        if (keyCode > 127 && applyFilter(context, keyChar)) {
            return (char) (keyCode + 32);
        }
        return keyChar;
    }

}
