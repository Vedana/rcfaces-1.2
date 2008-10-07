/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRepository extends Serializable {

    String getVersion();

    IContext createContext(Locale locale);

    IFile getFileByName(String filename);

    IFile getFileByURI(String uri);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IFile extends Serializable {
        IRepository getRepository();

        IContentProvider getContentProvider();

        String getFilename();

        Object[] getContentReferences(Locale locale);

        String getURI(Locale locale);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IContext {
        Locale getLocale();

        boolean contains(IFile file);

        boolean add(IFile file);

        IContext copy();

        Object saveState();

        void restoreState(IRepository repository, Object state);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IContentProvider {
        IContent getContent(Object contentReference, Locale locale);

        Object searchLocalizedContentReference(Object contentReference,
                Locale locale);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IContent {
        InputStream getInputStream() throws IOException;

        long getLastModified() throws IOException;

        long getLength() throws IOException;

        void release();
    }
}
