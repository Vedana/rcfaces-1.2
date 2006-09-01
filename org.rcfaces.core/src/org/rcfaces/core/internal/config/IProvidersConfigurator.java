/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.config;

import org.apache.commons.digester.Digester;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IProvidersConfigurator {
    void parseConfiguration(Digester digester);
}
