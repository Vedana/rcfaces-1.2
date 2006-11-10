/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import javax.faces.component.UISelectItem;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemIterator extends IComponentIterator {

    UISelectItem next();

    UISelectItem [] toArray();
}
