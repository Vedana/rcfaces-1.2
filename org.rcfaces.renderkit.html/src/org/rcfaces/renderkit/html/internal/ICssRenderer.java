/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.renderkit.html.internal.renderer.ICssStyleClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssRenderer {

    ICssStyleClasses getCssStyleClasses(IHtmlWriter htmlWriter);
}
