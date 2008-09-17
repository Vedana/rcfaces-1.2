/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicGeneratedResourceInformation extends AbstractInformation
        implements IGeneratedResourceInformation {
    private static final String REVISION = "$Revision$";

    private static final String FILTRED_MODEL_PROPERTY = "org.rcfaces.response.FILTRED_MODEL";

    private static final String PROCESSED_AT_REQUEST_PROPERTY = "org.rcfaces.response.PROCESSED_AT_REQUEST";

    private static final String RESOURCE_SUFFIX_PROPERTY = "org.rcfaces.response.RESOURCE_SUFFIX";

    private IContentFamily contentFamily;

    public BasicGeneratedResourceInformation() {
    }

    public final String getResponseMimeType() {
        return (String) getAttribute(IContentModel.RESPONSE_MIME_TYPE_PROPERTY);
    }

    public final void setResponseMimeType(String contentType) {
        setAttribute(IContentModel.RESPONSE_MIME_TYPE_PROPERTY, contentType);
    }

    public final String getSourceMimeType() {
        return (String) getAttribute(IContentModel.SOURCE_MIME_TYPE_PROPERTY);
    }

    public final void setSourceMimeType(String contentType) {
        setAttribute(IContentModel.SOURCE_MIME_TYPE_PROPERTY, contentType);
    }

    public boolean isFiltredModel() {
        Boolean val = (Boolean) getAttribute(FILTRED_MODEL_PROPERTY);
        if (val == null) {
            return false;
        }

        return val.booleanValue();
    }

    public void setProcessingAtRequest(boolean processedAtRequest) {
        setAttribute(PROCESSED_AT_REQUEST_PROPERTY, Boolean
                .valueOf(processedAtRequest));
    }

    public boolean isProcessingAtRequest() {
        Boolean val = (Boolean) getAttribute(PROCESSED_AT_REQUEST_PROPERTY);
        if (val == null) {
            return false;
        }

        return val.booleanValue();
    }

    public void setFiltredModel(boolean filtredModel) {
        setAttribute(FILTRED_MODEL_PROPERTY, Boolean.valueOf(filtredModel));
    }

    public IContentFamily getContentFamily() {
        return contentFamily;
    }

    public void setContentFamily(IContentFamily contentFamilly) {
        this.contentFamily = contentFamilly;
    }

    public String getResponseSuffix() {
        return (String) getAttribute(RESOURCE_SUFFIX_PROPERTY);
    }

    public void setResponseSuffix(String suffix) {
        setAttribute(RESOURCE_SUFFIX_PROPERTY, suffix);
    }

    public void restoreState(FacesContext context, Object state) {
        Object states[] = (Object[]) state;

        super.restoreState(context, states[0]);

        if (states[1] != null) {
            contentFamily = ContentFamilies
                    .getContentFamillyByOrdinal(((Integer) states[1])
                            .intValue());
        }
    }

    public Object saveState(FacesContext context) {
        Object states[] = new Object[2];

        states[0] = super.saveState(context);
        if (contentFamily != null) {
            states[1] = new Integer(contentFamily.getOrdinal());
        }

        return states;
    }

}
