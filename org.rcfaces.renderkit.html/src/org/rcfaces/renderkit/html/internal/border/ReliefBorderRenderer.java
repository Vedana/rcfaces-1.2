/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.renderkit.html.internal.border;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ReliefBorderRenderer extends AbstractHtmlBorderRenderer {
    private static final String REVISION = "$Revision$";

    private static final String RELIEF_BORDER_CLASS = "fb_relief";

    protected boolean hasBorder() {
        return true;
    }

    protected String getClassName() {
        return RELIEF_BORDER_CLASS;
    }

}
