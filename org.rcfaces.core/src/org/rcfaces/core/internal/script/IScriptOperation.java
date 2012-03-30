/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

import java.io.IOException;
import java.util.Map;

import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptOperation extends IBufferOperation {

    String filter(Map<String, Object> applicationParameters,
            IResourceLoaderFactory resourceLoaderFactory, String scriptURL,
            String scriptContent, IScriptOperationContext operationContext)
            throws IOException;

}
