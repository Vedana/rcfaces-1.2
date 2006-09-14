/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
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

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractBufferedImageOperation extends
        AbstractImageOperation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractBufferedImageOperation.class);

    RasterOp imageOperation;

    public void configure(Map configuration) {
        this.imageOperation = getImageOperation(null, configuration);
    }

    protected abstract RasterOp getImageOperation(BufferedImage sourceImage,
            Map configuration);

    public BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination) {

        RasterOp imageOperation;

        if (requestParameter.isEmpty() == false
                && ignoreRequestParameter() == false) {
            imageOperation = getImageOperation(source, requestParameter);

        } else {
            imageOperation = this.imageOperation;
        }

        if (imageOperation == null) {
            return source;
        }

        int type = source.getType();
        if (type == BufferedImage.TYPE_BYTE_BINARY
                || type == BufferedImage.TYPE_BYTE_INDEXED) {
            type = BufferedImage.TYPE_INT_ARGB;
        }

        Rectangle2D dest = imageOperation.getBounds2D(source.getRaster());

        if (destination == null || destination.getType() != type
                || dest.getWidth() != source.getWidth()
                || dest.getHeight() != source.getHeight()) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Convert image to type '" + type + "'.");
            }

            destination = new BufferedImage((int) dest.getWidth(), (int) dest
                    .getHeight(), type);
        }

        filter0(imageOperation, source, destination);

        return destination;
    }

    protected void filter0(RasterOp imageOperation, BufferedImage source,
            BufferedImage destination) {

        imageOperation.filter(source.getRaster(), destination.getRaster());
    }

    protected boolean ignoreRequestParameter() {
        return false;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class ParametredRasterOp implements RasterOp {
        private static final String REVISION = "$Revision$";

        protected final RasterOp parent;

        protected ParametredRasterOp(RasterOp parent) {
            this.parent = parent;
        }

        public WritableRaster createCompatibleDestRaster(Raster src) {
            return parent.createCompatibleDestRaster(src);
        }

        public WritableRaster filter(Raster src, WritableRaster dest) {
            return parent.filter(src, dest);
        }

        public Rectangle2D getBounds2D(Raster src) {
            return parent.getBounds2D(src);
        }

        public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
            return parent.getPoint2D(srcPt, dstPt);
        }

        public RenderingHints getRenderingHints() {
            return parent.getRenderingHints();
        }

    }
}
