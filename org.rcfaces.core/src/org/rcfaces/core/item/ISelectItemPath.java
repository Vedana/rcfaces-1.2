/*
 * $Id$
 */
package org.rcfaces.core.item;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemPath {
    SelectItem[] segments();

    SelectItem segment(int index);

    int segmentCount();

    SelectItem getSelectItem();

    String normalizePath(FacesContext facesContext, UIComponent component, Converter valueConverter);

    String normalizePath();
}
