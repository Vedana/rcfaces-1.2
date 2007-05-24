/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A component that implements this interface would accept a dataSource
 * reference as a supply of data.
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataSourceCapability {

    /**
     * Sets a dataSource for the component.
     * 
     * @param dataSource
     *            reference to a dataSource
     */
    void setDataSource(String dataSource);

    /**
     * Returns the component's dataSource.
     * 
     * @return a reference to the dataSource
     */
    String getDataSource();
}
