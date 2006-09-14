/*
 * $Id$
 */
package org.rcfaces.core.internal.webapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractRepository implements IRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

    protected static final IFile[] FILE_EMPTY_ARRAY = new IFile[0];

    private static final Object[] OBJECT_EMPTY_ARRAY = new Object[0];

    protected final Map filesByName = new HashMap();

    protected final Map filesByURI = new HashMap();

    protected final String repositoryVersion;

    protected final String servletURI;

    public AbstractRepository(String servletURI, String repositoryVersion) {
        if (servletURI.length() > 0) {
            if (servletURI.endsWith("/") && servletURI.length() > 1) {
                servletURI = servletURI.substring(0, servletURI.length() - 1);

            } else if (servletURI.startsWith("/") == false) {
                servletURI = "/" + servletURI;
            }
        }

        this.servletURI = servletURI;
        this.repositoryVersion = repositoryVersion;
    }

    public final String getVersion() {
        return repositoryVersion;
    }

    public IFile getFileByURI(String uri) {
        return (IFile) filesByURI.get(uri);
    }

    public IFile getFileByName(String name) {
        return (IFile) filesByName.get(name);
    }

    public IContext createContext(Locale locale) {
        return new ContextImpl(locale);
    }

    protected abstract IContentProvider getDefaultContentProvider();

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class ContextImpl implements IContext {
        private static final String REVISION = "$Revision$";

        private final Set files = new HashSet(32);

        private final Locale locale;

        public ContextImpl(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        public boolean contains(IFile file) {
            return files.contains(file);
        }

        public boolean add(IFile file) {
            return files.add(file);
        }

        public IContext copy() {
            ContextImpl ctx = new ContextImpl(locale);
            ctx.files.addAll(files);

            return ctx;
        }

        public void restoreState(IRepository repository, Object state) {
            if (state == null) {
                return;
            }

            Object fs[] = (Object[]) state;
            for (int i = 0; i < fs.length; i++) {
                IFile file = ((AbstractHierarchicalRepository) repository)
                        .getFileById((String) fs[i]);

                if (file == null) {
                    continue;
                }

                add(file);
            }
        }

        public Object saveState() {
            if (files.isEmpty()) {
                return null;
            }

            Iterator it = files.iterator();
            Object ret[] = new Object[files.size()];
            for (int i = 0; it.hasNext();) {
                IFile file = (IFile) it.next();

                ret[i++] = ((File) file).getId();
            }

            return ret;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class File implements IFile {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -8396517787887070898L;

        private final String id;

        private final String filename;

        private final Object unlocalizedContentLocation;

        private final String unlocalizedURI;

        private final IContentProvider contentProvider;

        private LocalizedFile unlocalizedFile;

        private Map localizedFiles;

        public File(String name, String filename, String unlocalizedURI,
                Object unlocalizedContentLocation,
                IContentProvider contentProvider) {
            this.id = name;
            this.filename = filename;
            this.unlocalizedURI = unlocalizedURI;
            this.unlocalizedContentLocation = unlocalizedContentLocation;
            this.contentProvider = contentProvider;
        }

        public IRepository getRepository() {
            return AbstractRepository.this;
        }

        public final String getId() {
            return id;
        }

        public Object[] getContentReferences(Locale locale) {
            LocalizedFile localizedFile = getLocalizedFile(locale);

            return localizedFile.getContentLocations();
        }

        public String getFilename() {
            return filename;
        }

        public IContentProvider getContentProvider() {
            if (contentProvider != null) {
                return contentProvider;
            }
            return getDefaultContentProvider();
        }

        public String getURI(Locale locale) {
            LocalizedFile localizedFile = getLocalizedFile(locale);

            return localizedFile.getURI();
        }

        private synchronized LocalizedFile getLocalizedFile(Locale locale) {
            if (locale == null) {
                if (unlocalizedFile != null) {
                    return unlocalizedFile;
                }

                unlocalizedFile = new LocalizedFile(getContentProvider(), null,
                        unlocalizedURI, unlocalizedContentLocation);
                return unlocalizedFile;
            }

            if (localizedFiles == null) {
                localizedFiles = new HashMap(4);
            }

            LocalizedFile localizedFile = (LocalizedFile) localizedFiles
                    .get(locale);
            if (localizedFile != null) {
                return localizedFile;
            }

            localizedFile = new LocalizedFile(getContentProvider(), locale,
                    unlocalizedURI, unlocalizedContentLocation);
            localizedFiles.put(locale, localizedFile);

            return localizedFile;
        }

        public boolean equals(Object obj) {
            if (obj == null || (obj instanceof File) == false) {
                return false;
            }

            File f = (File) obj;

            return f.filename.equals(filename);
        }

        public int hashCode() {
            return filename.hashCode();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class LocalizedFile {
        private static final String REVISION = "$Revision$";

        private final String uri;

        private final Object[] contentLocations;

        public LocalizedFile(IContentProvider contentProvider, Locale locale,
                String uri, Object unlocalizedContentLocation) {
            Object localizedContentLocation = null;

            if (locale != null) {
                URIParameters uriParameters = new URIParameters(uri);

                if (locale != null) {
                    uriParameters.appendLocale(locale);
                }

                uri = uriParameters.computeParametredURI();
            }

            if (locale != null) {
                if (unlocalizedContentLocation != null) {
                    localizedContentLocation = contentProvider
                            .searchLocalizedContentReference(
                                    unlocalizedContentLocation, locale);

                    if (localizedContentLocation != null) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Find localized version (" + locale
                                    + ") of '" + uri + "' => "
                                    + localizedContentLocation);

                        }
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Can not find localized version ("
                                    + locale + ") of '" + uri + "'.");

                        }
                    }
                }
            }

            if (unlocalizedContentLocation == null) {
                contentLocations = OBJECT_EMPTY_ARRAY;

            } else if (localizedContentLocation == null) {
                contentLocations = new Object[] { unlocalizedContentLocation };

            } else {
                contentLocations = new Object[] { unlocalizedContentLocation,
                        localizedContentLocation };
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Content locations of " + this + " => "
                        + Arrays.asList(contentLocations));
            }

            this.uri = uri;
        }

        public Object[] getContentLocations() {
            return contentLocations;
        }

        public String getURI() {
            return uri;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static abstract class AbstractContent implements IContent {
        private static final String REVISION = "$Revision$";

        public long getLastModified() throws IOException {
            return -1;
        }

        public long getLength() throws IOException {
            return -1;
        }

        public void release() {
        }

    }
}
