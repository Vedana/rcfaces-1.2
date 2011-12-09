/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import org.rcfaces.core.internal.capability.IComponentEngine;

interface IComponentEngineAccessor {
    /**
     * INTERNAL USE ONLY
     */
    IComponentEngine getComponentEngine();

    /**
     * INTERNAL USE ONLY
     */
    void cloneComponentEngine();

}
