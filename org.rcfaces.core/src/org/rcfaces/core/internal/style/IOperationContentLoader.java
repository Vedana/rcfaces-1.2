/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.AbstractBufferOperationContentModel.ContentInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IOperationContentLoader {
    String loadContent(FacesContext facesContext,
            IResourceLoaderFactory resourceLoaderFactory, String path,
            String defaultCharset, ContentInformation contentInfoRef[]);
}
