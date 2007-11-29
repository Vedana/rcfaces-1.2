/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.IFilterTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NoBlankFilter implements IFilterTask {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(NoBlankFilter.class);

    public boolean applyFilter(IClientValidatorContext context, char keyChar) {

        return keyChar != ' ';
    }

}
