/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPageConfigurator extends ILocalizedAttributesCapability {
    String getPageScriptType();
}
