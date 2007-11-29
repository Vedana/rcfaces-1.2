/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.regex.Pattern;

import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.ITranslatorTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UpperCaseTranslator extends AbstractPatternTask implements
        ITranslatorTask {

    private static final Pattern UPPERCASE_PATTERN = Pattern
            .compile("[áãàâäåçéèêëíìîïñóõòôöúùûüý]");

    protected Pattern getPattern(IClientValidatorContext context) {
        return UPPERCASE_PATTERN;
    }

    public char applyTranslator(IClientValidatorContext context, char keyChar) {
        int keyCode = keyChar;

        if (keyCode >= 97 && keyCode <= 122) {
            return (char) (keyCode - 32);
        }
        if (keyCode > 127 && applyFilter(context, keyChar)) {
            return (char) (keyCode - 32);
        }

        return keyChar;
    }

}
