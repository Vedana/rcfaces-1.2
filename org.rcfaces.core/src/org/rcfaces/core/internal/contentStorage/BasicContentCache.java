/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentCache {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(BasicContentCache.class);

    // Il faut un cache des erreurs .... et des autres !

    private final Map caches;

    private final Map weakCache;

    private final int maxCacheSize;

    private List cacheList = new LinkedList();

    public BasicContentCache(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;

        caches = new HashMap(maxCacheSize + 2);

        LOG.debug("Set max cache size to " + maxCacheSize + ".");

        if (Constants.BASIC_CONTENT_WEAK_CACHE_ENABLED) {
            weakCache = new WeakHashMap(maxCacheSize);
            LOG.debug("Create a weak map initialized with size " + maxCacheSize
                    + ".");

        } else {
            weakCache = null;
        }

    }

    public synchronized Serializable get(String key) {
        Cache cache = (Cache) caches.get(key);
        if (cache == null && weakCache != null) {
            cache = (Cache) weakCache.get(key);
        }

        if (cache == null) {
            return null;
        }

        if (cacheList.get(0) != cache) {
            cacheList.remove(cache);
            cacheList.add(0, cache);
        }

        return cache.serializable;
    }

    public synchronized void put(String key, Serializable serializable) {
        Cache cache = (Cache) caches.get(key);
        if (cache != null) {
            cache.serializable = serializable;

            cacheList.remove(cache);

        } else {
            cache = new Cache(key, serializable);
            caches.put(key, cache);
        }

        cacheList.add(0, cache);

        int cacheSize = caches.size();

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Register key='" + key + "' cacheSize=" + cacheSize
                            + ".");
        }

        if (cacheSize > maxCacheSize) {
            Cache oldest = (Cache) cacheList.remove(cacheSize - 1);

            caches.remove(oldest.key);

            if (weakCache != null) {
                weakCache.put(oldest.key, oldest);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Remove the oldest cached.");
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
        private static final String REVISION = "$Revision$";

        final String key;

        Serializable serializable;

        public Cache(String key, Serializable serializable) {
            this.key = key;
            this.serializable = serializable;
        }
    }
}
