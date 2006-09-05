/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalitï¿½ de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.2  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.1  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.core.internal.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;

import org.rcfaces.core.internal.renderkit.IProcessContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IRepository extends Serializable {

    int FILE_COLLECTION_TYPE = 0;

    int FILENAME_COLLECTION_TYPE = 1;

    String getVersion();

    IContext createContext(Locale locale);

    IFile[] computeFiles(Collection collection, int typeOfCollection,
            IContext context);

    IFile getFileByURI(String uri);

    IFile getFileByName(String filename);

    IModule[] listModules();

    IModule getModuleByName(String name);

    ISet getBootSet();

    ISet getSetByName(String bootSet);

    void setBootSet(ISet set);

    ISet declareSet(String name, String uri, String[] moduleNames);

    String getBaseURI(IProcessContext context);

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface ISet extends IFile {
        IFile[] listExternalDependencies();
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IModule extends IFile {
        boolean getGroupAllFiles();

        void setGroupAllFiles(boolean enable);

        IFile[] listExternalDependencies();

        IModule[] listExternalModules();

        ISet getSet();
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IFile {
        IRepository getRepository();

        IContentProvider getContentProvider();

        String getFilename();

        URL[] getContentLocations(Locale locale);

        String getURI(Locale locale);

        IFile[] listDependencies();

        IModule getModule();
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IContext {
        Locale getLocale();

        boolean contains(IFile file);

        boolean add(IFile file);

        IContext copy();

        Object saveState();

        void restoreState(IRepository repository, Object state);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    public interface IContentProvider {
        IContent getContent(URL url, Locale locale);

        boolean testURL(URL url);
    }

    public interface IContent {
        InputStream getInputStream() throws IOException;

        long getLastModified() throws IOException;

        long getLength() throws IOException;

        void release();
    }
}
