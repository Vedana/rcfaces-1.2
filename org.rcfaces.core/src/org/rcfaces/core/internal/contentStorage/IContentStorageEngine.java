/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentStorageEngine {

    IContentStorageRepository getRepository();

    IContentAccessor registerRaw(FacesContext facesContext, Object ref,
            IContentInformation information, IContentType contentType);

    IContentAccessor registerContentModel(FacesContext facesContext,
            IContentModel contentModel, IContentInformation information,
            IContentType contentType);

}
