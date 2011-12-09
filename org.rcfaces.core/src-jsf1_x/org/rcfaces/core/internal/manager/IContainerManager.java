/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import java.util.List;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContainerManager {
    int getChildrenListState();

    int getChildCount();

    List getChildren();
}
