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

    public void configureRules(Digester digester) {
    }

    public void startup(FacesContext facesContext) {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj instanceof AbstractProvider) == false) {
            return false;
        }

        return getId().equals(((AbstractProvider) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "[AbstractProvider '" + getId() + "']";
    }
}
