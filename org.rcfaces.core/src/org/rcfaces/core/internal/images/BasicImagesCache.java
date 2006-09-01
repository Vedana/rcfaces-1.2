/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.internal.images;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.images.ImageFiltersServlet.IBufferedImage;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicImagesCache implements IImagesCache {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(BasicImagesCache.class);

    // Il faut un cache des erreurs .... et des autres !

    private final Map caches;

    private final Map weakCache;

    private final int maxCacheSize;

    private List cacheList = new LinkedList();

    public BasicImagesCache(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;

        caches = new HashMap(maxCacheSize + 2);

        LOG.debug("Set max cache size to " + maxCacheSize + ".");

        if (Constants.IMAGES_CACHE_WEAK_MAP_ENABLED) {
            weakCache = new WeakHashMap(maxCacheSize);
            LOG.debug("Create a weak map initialized with size of "
                    + maxCacheSize + ".");

        } else {
            weakCache = null;
        }

    }

    public synchronized IBufferedImage searchInCache(String cmd, String url) {
        String key = computeKey(cmd, url);

        Cache cache = (Cache) caches.get(key);
        if (cache == null && weakCache != null) {
            cache = (Cache) weakCache.get(key);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Search image cmd='" + cmd + "' url='" + url + "' => "
                    + ((cache != null) ? "FOUND" : "FAILED"), null);
        }

        if (cache == null) {
            return null;
        }

        if (cacheList.get(0) != cache) {
            cacheList.remove(cache);
            cacheList.add(0, cache);
        }

        return cache.bufferedImage;
    }

    public synchronized void storeIntoCache(String cmd, String url,
            IBufferedImage bufferedImage) {
        String key = computeKey(cmd, url);

        Cache cache = (Cache) caches.get(key);
        if (cache != null) {
            cache.bufferedImage = bufferedImage;

            cacheList.remove(cache);

        } else {
            cache = new Cache(key, bufferedImage);
            caches.put(key, cache);
        }

        cacheList.add(0, cache);

        int cacheSize = caches.size();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Register image cmd='" + cmd + "' url='" + url
                    + "' cacheSize=" + cacheSize + ".");
        }

        if (cacheSize > maxCacheSize) {
            Cache oldest = (Cache) cacheList.remove(cacheSize - 1);

            caches.remove(oldest.key);

            if (weakCache != null) {
                weakCache.put(oldest.key, oldest);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Remove the oldest cached imageBuffered.");
            }
        }
    }

    private String computeKey(String cmd, String url) {
        return cmd + "\uffff" + url;
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class Cache {

        final String key;

        IBufferedImage bufferedImage;

        public Cache(String key, IBufferedImage bufferedImage) {
            this.key = key;
            this.bufferedImage = bufferedImage;
        }
    }
}
