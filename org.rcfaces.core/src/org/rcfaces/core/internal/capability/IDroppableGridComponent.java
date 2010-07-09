/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDroppableGridComponent {
    boolean isBodyDroppable();

    String[] getRowDropTypes();

    boolean isRowDropTypesSetted();

    int getRowDropEffects();

    boolean isRowDropEffectsSetted();
}
