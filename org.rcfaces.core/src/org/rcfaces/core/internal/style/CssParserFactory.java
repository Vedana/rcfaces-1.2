/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssParserFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CssParserFactory.class);

    private static final String CSSOMPARSER = "com.steadystate.css.parser.CSSOMParser";

    public static final ICssParser getCssParser() {
        try {
            CssParserFactory.class.getClassLoader().loadClass(CSSOMPARSER);

            LOG.info("Enable 'SteadyState' css parser.");

            return new CssSteadyStateParser();

        } catch (Exception ex) {
            LOG.trace(ex);
        }

        LOG.info("No css parser found.");

        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface ICssParser {
        String getParserName();

        String mergesBuffer(IResourceLoaderFactory resourceLoaderFactory,
                String styleSheetURL, String styleSheetBuffer,
                String defaultCharSet) throws IOException;
    }
}
