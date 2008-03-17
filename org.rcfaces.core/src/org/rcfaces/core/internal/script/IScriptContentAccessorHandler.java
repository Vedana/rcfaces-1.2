/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

import org.rcfaces.core.internal.contentAccessor.ICompositeContentAccessorHandler;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptContentAccessorHandler extends
        ICompositeContentAccessorHandler {

    String SCRIPT_CONTENT_PROVIDER_ID = "org.rcfaces.core.SCRIPT_CONTENT_PROVIDER";

    IScriptOperation getScriptOperation(String operationId);

    boolean isOperationSupported(String operationId,
            IContentAccessor contentAccessor);
}
