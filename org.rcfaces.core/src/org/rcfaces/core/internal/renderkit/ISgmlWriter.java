/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISgmlWriter extends IComponentWriter {

    String NBSP = "\u00A0";

    ISgmlWriter write(String s) throws WriterException;

    ISgmlWriter writeText(String s) throws WriterException;

    ISgmlWriter write(char c) throws WriterException;

    ISgmlWriter write(int value) throws WriterException;

    ISgmlWriter writeComment(String comment) throws WriterException;

    ISgmlWriter writeAttribute(String name, String value)
            throws WriterException;

    ISgmlWriter writeAttribute(String name) throws WriterException;

    ISgmlWriter writeAttribute(String name, long value) throws WriterException;

    ISgmlWriter write(char[] buffer, int offset, int length)
            throws WriterException;

    ISgmlWriter startElement(String name) throws WriterException;

    ISgmlWriter startElement(String name, UIComponent component)
            throws WriterException;

    ISgmlWriter endElement(String name) throws WriterException;

    ISgmlWriter writeURIAttribute(String name, Object value)
            throws WriterException;

    ISgmlWriter writeln() throws WriterException;

}
