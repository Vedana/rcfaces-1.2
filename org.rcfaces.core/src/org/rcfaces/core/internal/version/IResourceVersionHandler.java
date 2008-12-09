/*
 * $Id$
 */
package org.rcfaces.core.internal.version;

import java.net.URL;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResourceVersionHandler {

    String ID = "org.rcfaces.core.URL_REWRITING_PROVIDER";

    boolean isEnabled();

    String getResourceVersion(FacesContext facesContext, String absolutePath,
            URL contentURLIfKnown);
}
