/*
 * $Id$
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
