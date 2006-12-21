/*
 * $Id$
 */
package org.rcfaces.core.internal.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.model.IAdapterFactory;
import org.rcfaces.core.provider.AbstractProvider;
import org.xml.sax.Attributes;

/**
 * 
 * @author Eclipse project (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdapterManagerImpl extends AbstractProvider implements
        IAdapterManager {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AdapterManagerImpl.class);

    protected final Map factories;

    protected Map adapterLookup;

    protected Map classSearchOrderLookup;

    public AdapterManagerImpl() {
        factories = new HashMap(5);
        adapterLookup = null;

        RcfacesContext rcfacesContext = RcfacesContext.getCurrentInstance();

        if (rcfacesContext.getAdapterManager() == null) {
            rcfacesContext.setAdapterManager(this);
        }
    }

    public String getId() {
        return "AdapterManager";
    }

    public void configureRules(Digester digester) {
        super.configureRules(digester);

        digester.addRule("rcfaces-config/adapters/adapter", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                super.digester.push(new AdapterBean());
            }

            public void end(String namespace, String name) throws Exception {
                AdapterBean adapterBean = (AdapterBean) super.digester.pop();

                declareAdapter(adapterBean);
            }
        });

        digester.addBeanPropertySetter(
                "rcfaces-config/adapters/adapter/adaptable-class", "className");

        digester.addBeanPropertySetter(
                "rcfaces-config/adapters/adapter/adapterFactory-class",
                "adapterFactoryClassName");

    }

    protected void declareAdapter(AdapterBean adapterBean) {
        String adapterClassName = adapterBean.getClassName();
        String factoryClassName = adapterBean.getAdapterFactoryClassName();

        LOG.debug("Declare adapter adapterClassName='" + adapterClassName
                + "' factoryClassName='" + factoryClassName + "'.");

        if (adapterClassName == null || adapterClassName.length() < 1
                || factoryClassName == null || factoryClassName.length() < 1) {
            throw new FacesException(
                    "Invalid adapter configuration. (adapter-class='"
                            + adapterClassName + "' adapterFactory-class='"
                            + factoryClassName + "')");
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();

        /*
         * int dimension = 0; for (;;) { int dim =
         * adapterClassName.lastIndexOf('['); if (dim < 0) { break; }
         * dimension++; adapterClassName = adapterClassName.substring(0, dim); }
         */

        Class adapterClass;
        try {
            adapterClass = ClassLocator.load(adapterClassName, this,
                    facesContext);

        } catch (ClassNotFoundException e) {
            LOG.info("Adapter class not found '" + adapterClassName
                    + "', ignore adapter !", e);
            return;
        }

        Class factoryClass;
        try {
            factoryClass = ClassLocator.load(factoryClassName, this,
                    facesContext);

        } catch (ClassNotFoundException e) {
            LOG.info("Factory class '" + factoryClassName
                    + "' for adapterClass='" + adapterClassName
                    + "' is not found, ignore adapter !", e);
            return;
        }

        IAdapterFactory adapterFactory;
        try {
            adapterFactory = (IAdapterFactory) factoryClass.newInstance();

        } catch (Throwable th) {
            LOG.info("Can not instanciate factory class '" + factoryClassName
                    + "' for adapterClass='" + adapterClassName
                    + "', ignore adapter !", th);
            return;
        }

        registerAdapters(adapterFactory, adapterClass);
    }

    private Map getFactories(Class adaptable) {
        // cache reference to lookup to protect against concurrent flush
        Map lookup = adapterLookup;
        if (lookup == null) {
            adapterLookup = lookup = new HashMap(30);
        }

        Map table = (Map) lookup.get(adaptable.getName());
        if (table == null) {
            // calculate adapters for the class
            table = new HashMap(4);
            Class[] classes = computeClassOrder(adaptable);
            for (int i = 0; i < classes.length; i++) {
                addFactoriesFor(classes[i].getName(), table);
            }

            // cache the table
            lookup.put(adaptable.getName(), table);
        }
        return table;
    }

    public Object getAdapter(Object adaptable, Class adapterType, Map parameters) {
        IAdapterFactory factory = (IAdapterFactory) getFactories(
                adaptable.getClass()).get(adapterType.getName());
        Object result = null;
        if (factory != null) {
            result = factory.getAdapter(adaptable, adapterType, parameters);
        }

        if (result == null && adapterType.isInstance(adaptable)) {
            return adaptable;
        }

        return result;
    }

    public Class[] computeClassOrder(Class adaptable) {
        List classes = null;
        // cache reference to lookup to protect against concurrent flush
        Map lookup = classSearchOrderLookup;
        if (lookup != null) {
            classes = (List) lookup.get(adaptable);
        }

        // compute class order only if it hasn't been cached before
        if (classes == null) {
            classes = new ArrayList();
            computeClassOrder(adaptable, classes);
            if (lookup == null) {
                classSearchOrderLookup = lookup = new HashMap();
            }
            lookup.put(adaptable, classes);
        }

        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

    private void computeClassOrder(Class adaptable, Collection classes) {
        Class clazz = adaptable;
        Set seen = new HashSet(4);
        while (clazz != null) {
            classes.add(clazz);
            computeInterfaceOrder(clazz.getInterfaces(), classes, seen);
            clazz = clazz.getSuperclass();
        }
    }

    private void computeInterfaceOrder(Class[] interfaces, Collection classes,
            Set seen) {
        List newInterfaces = new ArrayList(interfaces.length);
        for (int i = 0; i < interfaces.length; i++) {
            Class interfac = interfaces[i];
            if (seen.add(interfac)) {
                // note we cannot recurse here without changing the resulting
                // interface order
                classes.add(interfac);
                newInterfaces.add(interfac);
            }
        }

        for (Iterator it = newInterfaces.iterator(); it.hasNext();) {
            computeInterfaceOrder(((Class) it.next()).getInterfaces(), classes,
                    seen);
        }
    }

    private void addFactoriesFor(String typeName, Map table) {
        List factoryList = (List) getFactories().get(typeName);
        if (factoryList == null) {
            return;
        }

        for (int i = 0, imax = factoryList.size(); i < imax; i++) {
            IAdapterFactory factory = (IAdapterFactory) factoryList.get(i);

            Class[] adapters = factory.getAdapterList();
            for (int j = 0; j < adapters.length; j++) {
                String adapterName = adapters[j].getName();
                if (table.get(adapterName) == null)
                    table.put(adapterName, factory);
            }
        }
    }

    public Map getFactories() {
        return factories;
    }

    public synchronized void registerAdapters(IAdapterFactory factory,
            Class adaptable) {
        registerFactory(factory, adaptable.getName());
        flushLookup();
    }

    public synchronized void flushLookup() {
        adapterLookup = null;
        classSearchOrderLookup = null;
    }

    public void registerFactory(IAdapterFactory factory, String adaptableType) {
        List list = (List) factories.get(adaptableType);
        if (list == null) {
            list = new ArrayList(5);
            factories.put(adaptableType, list);
        }
        list.add(factory);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class AdapterBean {
        private String className;

        private String adapterFactoryClassName;

        public final String getAdapterFactoryClassName() {
            return adapterFactoryClassName;
        }

        public final String getClassName() {
            return className;
        }

        public final void setAdapterFactoryClassName(
                String adapterFactoryClassName) {
            this.adapterFactoryClassName = adapterFactoryClassName;
        }

        public final void setClassName(String className) {
            this.className = className;
        }

    }

}
