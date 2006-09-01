/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import org.rcfaces.core.internal.webapp.IRepository;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IJavaScriptRepository extends IRepository {

    int CLASS_NAME_COLLECTION_TYPE = 10;

    IClass getClassByName(String className);

    public interface IClassFile extends IFile {
        IClass[] listClasses();
    };

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IClass {
        String getName();

        IFile getFile();

        IClass[] listRequiredClasses(String requireId);

        IFile[] listRequiredResources(String requiredId);

    }
}
