/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.component;

import java.util.List;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IStateChildrenList extends List {

    void setChildren(List list);

    int getState();
}