/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IObjectLiteralWriter {

	IJavaScriptWriter getParent();

	IJavaScriptWriter writeProperty(String propertyName) throws WriterException;

	IJavaScriptWriter writeSymbol(String symbol) throws WriterException;

	IJavaScriptWriter end() throws WriterException;
}
