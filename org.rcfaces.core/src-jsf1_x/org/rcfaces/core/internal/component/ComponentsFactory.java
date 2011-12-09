/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.capability.IComponentEngine;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentsFactory implements IFactory {
    private static final String REVISION = "$Revision$";

    private static final ComponentsFactory SINGLETON = new ComponentsFactory();

    private ComponentsFactory() {
    }

    public String getName() {
        return "Camelia basic components factory";
    }

    public static final ComponentsFactory getCameliaFactory(
            FacesContext facesContext) {
        return SINGLETON;
    }

    public List createList(int size) {
        return new ArrayList(size);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.util.IContainerStrategies#createMap(int)
     */
    public Map createMap(int size) {
        return new HashMap(size);
    }

    public Object allocateObject(Class claz) {
        try {
            return claz.newInstance();

        } catch (Throwable th) {
            throw new FacesException("Can not allocate object from class '"
                    + claz + "'.", th);
        }
    }

    public IComponentEngine createComponentEngine() {
        return new BasicComponentEngine(this);
    }

    public IPropertiesManager createPropertiesManager(IComponentEngine engine) {

        BasicPropertiesManager pa = (BasicPropertiesManager) allocateObject(BasicPropertiesManager.class);

        pa.setCameliaFactory(this);

        return pa;
    }

    public IInitializationState createInitializationState() {
        return new BasicInitializationState();
    }

}