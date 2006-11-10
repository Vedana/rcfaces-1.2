/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
