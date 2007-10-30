/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.content.FileBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleSheetFileBuffer extends FileBuffer implements IStyleSheetFile {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(StyleSheetFileBuffer.class);

    private static final int INITIAL_BUFFER_SIZE = 8 * 1024;

    private static final String DEFAULT_CHARSET = "UTF-8";

    public StyleSheetFileBuffer(String bufferName) {
        super(bufferName);
    }

}
