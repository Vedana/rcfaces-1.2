/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.content.AbstractBufferOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractStyleOperation extends AbstractBufferOperation
        implements IStyleOperation {

    private static final Log LOG = LogFactory
            .getLog(AbstractStyleOperation.class);
}
