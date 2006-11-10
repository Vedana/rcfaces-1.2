/*
 * $Id$
 * 
 */
package org.rcfaces.core.provider;

import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProvider {

    void configureRules(Digester digester);

    void startup(FacesContext facesContext);
}
