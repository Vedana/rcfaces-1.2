/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

import java.io.IOException;

import org.rcfaces.core.internal.content.IBufferOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptOperation extends IBufferOperation {

    String filter(String scriptURL,
            String scriptContent, IScriptOperationContext operationContext)
            throws IOException;

}
