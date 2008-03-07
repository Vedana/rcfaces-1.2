/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentModel extends AbstractContentModel {
    private static final String REVISION = "$Revision$";

    private Map attributes;

    public BasicContentModel() {
    }

    public BasicContentModel(Object value) {
        super(value);
    }

    public BasicContentModel(Object value, Map attributes) {
        super(value);

        putAllAttributes(attributes);
    }

    protected Map getAttributeMap(boolean create) {
        if (create == false || attributes != null) {
            return attributes;
        }

        attributes = new HashMap();

        return attributes;
    }

    public Object getAttribute(String attributeName) {
        Map attributes = getAttributeMap(false);

        if (attributes == null) {
            return null;
        }

        return attributes.get(attributeName);
    }

    public Object setAttribute(String attributeName, Object attributeValue) {
        Map attributes = getAttributeMap(true);

        return attributes.put(attributeName, attributeValue);
    }

    public Object removeAttribute(String attributeName) {
        Map attributes = getAttributeMap(false);

        if (attributes == null) {
            return null;
        }
        return attributes.remove(attributeName);
    }

    public void putAllAttributes(Map newAttributes) {
        if (newAttributes.isEmpty()) {
            return;
        }

        Map attributes = getAttributeMap(true);

        attributes.putAll(newAttributes);
    }

    public void clearAttributes() {
        Map attributes = getAttributeMap(false);

        if (attributes == null) {
            return;
        }
        attributes.clear();
    }

    public Map getAttributes() {
        Map attributes = getAttributeMap(false);

        if (attributes == null || attributes.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        return new HashMap(attributes);
    }

    public void setContentType(String contentType) {
        setAttribute(CONTENT_TYPE_PROPERTY, contentType);
    }

    public void setURLSuffix(String suffix) {
        setAttribute(URL_SUFFIX_PROPERTY, suffix);
    }

    public void setProcessDataAtRequest(boolean state) {
        setAttribute(PROCESS_DATA_AT_REQUEST, (state) ? Boolean.TRUE : null);
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
        final BasicContentModel other = (BasicContentModel) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        return true;
    }

}