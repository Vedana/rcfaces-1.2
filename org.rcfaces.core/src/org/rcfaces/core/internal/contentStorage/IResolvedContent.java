/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

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

    InputStream getInputStream() throws IOException;

    String getHash();

    String getETag();
}