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
public class AlphaFrFilter extends AlphaFilter {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AlphaFrFilter.class);

    private static final String LATIN_ACCENT_FR = "àäâéèëêïîöôùüûÿçÀÄÂÉÈËÊÏÎÖÔÙÜÛÇ";

    protected void appendRegExp(StringAppender sa) {
        super.appendRegExp(sa);

        sa.append(LATIN_ACCENT_FR);
    }

}
