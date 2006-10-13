/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServerDataSelectItem {
    Map getServerDataMap();

    boolean isServerDataEmpty();
}
