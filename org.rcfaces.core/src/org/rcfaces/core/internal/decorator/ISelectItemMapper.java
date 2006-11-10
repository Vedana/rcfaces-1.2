/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemMapper {

    boolean map(SelectItem si);

    void unknownComponent(UIComponent component);

    boolean acceptCollections();
}
