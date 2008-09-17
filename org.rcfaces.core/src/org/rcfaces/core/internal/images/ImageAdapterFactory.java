/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
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
import org.rcfaces.core.image.ImageContentModel;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.contentStorage.AbstractResolvedContent;
import org.rcfaces.core.internal.contentStorage.IResolvedContent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.util.Base64;
import org.rcfaces.core.lang.IAdapterFactory;
import org.rcfaces.core.model.IContentModel;

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

    private static final FileNameMap fileNameMap = URLConnection
            .getFileNameMap();

    public Object getAdapter(Object adaptableObject, Class adapterType,
            Object parameter) {
        if (adaptableObject instanceof RenderedImage) {
            return adaptBufferedImage(adaptableObject, parameter);
        }

        if (adaptableObject instanceof RenderedImage[]) {
            RenderedImage renderedImages[] = (RenderedImage[]) adaptableObject;

            if (renderedImages.length < 1) {
                return null;
            }

            return adaptBufferedImage(renderedImages, parameter);
        }

        return null;
    }

    public static String getSuffixByContentType(String contentType) {
        return (String) suffixByContentType.get(contentType.toLowerCase());
    }

    public Class[] getAdapterList() {
        return new Class[] { IResolvedContent.class };
    }

    private IResolvedContent adaptBufferedImage(Object image, Object parameter) {
        Map parameters = Collections.EMPTY_MAP;
        if (parameter instanceof Map) {
            parameters = (Map) parameter;
        }

        String defaultContentType = RGB_DEFAULT_CONTENT_TYPE;
        if (image instanceof RenderedImage) {
            defaultContentType = getDefaultContentType((RenderedImage) image);

        } else if (image instanceof RenderedImage[]) {
            defaultContentType = getDefaultContentType(((RenderedImage[]) image)[0]);
        }

        String contentType = (String) parameters
                .get(IContentModel.RESPONSE_MIME_TYPE_PROPERTY);

        if (contentType == null) {
            String suffix = (String) parameters
                    .get(IContentModel.RESPONSE_URL_SUFFIX_PROPERTY);

            if (suffix != null) {
                contentType = fileNameMap.getContentTypeFor("x." + suffix);
            }
        }

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
                .get(IContentModel.RESPONSE_URL_SUFFIX_PROPERTY);
        if (suffix == null) {
            suffix = getSuffixByContentType(contentType);
        }

        File file = createTempFile(contentType, (suffix != null) ? suffix
                : "unknown");

        FileOutputStream fout = new FileOutputStream(file);
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

        return new FileResolvedContent(contentType, suffix, file);
    }

    public File createTempFile(String contentType, String suffix)
            throws IOException {
        File file = File.createTempFile(TEMP_PREFIX, "." + suffix);
        file.deleteOnExit();

        return file;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class FileResolvedContent extends AbstractResolvedContent implements
            Serializable {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 2045867975901327708L;

        private final String contentType;

        private final String suffix;

        private final int length;

        private final long lastModificationDate;

        private String etag;

        private String hashCode;

        private byte fileSerialized[];

        private transient File file;

        public FileResolvedContent(String contentType, String suffix, File file) {
            this.file = file;
            this.contentType = contentType;
            this.suffix = suffix;

            this.length = (int) file.length();
            this.lastModificationDate = file.lastModified();

            if (Constants.ETAG_SUPPORT || Constants.HASH_SUPPORT) {
                computeHashCodes();
            }
        }

        public String getContentType() {
            return contentType;
        }

        public String getURLSuffix() {
            return suffix;
        }

        public InputStream getInputStream() throws IOException {
            if (fileSerialized != null) {
                return new ByteArrayInputStream(fileSerialized);
            }

            return new FileInputStream(file);
        }

        public long getModificationDate() {
            return lastModificationDate;
        }

        public int getLength() {
            return length;
        }

        public String getETag() {
            return etag;
        }

        public String getHash() {
            return hashCode;
        }

        protected void finalize() throws Throwable {
            if (file != null) {
                try {
                    file.delete();
                    file = null;

                } catch (Throwable ex) {
                    LOG.error("Can not delete file '" + file + "'.", ex);
                }
            }
            super.finalize();
        }

        protected void computeHashCodes() {
            MessageDigest etagMessageDigest = null;
            MessageDigest hashMessageDigest = null;

            if (Constants.ETAG_SUPPORT) {
                try {
                    etagMessageDigest = MessageDigest
                            .getInstance(Constants.ETAG_DIGEST_ALGORITHM);

                } catch (NoSuchAlgorithmException ex) {
                    LOG.error("Can not find algorithm '"
                            + Constants.HASH_DIGEST_ALGORITHM + "'.", ex);
                }
            }

            if (Constants.HASH_SUPPORT) {
                try {
                    hashMessageDigest = MessageDigest
                            .getInstance(Constants.HASH_DIGEST_ALGORITHM);

                } catch (NoSuchAlgorithmException ex) {
                    LOG.error("Can not find algorithm '"
                            + Constants.HASH_DIGEST_ALGORITHM + "'.", ex);
                }
            }

            if (hashMessageDigest == null && etagMessageDigest == null) {
                return;
            }

            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);

            } catch (FileNotFoundException e) {
                LOG.error("Can not compute Etag and Hashcode for file '" + file
                        + "'." + e);
                return;
            }

            try {
                byte buffer[] = new byte[4096];

                for (;;) {
                    int ret = fileInputStream.read(buffer);
                    if (ret < 1) {
                        break;
                    }

                    if (etagMessageDigest != null) {
                        etagMessageDigest.update(buffer, 0, ret);
                    }

                    if (hashMessageDigest != null) {
                        hashMessageDigest.update(buffer, 0, ret);
                    }
                }

                if (etagMessageDigest != null) {
                    byte etagDigest[] = etagMessageDigest.digest();

                    StringAppender sb = new StringAppender(
                            etagDigest.length * 2 + 16);
                    sb.append("\"rcfaces:");
                    for (int i = 0; i < etagDigest.length; i++) {
                        int v = etagDigest[i] & 0xff;
                        if (v < 16) {
                            sb.append('0');
                        }
                        sb.append(Integer.toHexString(v));
                    }

                    sb.append(':');
                    sb.append(Integer.toHexString(length));

                    sb.append('\"');
                    etag = sb.toString();

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Etag for file '" + file + "' = " + etag);
                    }
                }

                if (hashMessageDigest != null) {
                    byte hashDigest[] = hashMessageDigest.digest();

                    hashCode = Base64.encodeBytes(hashDigest);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Hashcode for file '" + file + "' = "
                                + hashCode);
                    }
                }

            } catch (IOException e) {
                LOG.error("Can not compute Etag and Hashcode for file '" + file
                        + "'.", e);

            } finally {
                try {
                    fileInputStream.close();

                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }

        private void writeObject(java.io.ObjectOutputStream s)
                throws java.io.IOException {

            if (file != null) {
                fileSerialized = new byte[length];

                FileInputStream fin = new FileInputStream(file);
                try {
                    fin.read(fileSerialized);

                } catch (IOException ex) {
                    LOG.error(ex);

                } finally {
                    try {
                        fin.close();

                    } catch (IOException ex) {
                        LOG.error(ex);
                    }
                }

                file = null;
            }

            s.defaultWriteObject();
        }
    }
}
