/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.renderer;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageButtonRenderer extends ImageRenderer {
    private static final String REVISION = "$Revision$";

    protected boolean isItemSelectable() {
        return true;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_BUTTON;
    }

}
