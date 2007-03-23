/*
 * $Id$
 */
package org.rcfaces.core.internal.documentBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDocumentBuilderProvider {

    void serialize(Writer writer, Document document) throws IOException;

    Document parse(Reader reader) throws IOException;
}
