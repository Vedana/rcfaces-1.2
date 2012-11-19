/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.script;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.script.AbstractScriptOperation;
import org.rcfaces.core.internal.script.IScriptOperationContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CompositeScriptsContentOperation extends AbstractScriptOperation {
    private static final Log LOG = LogFactory
            .getLog(CompositeScriptsContentOperation.class);

    public CompositeScriptsContentOperation() {
        setName("Merge script content");
    }

    public String filter(String scriptURL,
            String scriptContent, IScriptOperationContext operationContext)
            throws IOException {

        return null;
    }
}
