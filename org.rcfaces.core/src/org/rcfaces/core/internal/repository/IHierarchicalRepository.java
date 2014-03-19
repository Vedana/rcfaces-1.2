/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.repository;

import java.util.Collection;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHierarchicalRepository extends IRepository {

    int FILE_COLLECTION_TYPE = 0;

    int FILENAME_COLLECTION_TYPE = 1;

    IHierarchicalFile[] computeFiles(Collection<Object> collection,
            int typeOfCollection, IContext context);

    IModule[] listModules();

    IModule getModuleByName(String name);

    ISet[] listSets();

    ISet getBootSet();

    ISet getSetByName(String bootSet);

    void setBootSet(ISet set);

    ISet declareSet(String name, String uri, String[] moduleNames);

    ISet declareSet(String name, String uri, IModule[] modules);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IHierarchicalFile extends IFile {

        IHierarchicalFile[] listDependencies();

        IModule getModule();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface ISet extends IHierarchicalFile {
        IHierarchicalFile[] listExternalDependencies();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IModule extends IHierarchicalFile {
        boolean getGroupAllFiles();

        void setGroupAllFiles(boolean enable);

        boolean isDefaultCoreModule();

        void setDefaultCoreModule();

        IHierarchicalFile[] listExternalDependencies();

        IModule[] listExternalModules();

        ISet getSet();
    }
}
