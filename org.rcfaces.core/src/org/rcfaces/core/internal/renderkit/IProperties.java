/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IProperties {

    boolean containsKey(String name);

    Object getProperty(String name);

    String getStringProperty(String name);

    String getStringProperty(String name, String defaultValue);

    boolean getBoolProperty(String name, boolean defaultValue);

    Boolean getBooleanProperty(String name);

    int getIntProperty(String name, int defaultValue);

    Number getNumberProperty(String name);

}
