/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.css;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.repository.SourceContainer;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleSheetSourceContainer extends SourceContainer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(StyleSheetSourceContainer.class);

    private static final String CSS_REPOSITORY_TYPE = "css";

    public StyleSheetSourceContainer(ServletConfig config, Set modules,
            String charSet, boolean canUseGzip, boolean canUseETag,
            boolean canUseHash, String repositoryVersion)
            throws ServletException {
        super(config, CSS_REPOSITORY_TYPE, modules, charSet, canUseGzip,
                canUseETag, canUseHash, EXTERNAL_REPOSITORIES_CONFIG_NAME,
                repositoryVersion);
    }

    protected StringAppender preConstructBuffer(StringAppender buffer) {

        buffer.append("@charset \"").append(getCharSet()).append("\";");

        return buffer;
    }
}
