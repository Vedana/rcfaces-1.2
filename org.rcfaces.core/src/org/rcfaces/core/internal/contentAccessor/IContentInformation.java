/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentInformation {
    Object getAttribute(String attributeName);

    Object setAttribute(String attributeName, Object attributeValue);

    String getContentType();

    void setContentType(String contentType);

    boolean isFiltredModel();

    void setFiltredModel(boolean filtredModel);

    boolean isTransient();

    void setTransient(boolean transientState);
}
