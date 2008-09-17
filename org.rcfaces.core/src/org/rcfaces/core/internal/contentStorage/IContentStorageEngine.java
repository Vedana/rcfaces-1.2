/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentStorageEngine {

    IContentStorageRepository getRepository();

    IContentAccessor registerRaw(FacesContext facesContext, Object ref,
            IGeneratedResourceInformation information);

    IContentAccessor registerContentModel(FacesContext facesContext,
            IContentModel contentModel, IGeneratedResourceInformation generatedInformation,
            IGenerationResourceInformation generationInformation);

}
