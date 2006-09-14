/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.core.internal.images;

import org.rcfaces.core.internal.images.ImageOperationsServlet.IBufferedImage;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImagesCache {

    IBufferedImage searchInCache(String cmd, String url);

    void storeIntoCache(String cmd, String url, IBufferedImage bufferedImage);
}
