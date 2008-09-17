/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.HashMap;
import java.util.Map;

import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentModel extends AbstractContentModel {
    private static final String REVISION = "$Revision$";

    private Map attributes;

    protected transient IGeneratedResourceInformation generatedInformation;

    protected transient IGenerationResourceInformation generationInformation;

    public BasicContentModel() {
    }

    public BasicContentModel(Object value) {
        super(value);
    }

    public BasicContentModel(Object value, Map attributes) {
        super(value);

        this.attributes = new HashMap(attributes);
    }

    public void setInformations(
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {
        super.setInformations(generationInformation, generatedInformation);

        this.generationInformation = generationInformation;
        this.generatedInformation = generatedInformation;
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