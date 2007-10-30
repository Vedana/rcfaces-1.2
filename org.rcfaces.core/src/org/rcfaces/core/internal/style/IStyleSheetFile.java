/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import java.io.IOException;

import org.rcfaces.core.internal.content.IFileBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStyleSheetFile extends IFileBuffer {

    void initialize(String contentType, byte styleSheetContent[], long lastModified) throws IOException;
}
