/*
 * $Id$
 */
package org.rcfaces.core.internal.repository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRepositoryManager {

    String[] listFamilies();

    String[] listRepositoryLocations(String family);

}
