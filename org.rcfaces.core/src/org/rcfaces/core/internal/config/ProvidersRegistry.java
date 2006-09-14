/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    private final Map providersRepository = new HashMap(64);

    private Digester digester;

    public ProvidersRegistry() {
    }

    public IProvider getProvider(String providerId) {
        return (IProvider) providersRepository.get(providerId);
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
            LOG.debug(
                    "Can not get constructor with provider parameter for class '"
                            + className + "' specified by provider id='" + id
                            + "'  (providerId='" + providerId + "').", ex);

            try {
                constructor = clazz.getConstructor(null);
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
            parameters[0] = providersRepository.get(providerId);
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

        providersRepository.put(providerId, provider);
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

    public void completeConfiguration(IProvidersConfigurator configurator) {
        Digester digester = new Digester();

        for (Iterator it = providersRepository.entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            IProvider provider = (IProvider) entry.getValue();

            provider.configureRules(digester);
        }

        if (digester.getRules().rules().isEmpty()) {
            return;
        }

        configurator.parseConfiguration(digester);
    }
}
