/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageResourceAdapter implements
        IImageIOResourceAdapter {

    private static final Log LOG = LogFactory
            .getLog(AbstractImageResourceAdapter.class);

    public boolean isContentSupported(String contentType, String suffix) {
        return true;
    }

    public BufferedImage adaptContent(FacesContext facesContext,
            InputStream inputStream,
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {
        return null;
    }

}
