/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentStorageRepository {
    String save(IResolvedContent content, IContentModel contentModel);

    IResolvedContent load(String key);
}
