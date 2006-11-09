/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageWriter;

import org.rcfaces.core.internal.images.IImageLoaderFactory.IImageLoader;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public interface IBufferedImage {
    void initialize(IImageLoader imageDownloader, String contentType,
            RenderedImage renderedImage, ImageWriter imageWriter, int imageType)
            throws IOException;

    String getName();

    void initializeRedirection(String url) throws IOException;

    String getRedirection();

    int getSize();

    boolean isErrored();

    void setErrored();

    boolean isInitialized();

    InputStream getContent() throws IOException;

    String getContentType();

    long getModificationDate();

    String getHash();

    String getETag();
}