/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.13  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.12  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.11  2005/11/08 12:16:26  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.10  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.9  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.8  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.7  2005/01/04 13:02:51  oeuillot
 * Amelioration des tables. (Ajout des tris, scrollbars ...)
 *
 * Revision 1.6  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.5  2004/09/29 20:49:38  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.webapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.codec.SourceFilter;
import org.rcfaces.core.internal.codec.StringAppender;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class SourceRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SourceRepository.class);

    protected static final String EXTERNAL_REPOSITORIES_CONFIG_NAME = "external.repositories";

    private final ServletConfig servletConfig;

    private final String charSet;

    private final String allSourcesURI;

    private final boolean canUseGzip;

    private final boolean canUseETag;

    private final boolean canUseHash;

    private final Set modules;

    private final String localizedSuffixes[];

    private final String externalRepositoriesPropertyName;

    private final String repositoryVersion;

    private StringAppender buffers;

    private List urls = new ArrayList(32);

    private byte[] sourceBuffer = null;

    private byte[] sourceBufferGZip = null;

    private byte[] sourceBufferExternalGZip = null;

    private long lastModified = -1;

    private String etag = null;

    private String hash = null;

    public SourceRepository(ServletConfig config, String allSourcesURI,
            Set modules, String charSet, boolean canUseGzip,
            boolean canUseETag, boolean canUseHash,
            String externalRepositoriesPropertyName, String repositoryVersion)
            throws ServletException {
        this.servletConfig = config;
        this.allSourcesURI = allSourcesURI;
        this.canUseGzip = canUseGzip;
        this.canUseETag = canUseETag;
        this.canUseHash = canUseHash;
        this.charSet = charSet;
        this.buffers = new StringAppender(16000);
        this.modules = modules;
        this.repositoryVersion = repositoryVersion;
        this.externalRepositoriesPropertyName = externalRepositoriesPropertyName;

        this.localizedSuffixes = getLocalizedSuffixes(config);

        URL url = getURL(allSourcesURI + ".gz");
        if (url != null) {
            loadExternalGZip(url);
        }

        addCameliaFiles();
        addExternalRepositories(externalRepositoriesPropertyName);
    }

    public String getVersion() {
        return repositoryVersion;
    }

    public byte[] getRawBuffer() throws ServletException {
        if (sourceBuffer == null) {
            initializeBuffers();
        }
        return sourceBuffer;
    }

    public byte[] getGZipedBuffer() throws ServletException {
        if (sourceBufferExternalGZip != null) {
            return sourceBufferExternalGZip;
        }

        if (sourceBuffer == null) {
            initializeBuffers();
        }
        return sourceBufferGZip;
    }

    protected abstract String getSourceType();

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
                URL url = servletConfig.getServletContext().getResource(p);
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

            URL url = getClass().getClassLoader().getResource(p);
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

    protected void registerURL(URL url) {
        urls.add(url);
    }

    protected void addURLContent(URL url) throws IOException {

        URLConnection urlConnection = url.openConnection();

        LOG.debug("Load URL content '" + url + "' (charset=" + charSet + ").");

        updateLastModification(urlConnection);

        InputStream inputStream = urlConnection.getInputStream();

        try {
            char buffer[] = new char[4096];

            InputStreamReader inr = new InputStreamReader(
                    new BufferedInputStream(inputStream, buffer.length),
                    charSet);

            for (;;) {
                int len = inr.read(buffer, 0, buffer.length);
                if (len < 1) {
                    break;
                }

                buffers.append(buffer, 0, len);
            }
        } finally {
            inputStream.close();
        }
    }

    private List listExternalFiles(InputStream inputStream, String source) {

        DocumentBuilder parser;

        // Create and return a new parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        try {
            parser = factory.newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            error("Can not create XML document builder.", e);
            return null;
        }

        Document document;
        try {
            document = parser.parse(inputStream);

        } catch (SAXException e) {
            error("Syntax error into XML document " + source + "'", e);
            return null;

        } catch (IOException e) {
            error("Can not parse XML document '" + source + "'", e);
            return null;
        }

        NodeList nl = document.getElementsByTagName("file");
        List list = new ArrayList(nl.getLength());

        for (int i = 0; i < nl.getLength(); i++) {
            Element node = (Element) nl.item(i);

            String value = node.getFirstChild().getNodeValue();

            if (value.startsWith("/") == false) {
                value = "/" + value;
            }

            list.add(value);
        }

        return list;
    }

    protected abstract boolean canSkipSpace();

    public synchronized void flush() throws ServletException {
        this.buffers = new StringAppender(16000);
        sourceBuffer = null;
        sourceBufferGZip = null;
        lastModified = -1;
        etag = null;
        hash = null;

        addCameliaFiles();
        addExternalRepositories(externalRepositoriesPropertyName);
    }

    protected void initializeBuffers() throws ServletException {

        StringAppender sb = this.buffers;
        this.buffers = null;

        try {
            if (canSkipSpace()) {
                sourceBuffer = SourceFilter.filterSkipSpaces(sb.toString())
                        .getBytes(charSet);

            } else {
                sourceBuffer = SourceFilter.filter(sb.toString()).getBytes(
                        charSet);
            }
        } catch (UnsupportedEncodingException ex) {
            throw new ServletException(ex);
        }

        if (lastModified <= 0) {
            lastModified = System.currentTimeMillis();
        }

        if (canUseETag) {
            etag = ExpirationHttpServlet.computeETag(sourceBuffer);
        }

        if (canUseHash) {
            hash = ExpirationHttpServlet.computeHash(sourceBuffer);
        }

        if (canUseGzip) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(
                        sourceBuffer.length);
                GZIPOutputStream gzos = new GZIPOutputStream(bos,
                        sourceBuffer.length);
                gzos.write(sourceBuffer);
                gzos.close();

                sourceBufferGZip = bos.toByteArray();

                bos.close();

            } catch (IOException ex) {
                throw new ServletException("Can not create GZIP buffer for "
                        + getSourceType() + " files.", ex);
            }
        }

        String message = getSourceType() + ": buffers loaded into "
                + sourceBuffer.length + " bytes";
        if (sourceBufferGZip != null) {
            message += " (GZiped: " + sourceBufferGZip.length + " ["
                    + (sourceBufferGZip.length * 100 / sourceBuffer.length)
                    + "%])";
        }

        LOG.info(message, null);
    }

    protected final void addExternalRepository(String path) {
        if (path.startsWith("/") == false) {
            path = "/" + path;
        }
        URL url = getURL(path);
        if (url == null) {
            error("Can not get URL for path '" + path + "'.", null);
            return;
        }

        URLConnection urlConnection;
        try {
            urlConnection = url.openConnection();

        } catch (IOException ex) {
            error("Can not get content of '" + url + "'.", ex);

            return;
        }

        if (urlConnection == null) {
            return;
        }

        updateLastModification(urlConnection);

        InputStream inputStream;
        try {
            inputStream = urlConnection.getInputStream();

        } catch (IOException ex) {
            error("Can not open '" + url + "'.", ex);

            return;
        }

        if (inputStream == null) {
            return;
        }

        List files;
        try {
            files = listExternalFiles(inputStream, path);

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }

        if (files == null) {
            return;
        }

        for (Iterator it = files.iterator(); it.hasNext();) {
            String filePath = (String) it.next();

            URL fileURL = getURL(filePath);
            if (fileURL == null) {
                error("Can not get URL for path '" + filePath + "'.", null);
                continue;
            }

            try {
                addURLContent(fileURL);

            } catch (IOException e) {
                error("Can not append external file '" + fileURL + "'.", e);
            }
        }
    }

    protected final void addExternalRepositories(String propertName) {
        if (propertName == null) {
            return;
        }
        String path = servletConfig.getInitParameter(propertName);
        if (path == null) {
            return;
        }

        StringTokenizer st = new StringTokenizer(path, ";,");
        for (; st.hasMoreTokens();) {
            String repositoryPath = st.nextToken();

            addExternalRepository(repositoryPath);
        }
    }

    protected void addCameliaFiles() throws ServletException {

        URL url = getURL(allSourcesURI);
        if (url == null) {
            throw new ServletException("Can not load URI '" + allSourcesURI
                    + "' !");
        }

        InputStream ins;
        try {
            URLConnection urlConnection = url.openConnection();

            updateLastModification(urlConnection);

            ins = url.openStream();

        } catch (IOException ex) {
            throw new ServletException(ex);
        }

        try {
            BufferedReader br;

            try {
                br = new BufferedReader(new InputStreamReader(ins, charSet));

            } catch (UnsupportedEncodingException e) {
                throw new ServletException("Unsupported encoding '" + charSet
                        + "'", e);
            }

            try {
                for (;;) {
                    String s = br.readLine();
                    if (s == null) {
                        break;
                    }
                    s = s.trim();

                    URL urls[] = getSourceURI(s, modules);

                    if (urls == null || urls.length < 1) {
                        continue;
                    }

                    for (int i = 0; i < urls.length; i++) {
                        try {
                            addURLContent(urls[i]);

                        } catch (IOException ex) {
                            throw new ServletException(
                                    "Can not read content of url '" + urls[i]
                                            + ". (repository='" + allSourcesURI
                                            + "')", ex);
                        }
                    }
                }
            } catch (IOException ex) {
                throw new ServletException(
                        "Reading files of repository (location='"
                                + allSourcesURI + "') performs exception ...",
                        ex);
            }

        } finally {
            try {
                ins.close();
            } catch (IOException ex) {
            }
        }
    }

    protected abstract URL[] getSourceURI(String line, Set modules);

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
        if (sourceBuffer == null) {
            initializeBuffers();
        }
        return lastModified;
    }

    public String getETag() throws ServletException {
        if (sourceBuffer == null) {
            initializeBuffers();
        }
        return etag;
    }

    public String getHash() throws ServletException {
        if (sourceBuffer == null) {
            initializeBuffers();
        }
        return hash;
    }

    private void loadExternalGZip(URL url) {
        InputStream ins;
        try {
            ins = url.openStream();

        } catch (IOException ex) {
            LOG.error("Can not open url '" + url + "'.", ex);
            return;
        }

        if (ins == null) {
            return;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(64000);
        try {
            byte buf[] = new byte[4096];
            for (;;) {
                int ret = ins.read(buf, 0, buf.length);
                if (ret < 0) {
                    break;
                }
                bos.write(buf, 0, ret);
            }

            bos.close();

            sourceBufferExternalGZip = bos.toByteArray();
        } catch (IOException ex) {
            LOG.error("Can not load content of url '" + url + "'.", ex);

        } finally {
            try {
                ins.close();
            } catch (IOException e) {
            }
        }
    }
}