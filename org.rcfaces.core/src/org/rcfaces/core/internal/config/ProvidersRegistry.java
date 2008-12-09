/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        ProviderBean providerBean = (ProviderBean) providersById
                .get(providerId);
        if (providerBean == null) {
            return null;
        }

        return providerBean.getProvider();
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

        /*
         * Ca n'a pas de sens ! if (parameters != null) { ProviderBean pb =
         * (ProviderBean) providersById.get(providerId); if (bp != null) {
         * parameters[0] = pb.getProvider(); } }
         */

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

        providerBean.setProvider(provider);

        providersById.put(providerId, providerBean);
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

        digester.addRule("rcfaces-config/providers/provider/requires",
                new Rule() {

                    public void body(String namespace, String name, String text)
                            throws Exception {

                        ProviderBean providerBean = (ProviderBean) super.digester
                                .peek();

                        providerBean.addRequired(text);
                    }

                });

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

        private List requirements;

        private IProvider provider;

        public String getProviderId() {
            return providerId;
        }

        public void addRequired(String required) {
            if (requirements == null) {
                requirements = new ArrayList();
            }

            requirements.add(required);
        }

        public String[] listRequirements() {
            if (requirements == null) {
                return new String[0]; // Quel est le mieux ?
            }
            return (String[]) requirements.toArray(new String[requirements
                    .size()]);
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

        public IProvider getProvider() {
            return provider;
        }

        public void setProvider(IProvider provider) {
            this.provider = provider;
        }

    }

    public Digester getConfigDigester() {
        return digester;
    }

    public void loadProvidersConfiguration(IProvidersConfigurator configurator) {
        Digester digester = new Digester();

        for (Iterator it = providersById.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            ProviderBean providerBean = (ProviderBean) entry.getValue();
            IProvider provider = providerBean.getProvider();

            provider.configureRules(digester);
        }

        if (digester.getRules().rules().isEmpty()) {
            return;
        }

        configurator.parseConfiguration(digester);
    }

    public void startupProviders(FacesContext facesContext) {

        Set started = new HashSet();

        boolean startedProcess = true;

        for (; startedProcess;) {
            startedProcess = false;

            next_provider: for (Iterator it = providersById.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String providerId = (String) entry.getKey();
                if (started.contains(providerId)) {
                    continue;
                }

                ProviderBean providerBean = (ProviderBean) entry.getValue();

                String requirements[] = providerBean.listRequirements();
                for (int i = 0; i < requirements.length; i++) {
                    String requirement = requirements[i];

                    if (providersById.containsKey(requirement) == false) {
                        LOG.error("Invalid dependency provider='" + providerId
                                + "' requires unknown provider '" + requirement
                                + "'.");
                    } else if (started.contains(requirement) == false) {
                        continue next_provider;
                    }
                }

                startedProcess = true;
                started.add(providerId);

                IProvider provider = providerBean.getProvider();

                try {
                    LOG.debug("Start provider '" + providerId + "' ...");

                    provider.startup(facesContext);

                    LOG.debug("Start provider '" + providerId + "' done");

                } catch (RuntimeException ex) {
                    it.remove();

                    LOG.error("Exception when starting up provider '"
                            + providerId + "'", ex);

                    throw ex;
                }
            }
        }

        if (started.size() != providersById.size()) {
            List l = new ArrayList(providersById.keySet());
            l.removeAll(started);

            LOG.error("Providers are failed to startup: " + l);
        }
    }
}
