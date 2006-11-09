/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * 
 * @author Eclipse team (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdaptable {
    Object getAdapter(Class adapter, Map parameters);
}
