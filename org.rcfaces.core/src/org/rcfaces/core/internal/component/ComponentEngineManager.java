/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IRCFacesComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ComponentEngineManager {

    private static final Map<Class< ? >, Field> fields = new HashMap<Class< ? >, Field>(
            256);

    private static final Map<Class< ? >, Method> getters = new HashMap<Class< ? >, Method>(
            256);

    private static final Map<Class< ? >, Method> setters = new HashMap<Class< ? >, Method>(
            256);

    private static final Class< ? >[] SETTER_PARAMETERS = new Class[] { IComponentEngine.class };

    private static final String COMPONENT_ENGINE_GETTER_METHOD_NAME = "getComponentEngine";

    private static final String COMPONENT_ENGINE_SETTER_METHOD_NAME = "setComponentEngine";

    private static final String COMPONENT_ENGINE_FIELD_NAME = "engine";

    @SuppressWarnings("unused")
    public static void setComponentEngine(IRCFacesComponent component,
            IComponentEngine componentEngine) {

        Field field = getField(component.getClass());

        try {
            field.set(component, componentEngine);

        } catch (Throwable e) {
            throw new RuntimeException("Can not set field '" + field
                    + "' for value '" + componentEngine + "'.");
        }

        if (false) {
            Method method = getSetter(component.getClass());

            try {
                method.invoke(component, new Object[] { componentEngine });

            } catch (Throwable e) {
                throw new RuntimeException("Can not call setter '" + method
                        + "' for value '" + componentEngine + "'.");
            }
        }
    }

    @SuppressWarnings("unused")
    public static IComponentEngine getComponentEngine(
            IRCFacesComponent component) {

        if (false) {
            Method method = getGetter(component.getClass());

            try {
                return (IComponentEngine) method.invoke(component,
                        (Object[]) null);

            } catch (Throwable e) {
                throw new RuntimeException("Can not call getter '" + method
                        + "'.");
            }
        }

        Field field = getField(component.getClass());

        try {
            return (IComponentEngine) field.get(component);

        } catch (Throwable e) {
            throw new RuntimeException("Can not get value '" + field + "'.");
        }
    }

    private static Method getGetter(Class< ? > componentClass) {

        synchronized (getters) {
            Method method = getters.get(componentClass);
            if (method != null) {
                return method;
            }

            Throwable th = null;
            for (Class< ? > cls = componentClass; cls != null; cls = cls
                    .getSuperclass()) {
                try {
                    method = cls
                            .getDeclaredMethod(
                                    COMPONENT_ENGINE_GETTER_METHOD_NAME,
                                    (Class[]) null);

                    break;

                } catch (Throwable th2) {
                    if (th == null) {
                        th = th2;
                    }
                }
            }

            if (method == null) {
                throw new RuntimeException("Can not get method '"
                        + COMPONENT_ENGINE_GETTER_METHOD_NAME + "' of class '"
                        + componentClass + "'.", th);
            }

            getters.put(componentClass, method);

            return method;
        }
    }

    private static Method getSetter(Class< ? > componentClass) {

        synchronized (setters) {
            Method method = setters.get(componentClass);
            if (method != null) {
                return method;
            }

            Throwable th = null;
            for (Class< ? > cls = componentClass; cls != null; cls = cls
                    .getSuperclass()) {
                try {
                    method = cls.getDeclaredMethod(
                            COMPONENT_ENGINE_SETTER_METHOD_NAME,
                            SETTER_PARAMETERS);

                    break;

                } catch (Throwable th2) {
                    if (th == null) {
                        th = th2;
                    }
                }
            }

            if (method == null) {
                throw new RuntimeException("Can not get method '"
                        + COMPONENT_ENGINE_SETTER_METHOD_NAME + "' of class '"
                        + componentClass + "'.", th);
            }

            setters.put(componentClass, method);

            return method;
        }
    }

    private static Field getField(Class< ? > componentClass) {

        synchronized (fields) {
            Field field = fields.get(componentClass);
            if (field != null) {
                return field;
            }

            Throwable th = null;
            for (Class< ? > cls = componentClass; cls != null; cls = cls
                    .getSuperclass()) {
                try {
                    field = cls.getDeclaredField("engine");

                    break;

                } catch (Throwable th2) {
                    if (th == null) {
                        th = th2;
                    }
                }
            }

            if (field == null) {
                throw new RuntimeException("Can not get field '"
                        + COMPONENT_ENGINE_GETTER_METHOD_NAME + "' of class '"
                        + componentClass + "'.", th);
            }

            fields.put(componentClass, field);

            return field;
        }
    }

    public static void cloneComponentEngine(FacesContext facesContext,
            IRCFacesComponent component) {
        IComponentEngine componentEngine = getComponentEngine(component);

        IComponentEngine newComponentEngine = componentEngine
                .copyOriginalState(facesContext);

        setComponentEngine(component, newComponentEngine);
    }
}
