/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.ns;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NamespaceConfigurationImpl implements INamespaceConfiguration {

    private static final Log LOG = LogFactory
            .getLog(NamespaceConfigurationImpl.class);

    public static final String DEFAULT_NAMESPACE = "*";

    public static final String DEFAULT_COMPONENT = "*";

    private final Map namespacesByURI = new HashMap();

    public void addComponent(String componentName) {
        addComponentNS(DEFAULT_NAMESPACE, componentName);
    }

    public void addAttribute(String componentName, String propertyName) {
        addAttributeNS(DEFAULT_NAMESPACE, componentName, propertyName);
    }

    public void addAttributes(String componentName, String[] propertiesName) {
        addAttributeNS(DEFAULT_NAMESPACE, componentName, propertiesName);
    }

    public void addComponentNS(String namespaceURI, String componentName) {
        getNameSpace(namespaceURI).getComponent(componentName);
    }

    public void addAttributeNS(String namespaceURI, String componentName,
            String propertyName) {
        if (componentName == null) {
            componentName = DEFAULT_COMPONENT;
        }

        getNameSpace(namespaceURI).getComponent(componentName).addAttribute(
                propertyName);
    }

    public void addAttributeNS(String namespaceURI, String componentName,
            String[] propertiesName) {
        if (componentName == null) {
            componentName = DEFAULT_COMPONENT;
        }

        getNameSpace(namespaceURI).getComponent(componentName).addAttributes(
                propertiesName);
    }

    protected NameSpace getNameSpace(String namespaceURI) {
        NameSpace nameSpace = (NameSpace) namespacesByURI.get(namespaceURI);
        if (nameSpace != null) {
            return nameSpace;
        }

        nameSpace = new NameSpace(namespaceURI);
        namespacesByURI.put(nameSpace.getURI(), nameSpace);

        return nameSpace;
    }

    public NameSpace[] listNamespaces() {
        Collection cl = namespacesByURI.values();

        return (NameSpace[]) cl.toArray(new NameSpace[cl.size()]);
    }

    public static class NameSpace {

        private final String uri;

        private final Map componentsByName = new HashMap();

        public NameSpace(String namespaceURI) {
            this.uri = namespaceURI;
        }

        public Component getComponent(String componentName) {
            Component component = (Component) componentsByName
                    .get(componentName);
            if (component != null) {
                return component;
            }

            component = new Component(componentName);
            componentsByName.put(component.getName(), component);

            return component;
        }

        public String getURI() {
            return uri;
        }

        public Component[] listComponents() {
            Collection cl = componentsByName.values();

            return (Component[]) cl.toArray(new Component[cl.size()]);
        }

    }

    public static class Component {

        private final String name;

        private final Set attributesName = new HashSet();

        public Component(String componentName) {
            this.name = componentName;
        }

        public String getName() {
            return name;
        }

        public void addAttribute(String propertyName) {
            attributesName.add(propertyName);
        }

        public void addAttributes(String[] propertiesName) {
            this.attributesName.addAll(Arrays.asList(propertiesName));
        }

        public String[] listAttributes() {
            return (String[]) attributesName.toArray(new String[attributesName
                    .size()]);
        }

    }
}
