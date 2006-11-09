/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractContentModel implements IContentModel {
    private static final String REVISION = "$Revision$";

    private Object wrappedData;

    private String contentEngineId;

    public AbstractContentModel() {
    }

    public AbstractContentModel(Object wrappedData) {
        this.wrappedData = wrappedData;
    }

    public Object getWrappedData() {
        return wrappedData;
    }

    public void setWrappedData(Object wrappedData) {
        this.wrappedData = wrappedData;
    }

    public String computeURL() {
        return computeURL(this);
    }

    public int getWidth() {
        Integer width = (Integer) getAttribute(WIDTH_PROPERTY);
        if (width == null) {
            return 0;
        }

        return width.intValue();
    }

    public int getHeight() {
        Integer height = (Integer) getAttribute(HEIGHT_PROPERTY);
        if (height == null) {
            return 0;
        }

        return height.intValue();
    }

    public String getContentType() {
        return (String) getAttribute(CONTENT_TYPE_PROPERTY);
    }

    public String getURLSuffix() {
        return (String) getAttribute(URL_SUFFIX_PROPERTY);
    }

    public static String computeURL(IContentModel contentModel) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        IContentAccessor contentAccessor = ContentAccessorFactory
                .createFromWebResource(contentModel, IContentType.USER);

        if (contentAccessor == null) {
            return null;
        }

        return contentAccessor.resolveURL(facesContext, null, null);
    }

    public String getContentEngineId() {
        return contentEngineId;
    }

    public void setContentEngineId(String contentEngineId) {
        this.contentEngineId = contentEngineId;
    }

    public boolean isProcessDataAtRequest() {
        Object processDataAtRequest = getAttribute(PROCESS_DATA_AT_REQUEST);

        if (processDataAtRequest == null) {
            return false;
        }

        if (processDataAtRequest instanceof Boolean) {
            return ((Boolean) processDataAtRequest).booleanValue();
        }

        return Boolean.valueOf(String.valueOf(processDataAtRequest))
                .booleanValue();
    }

    public boolean checkNotModified() {
        return true;
    }

}
