/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.css;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.repository.SourceContainer;
import org.rcfaces.renderkit.html.internal.IClientBrowser;
import org.xml.sax.Attributes;

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

    protected StringAppender preConstructBuffer(
            BasicParameterizedContent parameterizedBuffer, StringAppender buffer) {

        buffer.append("@charset \"").append(getCharSet()).append("\";");

        return buffer;
    }

    public IParameterizedContent getContent(IClientBrowser clientBrowser)
            throws ServletException {
        return getContent(clientBrowser.getBrowserId());
    }

    protected SourceFile createSourceFile(String baseDirectory, String body,
            Attributes attributes) {

        UserAgentSourceFile sf = (UserAgentSourceFile) super.createSourceFile(
                baseDirectory, body, attributes);
        if (sf == null) {
            return null;
        }

        String ua = attributes.getValue("userAgent");
        if (ua != null) {
            sf.setUserAgent(ua);
        }

        return sf;
    }

    protected SourceFile newSourceFile() {
        return new UserAgentSourceFile();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class UserAgentSourceFile extends SourceFile {
        private String userAgent;

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

    }

    protected IParameterizedContent createParameterizedContent(String parameter) {
        return new UserAgentContent(parameter);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class UserAgentContent extends BasicParameterizedContent {

        public UserAgentContent(String parameter) {
            super(parameter);
        }

        protected List filterFiles(List files) {

            List l = super.filterFiles(files);

            if (parameter == null) {
                return l;
            }

            for (Iterator it = l.iterator(); it.hasNext();) {
                UserAgentSourceFile sf = (UserAgentSourceFile) it.next();

                String ua = sf.getUserAgent();

                if (ua == null || parameter.equals(ua)) {
                    continue;
                }

                it.remove();
            }

            return l;
        }
    }
}
