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
 
    @Override
    protected boolean isItemSelectable() {
        return true;
    }

    @Override
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_BUTTON;
    }

}
