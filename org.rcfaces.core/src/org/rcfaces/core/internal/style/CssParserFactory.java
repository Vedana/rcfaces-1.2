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

    private static final String STEADY_STATE_PARSER_CLASSNAME = "org.rcfaces.css.internal.CssSteadyStateParser";

    public static final ICssParser getCssParser() {
        Class clazz = null;

        try {
            LOG.debug("Try CSS steady state parser ...");

            clazz = CssParserFactory.class.getClassLoader().loadClass(
                    STEADY_STATE_PARSER_CLASSNAME);

        } catch (Throwable ex) {
            LOG.trace(ex);
        }

        if (clazz == null) {
            LOG.info("No known css parsers found.");

            return null;
        }

        LOG.info("Instanciate css parser '" + clazz.getName() + "' ...");

        try {
            return (ICssParser) clazz.newInstance();

        } catch (Throwable e) {
            LOG.error("Can not instanciate css parser '" + clazz.getName()
                    + "'.", e);

            return null;
        }
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
                IParserContext mergeContext) throws IOException;

        /**
         * 
         * @author Olivier Oeuillot (latest modification by $Author$)
         * @version $Revision$ $Date$
         */
        public interface IParserContext {
            String getCharset();

            void setCharset(String charset);

            long getLastModifiedDate();

            void setLastModifiedDate(long lastModifiedDate);
        }
    }

}
