/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui implemente cette interface accepte les references d'une
 * DataSource pour remplir ses donn�es.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataSourceCapability {

	/**
	 * Sp�cifie la DataSource de l'�l�ment.
	 */
	void setDataSource(String dataSource);

	/**
	 * Retourne la DataSource de l'�l�ment.
	 */
	String getDataSource();
}
