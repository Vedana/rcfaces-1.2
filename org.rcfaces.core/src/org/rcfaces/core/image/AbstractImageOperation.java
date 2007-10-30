/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.content.AbstractBufferOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageOperation extends AbstractBufferOperation
        implements IImageOperation {
    private static final String REVISION = "$Revision$";

    private String forceSuffix;

    private String externalContentType;

    private String internalContentType;

    public final String getExternalContentType() {
        return externalContentType;
    }

    public final void setExternalContentType(String forceContentType) {
        this.externalContentType = forceContentType;
    }

    public final String getInternalContentType() {
        return internalContentType;
    }

    public final void setInternalContentType(String internalContentType) {
        this.internalContentType = internalContentType;
    }

    public final String getForceSuffix() {
        return forceSuffix;
    }

    public final void setForceSuffix(String forceSuffix) {
        this.forceSuffix = forceSuffix;
    }

}
