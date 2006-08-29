/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.3  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.2  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.4  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.3  2004/10/04 16:30:47  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
class MenuContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    public MenuContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            UIComponent rootComponent, Object value) {
        super(renderer, componentRenderContext, rootComponent, value);
    }
}