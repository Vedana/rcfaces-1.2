/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import java.util.List;
import java.util.Map;

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

    /*
    IPropertiesAccessor createPropertiesAccessor(IComponentEngine engine);

    
    IPropertiesAccessor restorePropertiesAccessor(FacesContext facesContext,
            IComponentEngine engine, Object state);
            */
}
