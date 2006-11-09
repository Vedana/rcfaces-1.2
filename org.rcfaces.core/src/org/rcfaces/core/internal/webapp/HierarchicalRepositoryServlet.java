/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/11/09 19:09:09  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.13  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.12  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.11  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.10  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.9  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.8  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.7  2005/11/08 12:16:26  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.6  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.5  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.4  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.3  2005/03/18 18:03:41  oeuillot
 * Ameliration du look du TabbedPane !
 *
 * Revision 1.2  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.1  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.core.internal.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.ISet;
import org.rcfaces.core.internal.webapp.IRepository.IFile;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class HierarchicalRepositoryServlet extends RepositoryServlet {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(HierarchicalRepositoryServlet.class);

    private static final String SET_PREFIX = ".sets";

    private static final String MODULES_PREFIX = ".modules";

    private static final String GROUP_ALL_DEFAULT_VALUE = null;

    private static final String BOOT_SET_DEFAULT_VALUE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        IHierarchicalRepository hierarchicalRepository = getHierarchicalRepository();

        String groupAll = config.getInitParameter(getParameterPrefix()
                + MODULES_PREFIX + ".GROUP_ALL_FILES");
        if (groupAll == null) {
            groupAll = getGroupAllDefaultValue();
        }
        if (groupAll != null) {
            if ("all".equalsIgnoreCase("all")) {
                LOG.debug("Concat all files for all modules.");

            } else {
                LOG.debug("Concat all files for modules: " + groupAll);
            }

            for (StringTokenizer st = new StringTokenizer(groupAll, ";, "); st
                    .hasMoreTokens();) {
                String name = st.nextToken();

                if ("all".equalsIgnoreCase(name)) {
                    IHierarchicalRepository.IModule modules[] = hierarchicalRepository
                            .listModules();
                    for (int i = 0; i < modules.length; i++) {
                        modules[i].setGroupAllFiles(true);
                    }

                    continue;
                }

                IHierarchicalRepository.IModule module = hierarchicalRepository
                        .getModuleByName(name);
                if (module == null) {
                    throw new IllegalArgumentException("Can not find module '"
                            + name + "' to enable 'groupAll'.");
                }

                module.setGroupAllFiles(true);
            }
        }

        String bootSet = config.getInitParameter(getParameterPrefix()
                + ".BOOT_SET");
        if (bootSet == null) {
            bootSet = getBootSetDefaultValue();
        }

        if (bootSet != null) {
            StringTokenizer st = new StringTokenizer(bootSet, ",; \n\t\r");
            if (st.countTokens() != 1) {
                throw new ServletException(
                        "Only one SET can be specified as BOOT_SET !");
            }

            bootSet = st.nextToken();

            String bootSetName = getParameterPrefix() + SET_PREFIX + "."
                    + bootSet;
            String parameterValue = config.getInitParameter(bootSetName);
            if (parameterValue == null) {
                throw new ServletException("Set specified by " + bootSetName
                        + " is not defined !");
            }

            IHierarchicalRepository.ISet set = initializeModuleSet(bootSet,
                    parameterValue);
            if (set == null) {
                throw new IllegalArgumentException("Can not find boot set '"
                        + bootSet + "'.");
            }

            hierarchicalRepository.setBootSet(set);
        }

        Enumeration parameterNames = config.getInitParameterNames();
        String setPrefix = getParameterPrefix() + SET_PREFIX;
        for (; parameterNames.hasMoreElements();) {
            String parameterName = (String) parameterNames.nextElement();

            if (parameterName.startsWith(setPrefix) == false) {
                continue;
            }

            String parameterValue = config.getInitParameter(parameterName);

            parameterName = parameterName.substring(setPrefix.length() + 1);
            if (parameterName.length() < 1) {
                continue;
            }

            if (parameterName.equals(bootSet)) {
                continue;
            }

            LOG.debug("Group modules '" + parameterValue + "' into the set '"
                    + parameterName + "'.");

            initializeModuleSet(parameterName, parameterValue);
        }
    }

    protected String getBootSetDefaultValue() {
        return BOOT_SET_DEFAULT_VALUE;
    }

    protected String getGroupAllDefaultValue() {
        return GROUP_ALL_DEFAULT_VALUE;
    }

    protected final IHierarchicalRepository getHierarchicalRepository() {
        return (IHierarchicalRepository) getRepository();
    }

    private ISet initializeModuleSet(String setName, String moduleNames) {
        StringTokenizer st = new StringTokenizer(moduleNames, ",; \n\t\r");

        List l = new ArrayList(st.countTokens());
        for (; st.hasMoreTokens();) {
            String moduleName = st.nextToken();

            l.add(moduleName);
        }

        String uri = getSetURI(setName);

        return getHierarchicalRepository().declareSet(setName, uri,
                (String[]) l.toArray(new String[l.size()]));
    }

    protected abstract String getSetURI(String setName);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected abstract class HierarchicalRecord extends Record {
        private static final String REVISION = "$Revision$";

        public HierarchicalRecord(IFile file, Locale locale) {
            super(file, locale);
        }

        public void verifyModifications() {

            boolean modified = false;

            if (file instanceof IHierarchicalRepository.ISet) {
                modified = verifySetModifications((IHierarchicalRepository.ISet) file);

            } else if (file instanceof IHierarchicalRepository.IModule) {
                modified = verifyModuleModifications((IHierarchicalRepository.IModule) file);
            }

            if (modified == false) {
                return;
            }

            resetRecord();
        }

        private boolean verifyModuleModifications(
                IHierarchicalRepository.IModule module) {
            return verifyFilesModifications(module.listDependencies());
        }

        private boolean verifySetModifications(IHierarchicalRepository.ISet set) {
            return verifyFilesModifications(set.listDependencies());
        }

        public byte[] getBuffer() throws IOException {
            if (buffer != null) {
                return buffer;
            }

            if (file instanceof IHierarchicalRepository.ISet) {
                return getSetBuffer();
            }

            if (file instanceof IHierarchicalRepository.IModule) {
                return getModuleBuffer();
            }

            return super.getBuffer();
        }

        private byte[] getModuleBuffer() throws IOException {
            IHierarchicalRepository.IModule module = (IHierarchicalRepository.IModule) file;

            return getFilesBuffer(module.listDependencies());
        }

        private byte[] getSetBuffer() throws IOException {
            IHierarchicalRepository.ISet set = (IHierarchicalRepository.ISet) file;

            return getFilesBuffer(set.listDependencies());
        }
    }
}
