/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageOperation implements IImageOperation {
    private static final String REVISION = "$Revision$";

    private String name;

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

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

}
