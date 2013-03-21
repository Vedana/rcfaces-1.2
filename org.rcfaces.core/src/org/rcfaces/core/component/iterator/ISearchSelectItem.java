/*
 * $Id$
 */
package org.rcfaces.core.component.iterator;

import java.util.EnumSet;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IOutlinedLabelCapability;
import org.rcfaces.core.item.ISelectItemPath;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISearchSelectItem {
    ISelectItemPath[] search(UIComponent component,
            Map<String, Object> criterias,
            EnumSet<IOutlinedLabelCapability.Method> methods);
}
