/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.lang.OrderedSet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlValuesTools extends ValuesTools {
    private static final Object[] OBJECT_EMPTY_ARRAY = new Object[0];

    public static List<Object> parseValues(FacesContext facesContext,
            UIComponent component, boolean convert, boolean testValue,
            String values) {
        StringTokenizer st = new StringTokenizer(values,
                HtmlTools.LIST_SEPARATORS);
        if (st.hasMoreTokens() == false) {
            return Collections.emptyList();
        }

        List<Object> tokens = new ArrayList<Object>(st.countTokens());
        for (; st.hasMoreTokens();) {
            tokens.add(st.nextToken());
        }

        if (convert == false) {
            return tokens;
        }

        Object vs[] = convertStringsToValues(facesContext, component,
                testValue, tokens.toArray(new String[tokens.size()]));

        return Arrays.asList(vs);
    }

    public static boolean updateValues(FacesContext facesContext,
            UIComponent component, boolean convert, Set<Object> values,
            String valuesToAdd, String valuesToRemove) {
        List<Object> vadd = Collections.emptyList();
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

        Set<Object> set = new OrderedSet<Object>(l.size());

        set.addAll(Arrays.asList(convertValuesToString(l.toArray(), component,
                facesContext)));

        return set.toArray();
    }
}
