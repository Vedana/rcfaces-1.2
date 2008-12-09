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
public class BasicContentModel extends AbstractContentModel {
    private static final String REVISION = "$Revision$";

    protected transient IGeneratedResourceInformation generatedInformation;

    protected transient IGenerationResourceInformation generationInformation;

    public BasicContentModel() {
    }

    public BasicContentModel(Object value) {
        super(value);
    }

    public void setInformations(
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {
        super.setInformations(generationInformation, generatedInformation);

        this.generationInformation = generationInformation;
        this.generatedInformation = generatedInformation;
    }
}