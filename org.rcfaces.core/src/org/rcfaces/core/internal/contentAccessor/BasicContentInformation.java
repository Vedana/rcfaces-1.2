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

    private static final String FILTRED_MODEL_PROPERTY = "org.rcfaces.org.FILTRED_MODEL";

    private static final String TRANSIENT_PROPERTY = "org.rcfaces.org.TRANSIENT_PROPERTY";

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

    public boolean isTransient() {
        Boolean val = (Boolean) getAttribute(TRANSIENT_PROPERTY);
        if (val == null) {
            return false;
        }

        return val.booleanValue();
    }

    public void setTransient(boolean transientState) {
        setAttribute(TRANSIENT_PROPERTY, Boolean.valueOf(transientState));
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attributes == null) ? 0 : attributes.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BasicContentInformation other = (BasicContentInformation) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        return true;
    }

}
