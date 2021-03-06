/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResolvedContent extends IResourceKey, Serializable {

    boolean isErrored();

    boolean isProcessAtRequest();

    int getLength();

    long getModificationDate();

    String getURLSuffix();

    String getContentType();

    String getContentEncoding();

    InputStream getInputStream() throws IOException;

    String getHash();

    String getETag();

    boolean isVersioned();

    void setVersioned(boolean versioned);

    void appendHashInformations(StringAppender sa);
}