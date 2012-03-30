/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.css;

import java.io.IOException;
import java.net.URLConnection;
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

    private static final Log LOG = LogFactory
            .getLog(StyleSheetSourceContainer.class);

    private static final String CSS_REPOSITORY_TYPE = "css";

    public StyleSheetSourceContainer(ServletConfig config, Set<String> modules,
            String charSet, boolean canUseGzip, boolean canUseETag,
            boolean canUseHash, String repositoryVersion)
            throws ServletException {
        super(config, CSS_REPOSITORY_TYPE, modules, charSet, canUseGzip,
                canUseETag, canUseHash, EXTERNAL_REPOSITORIES_CONFIG_NAME,
                repositoryVersion);
    }

    protected StringAppender preConstructBuffer(
            BasicParameterizedContent parameterizedBuffer, StringAppender buffer) {

        buffer.append("@charset \"").append(getCharSet()).append("\";\n");

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

    protected void addURLContent(URLConnection urlConnection,
            StringAppender buffer) throws IOException {

        StringAppender defferedBuffer = new StringAppender(32000);

        super.addURLContent(urlConnection, defferedBuffer);

        String db = defferedBuffer.toString();
        int idx = db.indexOf('\n');
        if (idx > 0) {
            String firstList = db.substring(0, idx).toLowerCase();
            if (firstList.startsWith("@charset")) {
                buffer.append(defferedBuffer, idx + 1, defferedBuffer.length()
                        - idx - 1);
                return;
            }
        }

        buffer.append(defferedBuffer);
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

        public String toString() {
            return "[UserAgentSourceFile fileName='" + getFileName()
                    + "' userAgent='" + getUserAgent() + "']";
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

        protected List<SourceFile> filterFiles(List<SourceFile> files) {

            List<SourceFile> l = super.filterFiles(files);

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
