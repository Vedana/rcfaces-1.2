/*
 * $Id$
 */
package org.rcfaces.core.component.iterator;

import java.util.Map;

import javax.faces.component.UIComponent;

import org.rcfaces.core.item.ISelectItemPath;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemFinder {
    ISelectItemPath[] search(UIComponent component,
            Map<String, Object> filterProperies);
}
