/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import org.apache.commons.digester.Digester;
import org.rcfaces.core.provider.IProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProvidersRegistry {

    IProvider getProvider(String id);

    Digester getConfigDigester();
}
