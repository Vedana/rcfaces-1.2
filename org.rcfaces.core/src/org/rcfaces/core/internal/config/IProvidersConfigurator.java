/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.config;

import org.apache.commons.digester.Digester;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProvidersConfigurator {
    void parseConfiguration(Digester digester);
}
