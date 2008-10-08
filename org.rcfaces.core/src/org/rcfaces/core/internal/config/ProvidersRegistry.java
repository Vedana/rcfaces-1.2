/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.provider.IProvider;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ProvidersRegistry implements IProvidersRegistry {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ProvidersRegistry.class);

    private static final Class[] PARENT_PROVIDER_PARAMETER_TYPES = new Class[] { IProvider.class };

    private final Map providersById = new TreeMap();

    private Digester digester;

    public ProvidersRegistry() {
    }

    public IProvider getProvider(String providerId) {
        return (IProvider) providersById.get(providerId);
    }

    public void addProvider(ProviderBean providerBean) {

        String id = providerBean.getId();

        String providerId = providerBean.getProviderId();
        if (providerId == null) {
            throw new FacesException("You must specify a provider identifier.");
        }

        String className = providerBean.getProviderClassName();
        if (className == null) {
            throw new FacesException(
                    "You must specify the className of the provider. (id='"
                            + id + "' providerId='" + providerId + "')");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialize provider '" + id + "'. (providerId='"
                    + providerId + "', classname='" + className + "'");
        }

        Class clazz;
        try {
            clazz = ClassLocator.load(className, null, FacesContext
                    .getCurrentInstance());

        } catch (ClassNotFoundException ex) {
            throw new FacesException("Can not load class '" + className
                    + "' specified by provider id='" + id + "' (providerId='"
                    + providerId + "').", ex);
        }

        if (IProvider.class.isAssignableFrom(clazz) == false) {
            throw new FacesException("Class '" + className
                    + "' specified by provider id='" + id + "'  (providerId='"
                    + providerId + "') must implement interface 'IProvider'.");
        }

        if ((clazz.getModifiers() & Modifier.ABSTRACT) > 0) {
            throw new FacesException("Class '" + className
                    + "' specified by provider id='" + id + "'  (providerId='"
                    + providerId + "') is abstract !");
        }

        Constructor constructor;
        Object parameters[];

        try {
            constructor = clazz.getConstructor(PARENT_PROVIDER_PARAMETER_TYPES);
            parameters = new Object[] { null };

        } catch (NoSuchMethodException ex) {
            LOG.trace(
                    "Can not get constructor with provider parameter for class '"
                            + className + "' specified by provider id='" + id
                            + "'  (providerId='" + providerId
                            + "'), TRY with no parameter !", ex);

            try {
                constructor = clazz.getConstructor((Class[]) null);
                parameters = null;

            } catch (NoSuchMethodException ex2) {
                throw new FacesException(
                        "Can not find accessible (public) constructor of class '"
                                + className + "' specified by provider id='"
                                + id + "'  (providerId='" + providerId + "').",
                        ex2);
            }
        }

        LOG.debug("Use constructor '" + constructor
                + "' to instanciate provider '" + id + "'.  (providerId='"
                + providerId + "', classname='" + className + "'");

        if (parameters != null) {
            parameters[0] = providersById.get(providerId);
        }

        IProvider provider;
        try {
            provider = (IProvider) constructor.newInstance(parameters);

        } catch (Throwable ex) {
            throw new FacesException("Can not instanciate class '" + className
                    + "' specified by provider id='" + id + "' (providerId='"
                    + providerId + "') using constructor '" + constructor
                    + "'.", ex);
        }

        LOG.trace("addProvider(" + providerId + "," + provider + ")");

        providersById.put(providerId, provider);
    }

    public void configureRules(Digester digester) {

        digester.addRule("rcfaces-config/providers", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                super.digester.push(ProvidersRegistry.this);
            }

            public void end(String namespace, String name) throws Exception {
                super.digester.pop();
            }
        });

        digester.addObjectCreate("rcfaces-config/providers/provider",
                ProviderBean.class);
        digester.addSetProperties("rcfaces-config/providers/provider", "id",
                "id");
        digester.addBeanPropertySetter(
                "rcfaces-config/providers/provider/provider-id", "providerId");
        digester.addBeanPropertySetter(
                "rcfaces-config/providers/provider/provider-class",
                "providerClassName");
        digester.addSetNext("rcfaces-config/providers/provider", "addProvider");
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ProviderBean {
        private static final String REVISION = "$Revision$";

        private String id;

        private String providerClassName;

        private String providerId;

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public final String getProviderClassName() {
            return providerClassName;
        }

        public final void setProviderClassName(String className) {
            this.providerClassName = className;
        }

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }
    }

    public Digester getConfigDigester() {
        return digester;
    }

    public void loadProvidersConfiguration(IProvidersConfigurator configurator) {
        Digester digester = new Digester();

        for (Iterator it = providersById.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            IProvider provider = (IProvider) entry.getValue();

            provider.configureRules(digester);
        }

        if (digester.getRules().rules().isEmpty()) {
            return;
        }

        configurator.parseConfiguration(digester);
    }

    public void startupProviders(FacesContext facesContext) {
        for (Iterator it = providersById.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String providerId = (String) entry.getKey();
            IProvider provider = (IProvider) entry.getValue();

            try {
                LOG.debug("Start provider '" + providerId + "' ...");

                provider.startup(facesContext);

                LOG.debug("Start provider '" + providerId + "' done");

            } catch (RuntimeException ex) {
                it.remove();

                LOG.error("Exception when starting up provider '" + providerId
                        + "'", ex);

                throw ex;
            }
        }

    }
}
