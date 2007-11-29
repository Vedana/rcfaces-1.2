/*
 * $Id$
 */
package org.rcfaces.core.internal.validator.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AlphaNumFilter extends AlphaFilter {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AlphaNumFilter.class);

    private static final String NUM = "0-9";

    protected void appendRegExp(StringAppender sa) {
        super.appendRegExp(sa);

        sa.append(NUM);
    }

}
