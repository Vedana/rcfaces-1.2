/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SignedFilter extends AbstractPatternTask {
    

    private static final Log LOG = LogFactory.getLog(SignedFilter.class);

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9\\-]");

    protected Pattern getPattern(IClientValidatorContext context) {

        return NUMBER_PATTERN;
    }

}
