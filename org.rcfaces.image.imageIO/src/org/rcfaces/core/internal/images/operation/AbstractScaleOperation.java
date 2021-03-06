/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.images.operation;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RasterOp;
import java.util.Map;

import org.rcfaces.core.image.AbstractBufferedImageOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractScaleOperation extends
        AbstractBufferedImageOperation {
    private static final String REVISION = "$Revision$";

    protected RasterOp getImageOperation(BufferedImage sourceImage,
            Map configuration) {
        float scales[] = computeScales(sourceImage, configuration);

        if (scales == null) {
            return null;
        }

        if (scales[0] == 1.0f && scales[1] == 1.0f) {
            return null;
        }
        if (scales[0] == 0.0f && scales[1] == 0.0f) {
            return null;
        }

        AffineTransform tx = new AffineTransform();
        tx.scale(scales[0], scales[1]);

        return new AffineTransformOp(tx, getInterpolationType());
    }

    protected int getInterpolationType() {
        return AffineTransformOp.TYPE_BILINEAR;
    }

    protected abstract float[] computeScales(BufferedImage sourceImage,
            Map configuration);
}
