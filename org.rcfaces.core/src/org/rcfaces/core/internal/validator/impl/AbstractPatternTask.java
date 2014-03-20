/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.IFilterTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractPatternTask extends AbstractClientValidatorTask
        implements IFilterTask {
    

    private static final Log LOG = LogFactory.getLog(AbstractPatternTask.class);

    protected abstract Pattern getPattern(IClientValidatorContext context);

    public boolean applyFilter(IClientValidatorContext context, char keyChar) {

        Pattern pattern = getPattern(context);

        return pattern.matcher(new StringAppender(keyChar)).matches();
    }
}
