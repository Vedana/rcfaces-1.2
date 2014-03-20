/*
 * $Id: IContainerManager.java,v 1.5 2007/05/24 12:26:17 oeuillot Exp $
 * 
 */
package org.rcfaces.core.internal.manager;

import java.util.List;

/**
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.5 $ $Date: 2007/05/24 12:26:17 $
 */
public interface IContainerManager {
    int getChildrenListState();

    int getChildCount();

    List getChildren();
}
