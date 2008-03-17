/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.script;

import java.io.IOException;

import org.rcfaces.core.internal.content.IFileBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptFile extends IFileBuffer {

    void initialize(String contentType, byte scriptContent[], long lastModified)
            throws IOException;

}
