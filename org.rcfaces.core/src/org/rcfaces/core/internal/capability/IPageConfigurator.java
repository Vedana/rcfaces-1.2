/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPageConfigurator extends ILiteralLocaleCapability,
        ILiteralTimeZoneCapability {
    String getPageScriptType();
}
