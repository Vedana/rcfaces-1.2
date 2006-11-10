/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.version.HashCodeTools;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentStorageRepository implements IContentStorageRepository {
    private static final String REVISION = "$Revision$";

    private static int id;

    private final BasicContentCache resolvedContentByKey;

    public BasicContentStorageRepository() {
        resolvedContentByKey = new BasicContentCache(
                Constants.BASIC_CONTENT_CACHE_SIZE);
    }

    public IResolvedContent load(String key) {
        synchronized (resolvedContentByKey) {
            return (IResolvedContent) resolvedContentByKey.get(key);
        }
    }

    public String save(IResolvedContent content, IContentModel contentModel) {
        String key = contentModel.getContentEngineId();

        if (key == null) {
            key = computeURL(content);

            contentModel.setContentEngineId(key);
        }

        synchronized (resolvedContentByKey) {
            resolvedContentByKey.put(key, content);
        }

        return key;
    }

    private String computeURL(IResolvedContent content) {

        String resourceKey = content.getResourceKey();
        if (resourceKey != null) {
            resourceKey = HashCodeTools.computeURLFormat(null, resourceKey,
                    resourceKey, -1);

            StringAppender sa = new StringAppender(resourceKey, 32);

            long date = content.getModificationDate();
            if (date > 0) {
                sa.append(date);
            } else {
                int length = content.getLength();
                if (length > 0) {
                    sa.append(length);
                }
            }

            String suffix = content.getURLSuffix();
            if (suffix != null) {
                sa.append('.');
                sa.append(suffix);
            }

            content.setVersioned(true);
            return sa.toString();
        }

        int id;
        synchronized (BasicContentStorageRepository.class) {
            id = BasicContentStorageRepository.id++;
        }

        String key = System.currentTimeMillis() + "-" + id;

        String suffix = content.getURLSuffix();
        if (suffix != null) {
            key += "." + suffix;
        }

        content.setVersioned(false);
        return key;
    }

}
