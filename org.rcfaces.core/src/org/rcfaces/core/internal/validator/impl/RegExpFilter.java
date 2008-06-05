/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RegExpFilter extends AbstractDynamicPatternTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(RegExpFilter.class);

    private final String expression;

    public RegExpFilter(String expression) {
        this.expression = expression;
    }

    protected String getRegularExpression(IClientValidatorContext context) {
        return expression;
    }
}
