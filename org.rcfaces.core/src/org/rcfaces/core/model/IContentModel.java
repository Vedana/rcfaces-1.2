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

    String RESPONSE_EXPIRATION_PROPERTY = "org.rfcaces.response.EXPIRATION";

    String SOURCE_MIME_TYPE_PROPERTY = "org.rcfaces.source.MIME_TYPE";

    String RESPONSE_MIME_TYPE_PROPERTY = "org.rcfaces.response.MIME_TYPE";

    String RESPONSE_URL_SUFFIX_PROPERTY = "org.rcfaces.response.SUFFIX";

    String AUTO_GENERATE_RESOURCE_KEY_PROPERTY = "org.rcfaces.source.AUTO_GENERATE_RESOURCE_KEY";

    void setInformations(IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation);

    Object getWrappedData();

    String getContentEngineId();

    void setContentEngineId(String contentEngineId);

    boolean checkNotModified();
}
