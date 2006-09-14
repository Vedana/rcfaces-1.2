/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 * Revision 1.2  2003/11/12 15:15:02  oeuillot
 * Retire les methodes "public"
 *
 * Revision 1.1  2003/05/13 05:48:05  oeuillot
 * Ajout des DataSources
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