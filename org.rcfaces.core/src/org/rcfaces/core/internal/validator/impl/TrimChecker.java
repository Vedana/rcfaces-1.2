/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.ICheckerTask;
import org.rcfaces.core.validator.IClientValidatorContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TrimChecker extends AbstractClientValidatorTask implements
        ICheckerTask {
    

    private static final Log LOG = LogFactory.getLog(TrimChecker.class);

    public String applyChecker(IClientValidatorContext context, String value) {
        if (value == null || value.length() == 0) {
            return value;
        }

        return value.trim();
    }
}
