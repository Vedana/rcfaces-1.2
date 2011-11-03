/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageIOResourceAdapter extends IImageResourceAdapter {
    BufferedImage adaptContent(FacesContext facesContext,
            InputStream inputStream,
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation);
}
