/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssRenderer {
    String[] getComponentStyleClassNames(IHtmlWriter htmlWriter);

    String getMainStyleClassName();

}
