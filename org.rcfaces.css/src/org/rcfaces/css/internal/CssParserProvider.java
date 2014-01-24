/*
 * $Id$
 */
package org.rcfaces.css.internal;

import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssParserProvider extends AbstractProvider {

    private static final String CSS_PARSER_SERVICE_ID = "org.rcfaces.css.CSS_PARSER";

    @Override
    public String getId() {
        return CSS_PARSER_SERVICE_ID;
    }

}
