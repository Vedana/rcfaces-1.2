/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DragDropTypesConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new DragDropTypesConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(value, ",");

        List list = new ArrayList();

        for (; st.hasMoreTokens();) {
            String token = st.nextToken().trim();

            list.add(token);
        }

        return (String[]) list.toArray(new String[list.size()]);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        throw new FacesException("Not implemented !");
    }
}
