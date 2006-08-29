/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageSubmitButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_SUBMIT_BUTTON;
    }

}
