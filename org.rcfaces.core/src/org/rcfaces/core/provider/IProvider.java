/*
 * $Id$
 * 
 * $Log$
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

import org.apache.commons.digester.Digester;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IProvider {

    void configureRules(Digester digester);

}
