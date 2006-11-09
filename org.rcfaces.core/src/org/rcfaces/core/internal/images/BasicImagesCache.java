/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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

        if (Constants.IMAGES_WEAK_MAP_CACHE_ENABLED) {
            weakCache = new WeakHashMap(maxCacheSize);
            LOG.debug("Create a weak map initialized with size " + maxCacheSize
                    + ".");

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
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
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
