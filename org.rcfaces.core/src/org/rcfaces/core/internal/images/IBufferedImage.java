/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.imageio.ImageWriter;

import org.rcfaces.core.internal.content.IFileBuffer;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory.IResourceLoader;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public interface IBufferedImage extends IFileBuffer {
    void initialize(IResourceLoader imageDownloader, String contentType,
            RenderedImage renderedImage, ImageWriter imageWriter,
            int imageType, long lastModified) throws IOException;
}