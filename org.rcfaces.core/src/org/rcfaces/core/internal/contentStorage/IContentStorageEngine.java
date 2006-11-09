/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.IContentInformation;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentStorageEngine {

    IContentStorageRepository getRepository();

    String registerRaw(FacesContext facesContext, Object ref,
            IContentInformation information);

    String registerContentModel(FacesContext facesContext,
            IContentModel contentModel, IContentInformation information);

}
