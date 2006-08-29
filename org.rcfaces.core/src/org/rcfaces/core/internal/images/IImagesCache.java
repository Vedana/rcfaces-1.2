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
 */
package org.rcfaces.core.internal.images;

import org.rcfaces.core.internal.images.ImageFiltersServlet.IBufferedImage;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IImagesCache {

    IBufferedImage searchInCache(String cmd, String url);

    void storeIntoCache(String cmd, String url, IBufferedImage bufferedImage);
}
