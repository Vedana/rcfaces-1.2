/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUpdatableDataModel {
    void addRow(int position, Object row);

    void removeRow(int position);
}
