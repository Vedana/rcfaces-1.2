/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.HashMap;
import java.util.Map;

import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentInformation implements IContentInformation {
    private static final String REVISION = "$Revision$";

    protected static final String FILTRED_MODEL_PROPERTY = "org.rcfaces.org.FILTRED_MODEL";

    private Map attributes;

    public final String getContentType() {
        return (String) getAttribute(IContentModel.CONTENT_TYPE_PROPERTY);
    }

    public final void setContentType(String contentType) {
        setAttribute(IContentModel.CONTENT_TYPE_PROPERTY, contentType);
    }

    public Object getAttribute(String attributeName) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(attributeName);
    }

    public Object setAttribute(String attributeName, Object attributeValue) {
        if (attributes == null) {
            attributes = new HashMap();
        }

        return attributes.put(attributeName, attributeValue);
    }

    public boolean isFiltredModel() {
        Boolean val = (Boolean) getAttribute(FILTRED_MODEL_PROPERTY);
        if (val == null) {
            return false;
        }

        return val.booleanValue();
    }

    public void setFiltredModel(boolean filtredModel) {
        setAttribute(FILTRED_MODEL_PROPERTY, Boolean.valueOf(filtredModel));
    }

}
