/*
 * $Id$
 * 
 */
package org.rcfaces.core.provider;

import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractProvider implements IProvider {
    private static final String REVISION = "$Revision$";

    public void configureRules(Digester digester) {
    }

    public void startup(FacesContext facesContext) {
    }

    public boolean equals(Object obj) {
        if (obj == null || (obj instanceof AbstractProvider) == false) {
            return false;
        }

        return getId().equals(((AbstractProvider) obj).getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public String toString() {
        return "[AbstractProvider '" + getId() + "']";
    }
}
