/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptRenderContext {

    Locale getUserLocale();

    String convertSymbol(String symbol);

}
