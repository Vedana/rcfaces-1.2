/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import org.rcfaces.core.internal.contentAccessor.ICompositeContentAccessorHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStyleContentAccessorHandler extends
        ICompositeContentAccessorHandler {

    String STYLE_CONTENT_PROVIDER_ID = "org.rcfaces.core.STYLE_CONTENT_PROVIDER";

    String MERGE_FILTER_NAME = "merge";

    String PROCESS_FILTER_NAME = "process";

    IStyleOperation getStyleOperation(String operationId);
}
