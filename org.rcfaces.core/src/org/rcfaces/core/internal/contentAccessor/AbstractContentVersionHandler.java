/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractContentVersionHandler implements
        IContentVersionHandler {
    private static final String REVISION = "$Revision$";

    public boolean equals(Object obj) {
        if (obj == null
                || (obj instanceof AbstractContentVersionHandler) == false) {
            return false;
        }

        return getId().equals(((AbstractContentVersionHandler) obj).getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public String toString() {
        return "[AbstractContentVersionHandler '" + getId() + "']";
    }

}
