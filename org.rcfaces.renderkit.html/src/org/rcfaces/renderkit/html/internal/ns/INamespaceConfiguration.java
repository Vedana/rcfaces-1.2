/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.ns;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface INamespaceConfiguration {
    void addComponent(String componentName);

    void addAttribute(String componentName, String propertyName);

    void addAttributes(String componentName, String[] propertiesName);

    void addComponentNS(String namespaceURI, String componentName);

    void addAttributeNS(String namespaceURI, String componentName,
            String propertyName);

    void addAttributeNS(String namespaceURI, String componentName,
            String[] propertiesName);
}
