/*
 * $Id$
 */
package org.rcfaces.core.internal.images;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImagesCache {

    IBufferedImage searchInCache(String cmd, String url);

    void storeIntoCache(String cmd, String url, IBufferedImage bufferedImage);
}
