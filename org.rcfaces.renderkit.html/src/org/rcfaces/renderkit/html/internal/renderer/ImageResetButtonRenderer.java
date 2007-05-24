/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageResetButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_RESET_BUTTON;
    }
}