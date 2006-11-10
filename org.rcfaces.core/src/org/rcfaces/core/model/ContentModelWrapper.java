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
public class ContentModelWrapper implements IContentModel {
    private static final String REVISION = "$Revision$";

    private IContentModel contentModel;

    public void setContentModel(IContentModel contentModel) {
        this.contentModel = contentModel;
    }

    public IContentModel getContentModel() {
        return contentModel;
    }

    public boolean checkNotModified() {
        return contentModel.checkNotModified();
    }

    public Object getAttribute(String attributeName) {
        return contentModel.getAttribute(attributeName);
    }

    public Map getAttributes() {
        return contentModel.getAttributes();
    }

    public String getContentEngineId() {
        return contentModel.getContentEngineId();
    }

    public Object getWrappedData() {
        return contentModel.getWrappedData();
    }

    public boolean isProcessDataAtRequest() {
        return contentModel.isProcessDataAtRequest();
    }

    public void setContentEngineId(String contentEngineId) {
        contentModel.setContentEngineId(contentEngineId);
    }
}
