/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.content.FileBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleSheetFileBuffer extends FileBuffer implements IStyleSheetFile {

    private static final Log LOG = LogFactory
            .getLog(StyleSheetFileBuffer.class);

    public StyleSheetFileBuffer(String bufferName) {
        super(bufferName);
    }

}
