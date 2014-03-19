/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.script;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.content.FileBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ScriptFileBuffer extends FileBuffer implements IScriptFile {

    private static final Log LOG = LogFactory.getLog(ScriptFileBuffer.class);

    public ScriptFileBuffer(String bufferName) {
        super(bufferName);
    }

}
