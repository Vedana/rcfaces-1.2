/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RasterOp;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractBufferedIndexedImageOperation extends
        AbstractBufferedImageOperation implements IIndexedImageOperation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractBufferedIndexedImageOperation.class);

    public IndexColorModel filter(Map requestParameter, IndexColorModel source,
            BufferedImage sourceImage) {
        RasterOp imageOperation;

        if (requestParameter.isEmpty() == false
                && ignoreRequestParameter() == false) {
            imageOperation = getImageOperation(sourceImage, requestParameter);

        } else {
            imageOperation = this.imageOperation;
        }

        if (imageOperation == null) {
            return source;
        }

        return filter(imageOperation, requestParameter, source);
    }

    protected abstract IndexColorModel filter(RasterOp imageOperation,
            Map requestParameter, IndexColorModel source);
}
