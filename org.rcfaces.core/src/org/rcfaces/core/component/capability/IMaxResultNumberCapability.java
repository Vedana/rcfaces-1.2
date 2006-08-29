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
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IMaxResultNumberCapability {
    void setMaxResultNumber(int maximum);

    int getMaxResultNumber();
}
