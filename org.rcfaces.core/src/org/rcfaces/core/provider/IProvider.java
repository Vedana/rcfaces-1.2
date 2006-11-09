/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/11/09 19:09:09  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.provider;

import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProvider {

    void configureRules(Digester digester);

    void startup(FacesContext facesContext);
}
