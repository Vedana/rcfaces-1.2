/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentInformation implements IContentInformation {
    private static final String REVISION = "$Revision$";

    private String contentType;

    private Map attributes;

    public final String getContentType() {
        return contentType;
    }

    public final void setContentType(String contentType) {
        this.contentType = contentType;
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

}
