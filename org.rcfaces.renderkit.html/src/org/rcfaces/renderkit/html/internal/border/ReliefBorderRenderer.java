/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ReliefBorderRenderer extends AbstractHtmlBorderRenderer {

    private static final String RELIEF_BORDER_CLASS = "fb_relief";

    protected boolean hasBorder() {
        return true;
    }

    protected String getClassName() {
        return RELIEF_BORDER_CLASS;
    }

}
