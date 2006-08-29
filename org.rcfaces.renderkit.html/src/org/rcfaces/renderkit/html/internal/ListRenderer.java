/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.9  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.8  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.7  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.6  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.5  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.4  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.3  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.2  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 * Revision 1.8  2004/08/27 09:57:21  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ListComponent;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ListDecorator;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ListRenderer extends ComboRenderer {
    private static final String REVISION = "$Revision$";

    private static final int DEFAULT_ROW_NUMBER = 4;

    protected boolean isMultipleSelect(UIComponent comboBox) {
        ListComponent listBox = (ListComponent) comboBox;

        return listBox.isMultipleSelect();
    }

    protected int getRowNumber(UIComponent comboBox) {
        ListComponent listBox = (ListComponent) comboBox;
        int rowNumber = listBox.getRowNumber();
        if (rowNumber > 0) {
            return rowNumber;
        }

        return DEFAULT_ROW_NUMBER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.LIST;
    }

    protected IComponentDecorator createComboDecorator(
            FacesContext facesContext, UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {

        return new ListDecorator(component, filterProperties, jsVersion);
    }
}