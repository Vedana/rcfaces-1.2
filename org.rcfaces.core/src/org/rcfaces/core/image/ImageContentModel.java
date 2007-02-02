/*
 * $Id$
 */
package org.rcfaces.core.image;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.rcfaces.core.model.BasicContentModel;

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
            return imageWriteParam;
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

    public boolean setCompressionQuality(float quality) {
        ImageWriteParam imageWriteParam = getImageWriteParam();
        if (imageWriteParam == null) {
            return false;
        }

        if (imageWriteParam.canWriteCompressed() == false) {
            return false;
        }

        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);

        return true;
    }

    public boolean setCompressionMode(int mode) {
        ImageWriteParam imageWriteParam = getImageWriteParam();
        if (imageWriteParam == null) {
            return false;
        }

        if (imageWriteParam.canWriteCompressed() == false) {
            return false;
        }

        imageWriteParam.setCompressionMode(mode);

        return true;
    }

    public boolean setCompressionType(String compressionType) {
        ImageWriteParam imageWriteParam = getImageWriteParam();
        if (imageWriteParam == null) {
            return false;
        }

        if (imageWriteParam.canWriteCompressed() == false) {
            return false;
        }

        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionType(compressionType);
        return true;
    }

    public boolean setProgressiveMode(boolean progressiveMode) {
        ImageWriteParam imageWriteParam = getImageWriteParam();
        if (imageWriteParam == null) {
            return false;
        }
        if (imageWriteParam.canWriteProgressive() == false) {
            return false;
        }

        imageWriteParam
                .setProgressiveMode((progressiveMode) ? ImageWriteParam.MODE_COPY_FROM_METADATA
                        : ImageWriteParam.MODE_DISABLED);

        return true;
    }
}
