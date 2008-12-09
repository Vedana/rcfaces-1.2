/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResourceKeyParticipant {
    String RESOURCE_KEY_SEPARATOR = "\u0001";

    void participeKey(StringAppender sa);
}
