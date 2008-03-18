/*
 * $Id$
 */
package org.rcfaces.core.internal.content;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.content.AbstractBufferOperationContentModel.ContentInformation;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;

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
