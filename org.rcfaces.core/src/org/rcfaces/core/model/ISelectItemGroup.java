/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemGroup extends ISelectItem {

    SelectItem[] getSelectItems();
}
