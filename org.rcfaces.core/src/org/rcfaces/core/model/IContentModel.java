/*
 * $Id$
 */
package org.rcfaces.core.model;

import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentModel {

    // String RESPONSE_EXPIRATION_PROPERTY = "org.rfcaces.response.EXPIRATION";

    void setInformations(IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation);

    Object getWrappedData();

    String getContentEngineId();

    void setContentEngineId(String contentEngineId);

    boolean checkNotModified();
}
