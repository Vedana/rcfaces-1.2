/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.capability;

import java.util.List;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStateChildrenList extends List {

    void setChildren(List list);

    int getState();
}
