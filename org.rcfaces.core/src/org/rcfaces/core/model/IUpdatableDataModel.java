/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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
