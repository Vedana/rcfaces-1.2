/*
 * $Id$
 */
package org.rcfaces.core.item;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItem {
    String getLabel();

    String getDescription();

    Object getValue();

    boolean isDisabled();
}
