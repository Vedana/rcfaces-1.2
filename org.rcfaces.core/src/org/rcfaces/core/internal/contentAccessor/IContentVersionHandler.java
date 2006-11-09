/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.RcfacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentVersionHandler {
    String getVersionPath(RcfacesContext rcfacesContext,
            FacesContext facesContext, String relativeUrl,
            IContentAccessor contentAccessor,
            IContentInformation contentInformation);

    String getVersionTag(RcfacesContext rcfacesContext,
            FacesContext facesContext, String relativeUrl,
            IContentAccessor contentAccessor,
            IContentInformation contentInformation);
}
