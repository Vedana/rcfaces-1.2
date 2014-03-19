/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.component.StateHolder;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGenerationResourceInformation extends StateHolder {

    String RESPONSE_MIME_TYPE_PROPERTY = "org.rcfaces.response.MIME_TYPE";

    String RESPONSE_URL_SUFFIX_PROPERTY = "org.rcfaces.response.SUFFIX";

    String SOURCE_URL = "org.rcfaces.generation.SOURCE_URL";

    String SOURCE_KEY = "org.rcfaces.generation.SOURCE_KEY";

    String RESPONSE_LAST_MODIFIED_PROPERTY = "org.rfcaces.response.LAST_MODIFIED";

    String COMPUTE_RESOURCE_KEY_FROM_GENERATION_INFORMATION = "org.rcfaces.source.REQUEST_INFORMATION_RESOURCE_KEY";

    String getResponseMimeType();

    void setResponseMimeType(String mimeType);

    String getResponseSuffix();

    void setResponseSuffix(String suffix);

    Object getAttribute(String attributeName);

    Object setAttribute(String attributeName, Object value);

    String getComponentClientId();

    IFilterProperties getFilterProperties();

    boolean isProcessAtRequest();

    long getResponseLastModified();

    void setComputeResourceKeyFromGenerationInformation(boolean b);

    boolean getComputeResourceKeyFromGenerationInformation();
}
