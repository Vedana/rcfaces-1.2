/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.3  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.1  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.tools.ValuesTools;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HtmlValuesTools extends ValuesTools {
    private static final String REVISION = "$Revision$";

    private static final Object[] OBJECT_EMPTY_ARRAY = new Object[0];

    public static List parseValues(FacesContext facesContext,
            UIComponent component, boolean convert, boolean testValue,
            String values) {
        StringTokenizer st = new StringTokenizer(values,
                HtmlTools.LIST_SEPARATORS);
        if (st.hasMoreTokens() == false) {
            return Collections.EMPTY_LIST;
        }

        List tokens = new ArrayList(st.countTokens());
        for (; st.hasMoreTokens();) {
            tokens.add(st.nextToken());
        }

        if (convert == false) {
            return tokens;
        }

        Object vs[] = convertStringsToValues(facesContext, component,
                testValue, (String[]) tokens.toArray(new String[tokens.size()]));

        return Arrays.asList(vs);
    }

    public static boolean updateValues(FacesContext facesContext,
            UIComponent component, boolean convert, Collection values,
            String valuesToAdd, String valuesToRemove) {
        List vadd = Collections.EMPTY_LIST;
        if (valuesToAdd != null) {
            vadd = parseValues(facesContext, component, convert, true,
                    valuesToAdd);
        }

        boolean modified = false;

        if (valuesToRemove != null) {
            if (HtmlTools.ALL_VALUE.equals(valuesToRemove)) {
                modified = (values.size() > 0);
                values.clear();

            } else {
                List radd = parseValues(facesContext, component, convert, true,
                        valuesToRemove);
                if (values.removeAll(radd)) {
                    modified = true;
                }
            }
        }

        if (values.addAll(vadd)) {
            modified = true;
        }

        return modified;
    }

    public static Object[] convertValuesToSet(FacesContext facesContext,
            UIComponent component, Object values) {
        List l = valueToList(values);

        if (l == null || l.isEmpty()) {
            return OBJECT_EMPTY_ARRAY;
        }

        Set set = new HashSet(l.size());

        set.addAll(Arrays.asList(convertValuesToString(l.toArray(), component,
                facesContext)));

        return set.toArray();
    }
}
