/*
 * $Id$
 */
package org.rcfaces.core.internal.content;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractBufferOperation implements IBufferOperation {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractBufferOperation.class);

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void configure(Map configuration) {
    }

}
