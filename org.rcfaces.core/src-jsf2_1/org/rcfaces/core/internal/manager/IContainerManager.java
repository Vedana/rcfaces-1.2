/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import java.util.List;

import javax.faces.component.UIComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContainerManager {

    int getChildCount();

    List<UIComponent> getChildren();
}
