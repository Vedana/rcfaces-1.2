/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageContentModel extends BasicContentModel {
    private static final String REVISION = "$Revision$";

    public static final String IMAGE_WRITE_PARAM_PROPERTY = "javax.imageio.ImageWriteParam";

    public ImageWriteParam getImageWriteParam() {
        ImageWriteParam imageWriteParam = (ImageWriteParam) getAttribute(IMAGE_WRITE_PARAM_PROPERTY);
        if (imageWriteParam != null) {
            return null;
        }

        String contentType = getContentType();
        if (contentType != null) {
            Iterator it = ImageIO.getImageWritersByMIMEType(contentType);
            if (it.hasNext()) {
                ImageWriter imageWriter = (ImageWriter) it.next();
                try {
                    imageWriteParam = imageWriter.getDefaultWriteParam();
                    if (imageWriteParam != null) {
                        setAttribute(IMAGE_WRITE_PARAM_PROPERTY,
                                imageWriteParam);

                        return imageWriteParam;
                    }
                } finally {
                    imageWriter.dispose();
                }
            }
        }

        String suffix = getURLSuffix();
        if (suffix != null) {
            Iterator it = ImageIO.getImageWritersBySuffix(suffix);
            if (it.hasNext()) {
                ImageWriter imageWriter = (ImageWriter) it.next();
                try {
                    imageWriteParam = imageWriter.getDefaultWriteParam();
                    if (imageWriteParam != null) {
                        setAttribute(IMAGE_WRITE_PARAM_PROPERTY,
                                imageWriteParam);

                        return imageWriteParam;
                    }
                } finally {
                    imageWriter.dispose();
                }
            }
        }

        return null;
    }
}
