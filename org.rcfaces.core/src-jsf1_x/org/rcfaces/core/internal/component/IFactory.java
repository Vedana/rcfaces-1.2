/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import java.util.List;
import java.util.Map;

import org.rcfaces.core.internal.capability.IComponentEngine;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFactory {

    String getName();

    List createList(int size);

    Map createMap(int size);

    IComponentEngine createComponentEngine();

    IPropertiesManager createPropertiesManager(IComponentEngine engine);

    IInitializationState createInitializationState();

    /*
     * IPropertiesAccessor createPropertiesAccessor(IComponentEngine engine);
     * 
     * 
     * IPropertiesAccessor restorePropertiesAccessor(FacesContext facesContext,
     * IComponentEngine engine, Object state);
     */
}
