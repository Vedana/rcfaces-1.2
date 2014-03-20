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
public class INSEEFilter extends AbstractPatternTask {
    

    private static final Log LOG = LogFactory.getLog(INSEEFilter.class);

    private static final Pattern INSEE_PATTERN = Pattern.compile("[0-9aAbB]");

    protected Pattern getPattern(IClientValidatorContext context) {
        return INSEE_PATTERN;
    }

}
