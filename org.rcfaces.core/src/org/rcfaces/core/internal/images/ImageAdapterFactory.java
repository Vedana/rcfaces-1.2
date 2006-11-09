/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.adapter.IAdapterFactory;
import org.rcfaces.core.internal.contentStorage.AbstractResolvedContent;
import org.rcfaces.core.internal.contentStorage.IResolvedContent;
import org.rcfaces.core.model.IContentModel;
import org.rcfaces.core.model.ImageContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageAdapterFactory implements IAdapterFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ImageAdapterFactory.class);

    private static final String RGB_DEFAULT_CONTENT_TYPE = "image/jpeg";

    private static final String INDEX_DEFAULT_CONTENT_TYPE = "image/gif";

    private static final String TEMP_PREFIX = "imageAdapter_";

    private static final Map suffixByContentType = new HashMap(8);

    static {
        suffixByContentType.put("image/gif", "gif");
        suffixByContentType.put("image/jpg", "jpeg");
        suffixByContentType.put("image/jpeg", "jpeg");
        suffixByContentType.put("image/png", "png");
    }

    public Object getAdapter(Object adaptableObject, Class adapterType,
            Map parameters) {
        if (adaptableObject instanceof RenderedImage) {
            return adaptBufferedImage(adaptableObject, parameters);
        }

        if (adaptableObject instanceof RenderedImage[]) {
            RenderedImage renderedImages[] = (RenderedImage[]) adaptableObject;

            if (renderedImages.length < 1) {
                return null;
            }

            return adaptBufferedImage(renderedImages, parameters);
        }

        return null;
    }

    public static String getSuffixByContentType(String contentType) {
        return (String) suffixByContentType.get(contentType.toLowerCase());
    }

    public Class[] getAdapterList() {
        return new Class[] { IResolvedContent.class };
    }

    private IResolvedContent adaptBufferedImage(Object image, Map parameters) {

        String defaultContentType = RGB_DEFAULT_CONTENT_TYPE;
        if (image instanceof RenderedImage) {
            defaultContentType = getDefaultContentType((RenderedImage) image);

        } else if (image instanceof RenderedImage[]) {
            defaultContentType = getDefaultContentType(((RenderedImage[]) image)[0]);
        }

        String contentType = (String) parameters
                .get(IContentModel.CONTENT_TYPE_PROPERTY);
        if (contentType == null) {
            contentType = defaultContentType;
        }

        IOException ex = null;

        Iterator it = ImageIO.getImageWritersByMIMEType(contentType);
        if (it.hasNext()) {
            ImageWriter imageWriter = (ImageWriter) it.next();

            try {
                return writeBufferedImage(imageWriter, image, parameters,
                        contentType);

            } catch (IOException e) {
                ex = e;

            } finally {
                imageWriter.dispose();
            }
        }

        if (contentType.equals(defaultContentType) == false) {
            it = ImageIO.getImageWritersByMIMEType(defaultContentType);
            if (it.hasNext()) {
                ImageWriter imageWriter = (ImageWriter) it.next();

                try {
                    return writeBufferedImage(imageWriter, image, parameters,
                            contentType);

                } catch (IOException e) {
                    if (ex == null) {
                        ex = e;
                    }

                } finally {
                    imageWriter.dispose();
                }
            }
        }

        throw new FacesException("Unsupported image content type '"
                + contentType + "'.", ex);
    }

    private String getDefaultContentType(RenderedImage image) {
        ColorModel colorModel = image.getColorModel();
        if (colorModel instanceof IndexColorModel) {
            return INDEX_DEFAULT_CONTENT_TYPE;
        }

        return RGB_DEFAULT_CONTENT_TYPE;
    }

    private IResolvedContent writeBufferedImage(ImageWriter imageWriter,
            Object image, Map parameters, String contentType)
            throws IOException {

        String suffix = (String) parameters
                .get(IContentModel.URL_SUFFIX_PROPERTY);
        if (suffix == null) {
            suffix = getSuffixByContentType(contentType);
        }

        FileResolvedContent resolvedContent = createTempResolvedContent(
                contentType, (suffix != null) ? suffix : "unknown");

        FileOutputStream fout = new FileOutputStream(resolvedContent.getFile());
        try {
            ImageOutputStream imageOutputStream = ImageIO
                    .createImageOutputStream(fout);

            imageWriter.setOutput(imageOutputStream);

            ImageWriteParam imageWriteParam = null;

            Object imageMetaData = parameters
                    .get(ImageContentModel.IMAGE_WRITE_PARAM_PROPERTY);
            if (imageMetaData instanceof ImageWriteParam) {
                imageWriteParam = (ImageWriteParam) imageMetaData;
            }

            if (image instanceof RenderedImage) {
                imageWriter.write(null, new IIOImage((RenderedImage) image,
                        null, null), imageWriteParam);

            } else if (image instanceof RenderedImage[]) {
                RenderedImage renderedImages[] = (RenderedImage[]) image;

                if (imageWriter.canWriteSequence() == false) {
                    imageWriter.write(null, new IIOImage(renderedImages[0],
                            null, null), imageWriteParam);
                } else {
                    for (int i = 0; i < renderedImages.length; i++) {
                        imageWriter.write(null, new IIOImage(renderedImages[i],
                                null, null), imageWriteParam);
                    }
                }
            }

            imageWriter.dispose();

            imageOutputStream.close();

        } finally {
            fout.close();
        }

        return resolvedContent;
    }

    public FileResolvedContent createTempResolvedContent(String contentType,
            String suffix) throws IOException {
        File file = File.createTempFile(TEMP_PREFIX, "." + suffix);
        file.deleteOnExit();

        return new FileResolvedContent(contentType, suffix, file);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class FileResolvedContent extends AbstractResolvedContent {

        private static final long serialVersionUID = 2045867975901327708L;

        private File file;

        private String contentType;

        private String suffix;

        public FileResolvedContent() {
            // Pour la serialization !
        }

        public FileResolvedContent(String contentType, String suffix, File file) {
            this.file = file;
            this.contentType = contentType;
            this.suffix = suffix;
        }

        public String getContentType() {
            return contentType;
        }

        public String getURLSuffix() {
            return suffix;
        }

        public InputStream getInputStream() throws IOException {
            return new FileInputStream(file);
        }

        public long getModificationDate() {
            return file.lastModified();
        }

        public int getLength() {
            return (int) file.length();
        }

        public File getFile() {
            return file;
        }
    }
}
