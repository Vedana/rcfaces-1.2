/*
 * $Id$
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractBufferedIndexedImageOperation extends
        AbstractBufferedImageOperation implements IIndexedImageOperation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractBufferedIndexedImageOperation.class);

    public IndexColorModel filter(Map<String, Object> requestParameter, IndexColorModel source,
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
