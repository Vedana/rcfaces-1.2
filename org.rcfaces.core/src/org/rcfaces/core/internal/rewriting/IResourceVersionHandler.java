/*
 * $Id$
 */
package org.rcfaces.core.internal.rewriting;

import java.net.URL;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResourceVersionHandler {
    String getResourceVersion(FacesContext facesContext, String absolutePath,
            URL contentURLIfKnown);
}
