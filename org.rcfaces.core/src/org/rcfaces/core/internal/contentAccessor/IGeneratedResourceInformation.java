/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.component.StateHolder;

import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGeneratedResourceInformation extends StateHolder {

    String RESPONSE_MIME_TYPE_PROPERTY = "org.rcfaces.response.MIME_TYPE";

    String RESPONSE_URL_SUFFIX_PROPERTY = "org.rcfaces.response.SUFFIX";

    String SOURCE_MIME_TYPE_PROPERTY = "org.rcfaces.source.MIME_TYPE";

    Object setAttribute(String attributeName, Object attributeValue);

    Object getAttribute(String attributeName);

    String getResponseMimeType();

    void setResponseMimeType(String mimeType);

    String getSourceMimeType();

    void setSourceMimeType(String mimeType);

    boolean isFiltredModel();

    void setFiltredModel(boolean filtredModel);

    IContentFamily getContentFamily();

    void setContentFamily(IContentFamily contentFamilly);

    void setProcessingAtRequest(boolean processDataAtRequest);

    boolean isProcessingAtRequest();

    boolean isProcessingAtRequestSetted();

    String getResponseSuffix();

    void setResponseSuffix(String suffix);

    void copyTo(IGeneratedResourceInformation generatedInformation);
}
