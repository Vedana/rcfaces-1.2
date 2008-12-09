/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.repository;

import java.io.BufferedInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.codec.SourceFilter;
import org.rcfaces.core.internal.lang.ByteBufferOutputStream;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.webapp.ExtendedHttpServlet;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class SourceContainer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SourceContainer.class);

    protected static final String EXTERNAL_REPOSITORIES_CONFIG_NAME = "external.repositories";

    private static final int BUFFER_INITIAL_SIZE = 16000;

    private final ServletConfig servletConfig;

    private final String charSet;

    private final boolean canUseGzip;

    private final boolean canUseETag;

    private final boolean canUseHash;

    private final Set modules;

    private final String localizedSuffixes[];

    private final String externalRepositoriesPropertyName;

    private final String repositoryVersion;

    private final String repositoryType;

    private byte[] sourceBuffer = null;

    private byte[] sourceBufferGZip = null;

    // private byte[] sourceBufferExternalGZip = null;

    private long lastModified = -1;

    private String etag = null;

    private String hash = null;

    public SourceContainer(ServletConfig config, String repositoryType,
            Set modules, String charSet, boolean canUseGzip,
            boolean canUseETag, boolean canUseHash,
            String externalRepositoriesPropertyName, String repositoryVersion)
            throws ServletException {
        this.servletConfig = config;
        this.repositoryType = repositoryType;
        this.canUseGzip = canUseGzip;
        this.canUseETag = canUseETag;
        this.canUseHash = canUseHash;
        this.charSet = charSet;
        this.modules = modules;
        this.repositoryVersion = repositoryVersion;
        this.externalRepositoriesPropertyName = externalRepositoriesPropertyName;

        this.localizedSuffixes = getLocalizedSuffixes(config);

        flush();
    }

    public final String getCharSet() {
        return charSet;
    }

    public final boolean isCanUseGzip() {
        return canUseGzip;
    }

    public final boolean isCanUseETag() {
        return canUseETag;
    }

    public final boolean isCanUseHash() {
        return canUseHash;
    }

    protected StringAppender postConstructBuffer(StringAppender buffer) {

        if (canSkipSpace()) {
            return new StringAppender(SourceFilter.filterSkipSpaces(buffer
                    .toString()));
        }

        if (canRemoveComments()) {
            return new StringAppender(SourceFilter.filter(buffer.toString()));
        }

        return buffer;
    }

    protected StringAppender preConstructBuffer(StringAppender buffer) {
        return buffer;
    }

    public String getVersion() {
        return repositoryVersion;
    }

    public byte[] getRawBuffer() throws ServletException {
        return sourceBuffer;
    }

    public byte[] getGZipedBuffer() throws ServletException {
        return sourceBufferGZip;
    }

    protected void updateLastModification(URLConnection urlConnection) {
        long lm = urlConnection.getLastModified();
        if (lm <= 0) {
            return;
        }

        if (lm <= lastModified) {
            return;
        }

        lastModified = lm;
    }

    protected URL getURL(String path) {
        for (int i = 0; i < localizedSuffixes.length; i++) {
            String p = path + localizedSuffixes[i];

            try {
                String pr = p;
                if (pr.charAt(0) != '/') {
                    pr = "/" + pr;
                }

                URL url = servletConfig.getServletContext().getResource(pr);
                if (url != null) {
                    try {
                        InputStream in = url.openStream();
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException ex) {
                                // Probleme � la fermeture ?
                            }

                            return url;
                        }
                    } catch (IOException ex) {
                        // Rien ...
                    }
                }

            } catch (MalformedURLException e) {
                // Rien ...
            }

            String pr = p;
            if (pr.charAt(0) == '/') {
                pr = pr.substring(1);
            }
            URL url = getClass().getClassLoader().getResource(pr);
            if (url != null) {
                try {
                    InputStream in = url.openStream();
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            // Probleme � la fermeture ?
                        }

                        return url;
                    }
                } catch (IOException ex) {
                    // Rien ...
                }
            }
        }

        return null;
    }

    protected String[] getLocalizedSuffixes(ServletConfig config) {
        Locale locale = Locale.getDefault();

        List l = new ArrayList();

        if (locale != null) {
            String language = locale.getLanguage();
            if (language != null && language.length() > 0) {
                String country = locale.getCountry();
                if (country != null && country.length() > 0) {
                    String variant = locale.getVariant();
                    if (variant != null && variant.length() > 0) {
                        l.add("_" + language + "_" + country + "_" + variant);
                    }

                    l.add("_" + language + "_" + country);
                }
                l.add("_" + language);
            }
        }

        l.add("");

        return (String[]) l.toArray(new String[l.size()]);
    }

    protected void addURLContent(StringAppender buffer, URL url)
            throws IOException {

        URLConnection urlConnection = url.openConnection();

        LOG.debug("Load URL content '" + url + "' (charset=" + charSet + ").");

        updateLastModification(urlConnection);

        InputStream inputStream = urlConnection.getInputStream();

        try {
            char buf[] = new char[4096];

            InputStreamReader inr = new InputStreamReader(
                    new BufferedInputStream(inputStream, buf.length), charSet);

            for (;;) {
                int len = inr.read(buf, 0, buf.length);
                if (len < 1) {
                    break;
                }

                buffer.append(buf, 0, len);
            }
        } finally {
            inputStream.close();
        }
    }

    private List listExternalFiles(InputStream inputStream, String source,
            boolean nameAttribute) {

        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);

        digester.setEntityResolver(new EntityResolver() {
            private static final String REVISION = "$Revision$";

            public InputSource resolveEntity(String string, String string1) {
                return new InputSource(new CharArrayReader(new char[0]));
            }

        });

        final List list = new ArrayList();

        if (nameAttribute) {
            final String[] baseDirectoryRef = new String[1];

            digester.addRule("repository", new Rule() {

                public void begin(String namespace, String name,
                        Attributes attributes) throws Exception {

                    String baseDirectory = attributes.getValue("baseDirectory");

                    if (baseDirectory != null && baseDirectory.length() > 0) {
                        baseDirectoryRef[0] = baseDirectory;
                    }
                }
            });

            digester.addRule("repository/module/file", new Rule() {

                public void begin(String namespace, String name,
                        Attributes attributes) throws Exception {

                    String fileName = attributes.getValue("name");

                    if (fileName != null && fileName.length() > 0) {
                        if (baseDirectoryRef[0] != null) {
                            fileName = baseDirectoryRef[0] + "/" + fileName;
                        }
                        list.add(fileName);
                    }
                }

            });

        } else {
            digester.addRule("repository/file", new Rule() {

                public void body(String namespace, String name, String text)
                        throws Exception {
                    if (text != null && text.length() > 0) {
                        list.add(text);
                    }
                }
            });
        }

        try {
            digester.parse(inputStream);

        } catch (Exception e) {
            LOG.error("Can not parse '" + source + "'", e);
        }

        return list;
    }

    protected boolean canSkipSpace() {
        return false;
    }

    protected boolean canRemoveComments() {
        return false;
    }

    public synchronized void flush() throws ServletException {
        sourceBuffer = null;
        sourceBufferGZip = null;
        lastModified = -1;
        etag = null;
        hash = null;

        StringAppender buffer = preConstructBuffer(new StringAppender(
                BUFFER_INITIAL_SIZE));
        buffer = addRepositoryFiles(buffer);
        buffer = addExternalRepositories(buffer,
                externalRepositoriesPropertyName);
        buffer = postConstructBuffer(buffer);
        initializeBuffers(buffer);
    }

    protected void initializeBuffers(StringAppender sb) throws ServletException {

        try {
            sourceBuffer = sb.getBytes(getCharSet());

        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
            throw new ServletException(e);
        }

        if (lastModified <= 0) {
            lastModified = System.currentTimeMillis();
        }

        if (canUseETag) {
            etag = ExtendedHttpServlet.computeETag(sourceBuffer);
        }

        if (canUseHash) {
            hash = ExtendedHttpServlet.computeHash(sourceBuffer);
        }

        if (canUseGzip && sourceBuffer.length > 0) {
            try {
                ByteBufferOutputStream bos = new ByteBufferOutputStream(
                        sourceBuffer.length);
                GZIPOutputStream gzos = new GZIPOutputStream(bos,
                        sourceBuffer.length);
                gzos.write(sourceBuffer);
                gzos.close();

                sourceBufferGZip = bos.toByteArray();

                bos.close();

            } catch (IOException ex) {
                throw new ServletException("Can not create GZIP buffer for "
                        + repositoryType + " files.", ex);
            }
        }

        if (LOG.isInfoEnabled()) {
            String message = repositoryType + ": buffers loaded into "
                    + sourceBuffer.length + " bytes";
            if (sourceBufferGZip != null) {
                message += " (GZiped: " + sourceBufferGZip.length + " ["
                        + (sourceBufferGZip.length * 100 / sourceBuffer.length)
                        + "%])";
            }

            LOG.info(message, null);
        }
    }

    protected final StringAppender parseXMLRepository(StringAppender buffer,
            String path, boolean repositoryFormal) {
        URL url = getURL(path);
        if (url == null) {
            error("Can not get URL for path '" + path + "'.", null);
            return buffer;
        }

        URLConnection urlConnection;
        try {
            urlConnection = url.openConnection();

        } catch (IOException ex) {
            error("Can not get content of '" + url + "'.", ex);

            return buffer;
        }

        if (urlConnection == null) {
            return buffer;
        }

        updateLastModification(urlConnection);

        InputStream inputStream;
        try {
            inputStream = urlConnection.getInputStream();

        } catch (IOException ex) {
            error("Can not open '" + url + "'.", ex);

            return buffer;
        }

        if (inputStream == null) {
            return buffer;
        }

        List files;
        try {
            files = listExternalFiles(inputStream, path, repositoryFormal);

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }

        if (files == null || files.isEmpty() == true) {
            return buffer;
        }

        for (Iterator it = files.iterator(); it.hasNext();) {
            String filePath = (String) it.next();

            URL fileURL = getURL(filePath);
            if (fileURL == null) {
                error("Can not get URL for path '" + filePath + "'.", null);
                continue;
            }

            try {
                addURLContent(buffer, fileURL);

            } catch (IOException e) {
                error("Can not append external file '" + fileURL + "'.", e);
            }
        }

        return buffer;
    }

    protected final StringAppender addExternalRepositories(
            StringAppender buffer, String propertName) {
        if (propertName == null) {
            return buffer;
        }

        String path = servletConfig.getInitParameter(propertName);
        if (path == null) {
            return buffer;
        }

        StringTokenizer st = new StringTokenizer(path, ";,");
        for (; st.hasMoreTokens();) {
            String repositoryPath = st.nextToken();

            parseXMLRepository(buffer, repositoryPath, false);
        }

        return buffer;
    }

    protected StringAppender addRepositoryFiles(StringAppender buffer)
            throws ServletException {

        RcfacesContext rcfacesContext = RcfacesContext.getInstance(
                servletConfig.getServletContext(), null, null);

        String repositoriesPaths[] = rcfacesContext.getRepositoryManager()
                .listRepositoryLocations(repositoryType);

        for (int j = 0; j < repositoriesPaths.length; j++) {

            String repositoryPath = repositoriesPaths[j];

            parseXMLRepository(buffer, repositoryPath, true);
        }

        return buffer;
    }

    protected void error(String message, Throwable th) {
        message = "RCFaces.SourceRepository: " + message;

        LOG.error(message, th);

        if (th == null) {
            servletConfig.getServletContext().log(message);
            return;
        }

        servletConfig.getServletContext().log(message, th);
    }

    public long getLastModified() throws ServletException {
        return lastModified;
    }

    public String getETag() throws ServletException {
        return etag;
    }

    public String getHash() throws ServletException {
        return hash;
    }
}
