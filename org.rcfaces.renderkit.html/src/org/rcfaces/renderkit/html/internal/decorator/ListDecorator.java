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
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.HtmlTools;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListDecorator extends ComboDecorator {
    private static final String REVISION = "$Revision$";

    public ListDecorator(UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {
        super(component, filterProperties, jsVersion);
    }

    protected void decodeList(UIInput component, IComponentData componentData) {
        String value = componentData.getStringProperty("value");
        if (value != null) {
            if (value.length() < 1) {
                component.setSubmittedValue(null);
                return;
            }

            StringTokenizer st = new StringTokenizer(value,
                    HtmlTools.LIST_SEPARATORS);
            Set s = new HashSet(st.countTokens());
            for (; st.hasMoreTokens();) {
                s.add(st.nextToken());
            }

            component.setSubmittedValue(s.toArray());
            return;
        }

        String values[] = componentData.getComponentParameters();

        component.setSubmittedValue(values);
    }

}
