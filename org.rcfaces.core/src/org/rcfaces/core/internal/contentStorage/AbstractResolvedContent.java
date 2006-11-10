/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractResolvedContent implements IResolvedContent {
    private static final String REVISION = "$Revision$";

    private boolean versioned;

    public String getETag() {
        return null;
    }

    public String getHash() {
        return null;
    }

    public int getLength() {
        return -1;
    }

    public long getModificationDate() {
        return -1;
    }

    public String getURLSuffix() {
        return null;
    }

    public boolean isErrored() {
        return false;
    }

    public boolean isProcessAtRequest() {
        return false;
    }

    public String getResourceKey() {
        return null;
    }

    public boolean isVersioned() {
        return versioned;
    }

    public void setVersioned(boolean versioned) {
        this.versioned = versioned;
    }

}
