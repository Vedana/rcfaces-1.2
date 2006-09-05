/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.13  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalitï¿½ de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.12  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cotï¿½ client
 *
 * Revision 1.11  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.10  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.9  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.8  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.7  2005/11/08 12:16:26  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cotï¿½ client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.6  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.5  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propriï¿½tï¿½s des composants
 *
 * Revision 1.4  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalitï¿½s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.3  2005/03/18 18:03:41  oeuillot
 * Ameliration du look du TabbedPane !
 *
 * Revision 1.2  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compressï¿½
 * Menu du style XP et pas Office !
 *
 * Revision 1.1  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.core.internal.webapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.core.internal.webapp.IRepository.ISet;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class RepositoryServlet extends ParametredHttpServlet {
    private static final String REVISION = "$Revision$";

    private static final String SET_PREFIX = ".sets";

    private static final String MODULES_PREFIX = ".modules";

    private static final Log LOG = LogFactory.getLog(RepositoryServlet.class);

    private IRepository repository;

    private static final Map convertedLocales = new HashMap(32);

    private static final String NO_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".NO_CACHE";

    private static final String FILTERED_LOCALES_PARAMETER = Constants
            .getPackagePrefix()
            + ".FILTERED_LOCALES";

    private static final String REPOSITORY_DEFAULT_LOCALE = Constants
            .getPackagePrefix()
            + ".REPOSITORY_DEFAULT_LOCALE";

    private final Map fileToRecordByLocale = new HashMap();

    private Set filteredLocales = null;

    protected boolean localeSupport;

    private Locale defaultLocale;

    private boolean noCache;

    private boolean devMode;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String nc = getParameter(NO_CACHE_PARAMETER);
        if ("true".equalsIgnoreCase(nc)) {
            noCache = true;

            LOG.info("NO_CACHE detected and enabled for servlet '"
                    + getServletName() + "'.");
        }

        String repositoryDevModePropertyName = getRepositoryDevModeParameterName();
        if (repositoryDevModePropertyName != null) {
            String dev = getParameter(repositoryDevModePropertyName);

            if ("true".equalsIgnoreCase(dev)) {
                devMode = true;

                LOG
                        .info("REPOSITORY_DEV_MODE detected and enabled for servlet '"
                                + getServletName() + "'.");
            }
        }

        String acceptedLocaleNames = getParameter(FILTERED_LOCALES_PARAMETER);
        if (acceptedLocaleNames != null) {
            localeSupport = true;

            filteredLocales = new HashSet();

            for (StringTokenizer st = new StringTokenizer(acceptedLocaleNames,
                    ","); st.hasMoreTokens();) {
                String localeName = st.nextToken();

                Locale locale = convertLocaleName(localeName, false);
                if (locale == null) {
                    continue;
                }

                filteredLocales.add(locale);
            }
        }

        String localeName = getParameter(REPOSITORY_DEFAULT_LOCALE);
        if (localeName != null) {
            defaultLocale = convertLocaleName(localeName, true);
            localeSupport = true;

            if (defaultLocale != null) {
                LOG
                        .info("REPOSITORY_DEFAULT_LOCALE specify default locale to '"
                                + defaultLocale + "'.");
            } else {
                LOG.info("REPOSITORY_DEFAULT_LOCALE value '" + localeName
                        + "' is not accepted ! (invalid or not accepted !)");
            }
        }

        try {
            repository = initializeRepository(config);

        } catch (IOException e) {
            throw new ServletException(
                    "Can not initialize repository for servlet '"
                            + getServletName() + "'.", e);
        }

        String groupAll = config.getInitParameter(getParameterPrefix()
                + MODULES_PREFIX + ".GROUP_ALL");
        if (groupAll != null) {
            LOG.debug("Group all modules: " + groupAll);

            for (StringTokenizer st = new StringTokenizer(groupAll, ";, "); st
                    .hasMoreTokens();) {
                String name = st.nextToken();

                if ("all".equalsIgnoreCase(name)) {
                    IRepository.IModule modules[] = repository.listModules();
                    for (int i = 0; i < modules.length; i++) {
                        modules[i].setGroupAllFiles(true);
                    }

                    continue;
                }

                IRepository.IModule module = repository.getModuleByName(name);
                if (module == null) {
                    throw new IllegalArgumentException("Can not find module '"
                            + name + "' to enable 'groupAll'.");
                }

                module.setGroupAllFiles(true);
            }
        }

        String bootSet = config.getInitParameter(getParameterPrefix()
                + ".BOOT_SET");

        if (bootSet != null) {
            StringTokenizer st = new StringTokenizer(bootSet, ",; \n\t\r");
            if (st.countTokens() != 1) {
                throw new ServletException(
                        "Only one SET can be specified as BOOT_SET !");
            }

            bootSet = st.nextToken();

            String parameterValue = config
                    .getInitParameter(getParameterPrefix() + SET_PREFIX + "."
                            + bootSet);
            if (parameterValue == null) {
                throw new ServletException(
                        "Set specified by BOOT_SET is not defined !");
            }

            IRepository.ISet set = initializeModuleSet(bootSet, parameterValue);
            if (set == null) {
                throw new IllegalArgumentException("Can not find boot set '"
                        + bootSet + "'.");
            }

            repository.setBootSet(set);
        }

        Enumeration parameterNames = config.getInitParameterNames();
        String setPrefix = getParameterPrefix() + SET_PREFIX;
        for (; parameterNames.hasMoreElements();) {
            String parameterName = (String) parameterNames.nextElement();

            if (parameterName.startsWith(setPrefix) == false) {
                continue;
            }

            String parameterValue = config.getInitParameter(parameterName);

            parameterName = parameterName.substring(setPrefix.length() + 1);
            if (parameterName.length() < 1) {
                continue;
            }

            if (parameterName.equals(bootSet)) {
                continue;
            }

            LOG.debug("Group modules '" + parameterValue + "' to set : "
                    + parameterName);

            initializeModuleSet(parameterName, parameterValue);
        }
    }

    protected String getRepositoryDevModeParameterName() {
        return null;
    }

    protected abstract String getParameterPrefix();

    protected final IRepository getRepository() {
        return repository;
    }

    private ISet initializeModuleSet(String setName, String moduleNames) {
        StringTokenizer st = new StringTokenizer(moduleNames, ",; \n\t\r");

        List l = new ArrayList(st.countTokens());
        for (; st.hasMoreTokens();) {
            String moduleName = st.nextToken();

            l.add(moduleName);
        }

        String uri = getSetURI(setName);

        return repository.declareSet(setName, uri, (String[]) l
                .toArray(new String[l.size()]));
    }

    protected abstract String getSetURI(String setName);

    protected abstract IRepository initializeRepository(ServletConfig config)
            throws IOException;

    protected void doHead(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        // On gere le fonctionement en interne !
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();

        String contextPath = request.getContextPath();
        if (contextPath != null) {
            uri = uri.substring(contextPath.length());
        }

        String servletPath = request.getServletPath();
        if (servletPath != null) {
            uri = uri.substring(servletPath.length());
        }

        int idx = uri.indexOf('/');
        if (idx >= 0) {
            uri = uri.substring(idx + 1);
        }

        Locale locale = null;

        String version = null;
        if (Constants.BUILD_ID_URL_SUPPORT) {
            idx = uri.indexOf('/');
            if (idx < 0) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            version = uri.substring(0, idx);
            uri = uri.substring(idx + 1);
        }

        URIParameters up = URIParameters.parseURI(uri);
        if (up != null) {
            if (localeSupport) {
                String localeName = up.getLocaleName();
                if (localeName != null) {
                    locale = convertLocaleName(localeName, true);
                }
            }

            if (version == null) {
                version = up.getVersion();
            }

            uri = up.getURI();
        }

        if (version != null) {
            String repositoryVersion = repository.getVersion();
            if (repositoryVersion != null) {
                if (repositoryVersion.equals(version) == false) {
                    LOG.error("Not same repository version ! (current="
                            + repositoryVersion + " request=" + version + ")");

                    setNoCache(response);
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    return;
                }
            }
        }

        IFile file = repository.getFileByURI(uri);
        if (file == null) {
            setNoCache(response);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Record record = getFileRecord(file, locale);

        sendRecord(request, response, record);
    }

    protected Record getFileRecord(IFile file, Locale locale) {
        Record record;

        if (locale == null) {
            locale = getDefaultLocale();
        }

        synchronized (fileToRecordByLocale) {
            Map fileToRecord = (Map) fileToRecordByLocale.get(locale);
            if (fileToRecord == null) {
                fileToRecord = new HashMap();
                fileToRecordByLocale.put(locale, fileToRecord);
            }

            record = (Record) fileToRecord.get(file);
            if (record == null) {
                record = newRecord(file, locale);

                fileToRecord.put(file, record);
            }

            if (devMode) {
                record.verifyModifications();
            }
        }

        return record;
    }

    protected Locale getDefaultLocale() {
        if (defaultLocale == null) {
            defaultLocale = Locale.getDefault();
        }

        return defaultLocale;
    }

    private Locale convertLocaleName(String localeName, boolean accept) {
        localeName = localeName.toLowerCase();

        Locale locale;
        synchronized (convertedLocales) {
            locale = (Locale) convertedLocales.get(localeName);
        }

        if (locale != null) {
            return locale;
        }

        // On synchronise pas le bloc, histore de pas bloquer le reste des
        // Threads ...
        // Et tanpis pour les put multiple de la meme valeur !

        StringTokenizer st = new StringTokenizer(localeName, "_");
        String language = st.nextToken().toLowerCase();
        String country = (st.hasMoreTokens()) ? st.nextToken().toLowerCase()
                : "";
        String variant = (st.hasMoreTokens()) ? st.nextToken().toLowerCase()
                : "";

        Locale bestLocale = null;
        int bestHit = 0;

        Locale locales[] = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            locale = locales[i];
            if (accept && filteredLocales != null
                    && filteredLocales.contains(locale) == false) {
                continue;
            }

            if (locale.getLanguage().equalsIgnoreCase(language) == false) {
                continue;
            }
            int hit = 1;

            String lcountry = locale.getCountry();
            if (lcountry.equalsIgnoreCase(country)) {
                hit += 2;

                String lvariant = locale.getVariant();
                if (lvariant.equalsIgnoreCase(variant)) {
                    hit += 2;

                } else if (lvariant.length() < 1) {
                    hit++;
                }

            } else if (lcountry.length() < 1) {
                hit++;
            }

            if (hit < bestHit) {
                continue;
            }

            bestLocale = locale;
            bestHit = hit;
        }

        if (bestLocale == null) {
            // On n'enregistre pas la mauvaise reponse !
            return null;
        }

        synchronized (convertedLocales) {
            convertedLocales.put(localeName, bestLocale);

            return bestLocale;
        }
    }

    private void sendRecord(HttpServletRequest request,
            HttpServletResponse response, Record record) throws IOException {

        byte buf[] = null;
        long modificationDate;
        boolean noHeader = false;
        String etag;
        String hash;

        synchronized (record) {
            etag = record.getETag();
            hash = record.getHash();

            modificationDate = record.getLastModificationDate();

            if (hasGZipSupport() && hasGzipSupport(request)) {
                byte jsGZip[] = record.getGZipedBuffer();
                if (jsGZip != null) {
                    if (hasGzipSupport(request)) {
                        setGzipContentEncoding(response);

                        buf = jsGZip;
                        noHeader = true;
                    }
                }
            }
            if (buf == null) {
                buf = record.getBuffer();
            }
        }

        if (buf == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Set no cache for response.");
            }

            setNoCache(response);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        boolean different = false;

        if (different == false && etag != null) {
            String ifETag = request.getHeader(HTTP_IF_NONE_MATCH);
            if (ifETag != null) {
                if (etag.equals(ifETag)) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }

                different = true;
            }
        }

        if (different == false && hash != null) {
            String isHash = request.getHeader(HTTP_IF_NOT_HASH);
            if (isHash != null) {
                if (hash.equals(isHash)) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }

                different = true;
            }
        }

        if (different == false) {
            long ifModifiedSince = request
                    .getDateHeader(HTTP_IF_MODIFIED_SINCE);
            if (ifModifiedSince > 0) {
                if (ifModifiedSince >= modificationDate) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }
            }
        }

        // String acceptCharset=request.getHeader("Accept-Charset");

        String contentType = getContentType(record);
        response.setContentType(contentType);

        if (noCache) {
            setNoCache(response);

        } else {
            if (modificationDate > 0) {
                response.setDateHeader(HTTP_LAST_MODIFIED, modificationDate);
            }
            ExpirationDate expirationDate = record.getExpirationDate();
            if (expirationDate == null) {
                expirationDate = getDefaultExpirationDate();
            }

            expirationDate.sendExpires(response);
        }

        if (etag != null) {
            response.setHeader(HTTP_ETAG, etag);
        }

        if (hash != null) {
            response.setHeader(HTTP_HASH, hash);
        }

        byte prolog[] = null;
        byte epilog[] = null;
        int length = buf.length;

        if (noHeader == false) {
            prolog = record.getProlog();
            if (prolog != null) {
                length += prolog.length;
            }

            epilog = record.getEpilog();
            if (epilog != null) {
                length += epilog.length;
            }
        }

        response.setContentLength(length);

        if (request.getMethod().equals(HEAD_HTTP_METHOD)) {
            return;
        }

        OutputStream outputStream = response.getOutputStream();
        if (prolog != null) {
            outputStream.write(prolog);
        }

        outputStream.write(buf);

        if (epilog != null) {
            outputStream.write(epilog);
        }
    }

    /*
     * private void closeConnection(URLConnection urlConnection) { // Il faut
     * fermer le InputStream pour fermer la connection try { InputStream in =
     * urlConnection.getInputStream(); if (in != null) { in.close(); } } catch
     * (IOException ex) { } }
     */
    protected abstract Record newRecord(IFile file, Locale locale);

    protected abstract String getContentType(Record record);

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected abstract class Record {
        private static final String REVISION = "$Revision$";

        private final IFile file;

        protected final Locale locale;

        private byte buffer[];

        private byte gzippedBuffer[];

        private long lastModificationDate;

        protected ExpirationDate expirationDate;

        private String etag;

        private String hash;

        public Record(IFile file, Locale locale) {
            this.file = file;
            this.locale = locale;
        }

        protected final IFile getFile() {
            return file;
        }

        public ExpirationDate getExpirationDate() {
            return expirationDate;
        }

        public void verifyModifications() {

            boolean modified;

            if (file instanceof IRepository.ISet) {
                modified = verifySetModifications((IRepository.ISet) file);

            } else if (file instanceof IRepository.IModule) {
                modified = verifyModuleModifications((IRepository.IModule) file);

            } else {
                modified = verifyFileModifications();
            }

            if (modified == false) {
                return;
            }

            buffer = null;
            gzippedBuffer = null;
            lastModificationDate = 0;
            etag = null;
            hash = null;
        }

        private boolean verifyFileModifications() {

            URL urls[] = getFileURL(file);

            for (int i = 0; i < urls.length; i++) {
                long l;
                try {
                    IContent content = file.getContentProvider().getContent(
                            urls[i], locale);
                    try {
                        l = content.getLastModified();

                    } finally {
                        content.release();
                    }

                } catch (IOException ex) {
                    LOG.error("Can not get lastModified date of '" + urls[i]
                            + "'.", ex);
                    l = -1;
                }

                if (l < 1) {
                    l = System.currentTimeMillis();
                }

                LOG.debug("Verify file '" + file.getFilename() + "' delta="
                        + (l - lastModificationDate) + " ms.");

                if (lastModificationDate == l) {
                    return false;
                }

                if (lastModificationDate > 0) {
                    LOG.debug("File '" + file.getFilename()
                            + "' has been modified !");
                }
            }

            return true;
        }

        private boolean verifyModuleModifications(IRepository.IModule module) {
            return verifyFilesModifications(module.listDependencies());
        }

        private boolean verifySetModifications(IRepository.ISet set) {
            return verifyFilesModifications(set.listDependencies());
        }

        private boolean verifyFilesModifications(IFile[] files) {
            for (int i = 0; i < files.length; i++) {
                Record record = getFileRecord(files[i], locale);

                if (record.verifyFileModifications()) {
                    return true;
                }
            }

            return false;
        }

        public final byte[] getBuffer() throws IOException {
            if (buffer != null) {
                return buffer;
            }

            if (file instanceof IRepository.ISet) {
                return getSetBuffer();
            }

            if (file instanceof IRepository.IModule) {
                return getModuleBuffer();
            }

            URL urls[] = getFileURL(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream(16000);
            lastModificationDate = -1;

            for (int i = 0; i < urls.length; i++) {
                IContent contentProvider = file.getContentProvider()
                        .getContent(urls[i], locale);
                try {
                    long date = contentProvider.getLastModified();
                    if (date < 1) {
                        date = System.currentTimeMillis();
                    }
                    if (lastModificationDate < date) {
                        lastModificationDate = date;
                    }

                    long size = contentProvider.getLength();
                    if (size == 0) {
                        continue;
                    }

                    InputStream in = contentProvider.getInputStream();
                    try {
                        byte buf[];
                        if (size > 0) {
                            buf = new byte[(int) size];

                        } else {
                            buf = new byte[4096];
                        }

                        for (;;) {
                            int ret = in.read(buf);
                            if (ret < 0) {
                                break;
                            }
                            bos.write(buf, 0, ret);
                        }

                    } finally {
                        try {
                            in.close();

                        } catch (Exception ex) {
                            LOG.error("Can not close inputstream '" + urls[i]
                                    + "'.", ex);
                        }
                    }
                } finally {
                    contentProvider.release();
                }
            }

            bos.close();

            buffer = bos.toByteArray();

            int beforeUpdate = buffer.length;

            buffer = updateBuffer(buffer);

            if (LOG.isInfoEnabled()) {
                DateFormat dateFormat = DateFormat.getDateTimeInstance(
                        DateFormat.SHORT, DateFormat.MEDIUM);

                LOG.debug("Load record '" + file.getFilename() + "' into "
                        + buffer.length + " bytes, modified date="
                        + dateFormat.format(new Date(lastModificationDate))
                        + "  (update-ratio "
                        + (buffer.length * 100 / beforeUpdate) + "%)");
            }

            if (hasEtagSupport()) {
                etag = computeETag(buffer);
            }

            if (hasHashSupport()) {
                hash = computeHash(buffer);
            }

            return buffer;
        }

        private byte[] getModuleBuffer() throws IOException {
            IRepository.IModule module = (IRepository.IModule) file;

            return getFilesBuffer(module.listDependencies());
        }

        private byte[] getSetBuffer() throws IOException {
            IRepository.ISet set = (IRepository.ISet) file;

            return getFilesBuffer(set.listDependencies());
        }

        private byte[] getFilesBuffer(IFile files[]) throws IOException {
            byte buffers[][] = new byte[files.length][];
            int size = 0;
            lastModificationDate = 0;

            for (int i = 0; i < files.length; i++) {
                Record record = getFileRecord(files[i], locale);

                synchronized (record) {
                    buffers[i] = record.getBuffer();

                    size += buffers[i].length;

                    long lm = record.getLastModificationDate();

                    // plusieurs cas :
                    // * on ne connait encore aucune date
                    // * une des dates est inconnue, la date globale est
                    // donc inconnue
                    // * la nouvelle date est plus recente que les
                    // precedentes
                    if (lm > lastModificationDate) {
                        lastModificationDate = lm;
                    }
                }
            }

            buffer = new byte[size];
            int offset = 0;
            for (int i = 0; i < files.length; i++) {
                byte b[] = buffers[i];

                System.arraycopy(b, 0, buffer, offset, b.length);
                offset += b.length;
            }

            if (LOG.isInfoEnabled()) {
                DateFormat dateFormat = DateFormat.getDateTimeInstance(
                        DateFormat.SHORT, DateFormat.MEDIUM);

                StringAppender sb = new StringAppender(files.length * 32);
                for (int i = 0; i < files.length; i++) {
                    Record record = getFileRecord(files[i], locale);

                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(record.file.getFilename());
                }

                LOG.debug("Merge records for '" + file.getFilename()
                        + "' into " + buffer.length + " bytes, modified date="
                        + dateFormat.format(new Date(lastModificationDate))
                        + ", files '" + sb.toString() + "'.");
            }

            if (hasEtagSupport()) {
                etag = computeETag(buffer);
            }

            if (hasHashSupport()) {
                hash = computeHash(buffer);
            }

            return buffer;
        }

        protected URL[] getFileURL(IFile file) {
            return file.getContentLocations(locale);
        }

        protected byte[] updateBuffer(byte[] buffer) throws IOException {
            return buffer;
        }

        public final long getLastModificationDate() throws IOException {
            getBuffer();

            return lastModificationDate;
        }

        public final String getETag() throws IOException {
            getBuffer();

            return etag;
        }

        public final String getHash() throws IOException {
            getBuffer();

            return hash;
        }

        public final byte[] getGZipedBuffer() throws IOException {
            if (gzippedBuffer != null) {
                return gzippedBuffer;
            }

            byte buf[] = getBuffer();
            if (buf == null || buf.length < 1) {
                return buf;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream(buf.length);
            GZIPOutputStream gzos = new GZIPOutputStream(bos, buf.length);

            byte prolog[] = getProlog();
            if (prolog != null) {
                gzos.write(prolog);
            }

            gzos.write(buf);

            byte epilog[] = getEpilog();
            if (epilog != null) {
                gzos.write(epilog);
            }
            gzos.close();

            gzippedBuffer = bos.toByteArray();

            LOG.debug("GZIP record '" + file.getFilename() + "' into "
                    + gzippedBuffer.length + " bytes (compression-ratio "
                    + (gzippedBuffer.length * 100 / buffer.length)
                    + "% , original size=" + buffer.length + " bytes)");

            return gzippedBuffer;
        }

        public byte[] getProlog() throws IOException {
            return null;
        }

        public byte[] getEpilog() throws IOException {
            return null;
        }

        public String getCharset() {
            return null;
        }
    }
}
