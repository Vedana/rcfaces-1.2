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
public class DPSFilter extends AbstractPatternTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(DPSFilter.class);

    private static final Pattern DPS_PATTERN = Pattern.compile("[\040-\177]");

    protected Pattern getPattern(IClientValidatorContext context) {
        return DPS_PATTERN;
    }

}
