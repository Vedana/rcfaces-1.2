/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentModel {

    String PROCESS_DATA_AT_REQUEST = "org.rfcaces.core.model.PROCESS_DATA_AT_REQUEST";

    String EXPIRATION_PROPERTY = "org.rfcaces.core.model.EXPIRATION";

    String CONTENT_TYPE_PROPERTY = "org.rcfaces.ContentType";

    String URL_SUFFIX_PROPERTY = "org.rcfaces.url.Suffix";

    String WIDTH_PROPERTY = "org.rfcaces.core.model.WIDTH";

    String HEIGHT_PROPERTY = "org.rfcaces.core.model.HEIGHT";

    Object getAttribute(String attributeName);

    Map getAttributes();

    Object getWrappedData();

    String getContentEngineId();

    void setContentEngineId(String contentEngineId);

    boolean isProcessDataAtRequest();

    boolean checkNotModified();
}
