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
public interface IContentProxyHandler {

    String getId();

    IContentAccessor getProxyedContentAccessor(RcfacesContext rcfacesContext,
            FacesContext facesContext, IContentAccessor returnContentAccessor,
            IGeneratedResourceInformation[] contentInformationRef);

}
