/*
 * $Id$
 */
package org.rcfaces.core.model;

import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

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

    public String getContentEngineId() {
        return contentModel.getContentEngineId();
    }

    public Object getWrappedData() {
        return contentModel.getWrappedData();
    }

    public void setContentEngineId(String contentEngineId) {
        contentModel.setContentEngineId(contentEngineId);
    }

    public boolean equals(Object obj) {
        return contentModel.equals(obj);
    }

    public int hashCode() {
        return contentModel.hashCode();
    }

    public void setInformations(IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {
        contentModel.setInformations(generationInformation,
                generatedInformation);
    }

}
