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
public class NameFilter extends AbstractPatternTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(NameFilter.class);

    private static final Pattern NAME_PATTERN = Pattern
            .compile("[ a-zA-Z0-9\\*\\.\\-]");

    protected Pattern getPattern(IClientValidatorContext context) {
        return NAME_PATTERN;
    }

}
