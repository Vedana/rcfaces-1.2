/*
 * $Id$
 */
package org.rcfaces.core.internal.content;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFileBuffer {

    String getName();

    void initializeRedirection(String url) throws IOException;

    String getRedirection();

    int getSize();

    boolean isErrored();

    void setErrored();

    boolean isInitialized();

    InputStream getContent() throws IOException;

    String getContentType();

    long getModificationDate();

    String getHash();

    String getETag();
}
