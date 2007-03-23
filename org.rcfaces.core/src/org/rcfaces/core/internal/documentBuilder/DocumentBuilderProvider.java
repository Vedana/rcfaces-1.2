/*
 * $Id$
 */
package org.rcfaces.core.internal.documentBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.faces.FacesException;

import org.w3c.dom.Document;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DocumentBuilderProvider implements IDocumentBuilderProvider {
    private static final String REVISION = "$Revision$";

    public Document parse(Reader reader) throws IOException {
        throw new FacesException("Not implemented !");
    }

    public void serialize(Writer writer, Document document) throws IOException {
        throw new FacesException("Not implemented !");
    }

}
