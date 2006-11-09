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

    public void setWidth(int width) {
        if (width < 1) {
            setAttribute(WIDTH_PROPERTY, null);
            return;
        }
        setAttribute(WIDTH_PROPERTY, new Integer(width));
    }

    public void setHeight(int height) {
        if (height < 1) {
            setAttribute(HEIGHT_PROPERTY, null);
            return;
        }
        setAttribute(HEIGHT_PROPERTY, new Integer(height));
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

}