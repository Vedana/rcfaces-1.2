/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.image;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.internal.images.AbstractImageResourceAdapter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGImageResourceAdapter extends AbstractImageResourceAdapter {

    private static final Log LOG = LogFactory
            .getLog(SVGImageResourceAdapter.class);

    private final Object batickBridge_Lock = new Object();

    private BatikLazyLoadingBridge batikBridge;

    public SVGImageResourceAdapter() {
    }

    protected BatikLazyLoadingBridge getBatikBridge() {
        synchronized (batickBridge_Lock) {
            if (batikBridge == null) {
                batikBridge = new BatikLazyLoadingBridge();
            }
        }

        return batikBridge;
    }

    @Override
    public BufferedImage adaptContent(FacesContext facesContext,
            InputStream inputStream,
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {

        return getBatikBridge().adaptContent(facesContext, inputStream,
                generationInformation, generatedInformation);
    }
}
