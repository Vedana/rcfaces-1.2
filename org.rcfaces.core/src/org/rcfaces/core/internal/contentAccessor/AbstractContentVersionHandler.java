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

    @Override
    public boolean equals(Object obj) {
        if (obj == null
                || (obj instanceof AbstractContentVersionHandler) == false) {
            return false;
        }

        return getId().equals(((AbstractContentVersionHandler) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "[AbstractContentVersionHandler '" + getId() + "']";
    }

}
