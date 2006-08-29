/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IScriptRenderContext {

    Locale getUserLocale();

    String convertSymbol(String symbol);

}
