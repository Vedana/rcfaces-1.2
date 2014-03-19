/*
 * $Id$
 */
package org.rcfaces.core.item;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDataItem {
    Map<String, String> getClientDataMap();

    boolean isClientDataEmpty();
}
