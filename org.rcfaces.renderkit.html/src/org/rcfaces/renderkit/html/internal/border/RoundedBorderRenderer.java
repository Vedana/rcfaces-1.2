/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RoundedBorderRenderer extends AbstractFieldSetBorderRenderer {
    private static final String REVISION = "$Revision$";

    private static final String FIELDSET_BORDER_CLASS = "fb_rounded";

    protected String getClassName() {
        return FIELDSET_BORDER_CLASS;
    }

    protected int getEastBorderWidth() {
        return 8;
    }

    protected int getNorthBorderHeight() {
        return 17;
    }

    protected int getSouthBorderHeight() {
        return 9;
    }

    protected int getWestBorderWidth() {
        return 8;
    }
}
